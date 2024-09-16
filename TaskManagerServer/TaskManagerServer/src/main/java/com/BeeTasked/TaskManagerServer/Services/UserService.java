package com.BeeTasked.TaskManagerServer.Services;

import com.BeeTasked.TaskManagerServer.Repository.NoticeRepositoryImpl;
import com.BeeTasked.TaskManagerServer.Repository.MemberRepository;
import com.BeeTasked.TaskManagerServer.Repository.NoticeRepository;
import com.BeeTasked.TaskManagerServer.Repository.UserRepository;
import com.BeeTasked.TaskManagerServer.collections.Member;
import com.BeeTasked.TaskManagerServer.collections.Notification;
import com.BeeTasked.TaskManagerServer.collections.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private NoticeRepositoryImpl noticeCustom;

    //Admin  registration
    public Optional<User> registerUser(User user, String name, String email, String role) {
        try {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new RuntimeException("Account already exists..");
        }else {
            User newAdmin = new User();
            newAdmin.setName(name);
            newAdmin.setEmail(email);
            newAdmin.setRole(role);
            newAdmin.setAdmin(true);
            newAdmin.setTeamCode(generateTeamCode());
            userRepository.save(newAdmin);
            sendMail(email, "BeeTasked TeamCode",
                    "<html><body>" +
                            "<p><b>Hi! "+name+"</b>,</p>\n" +
                            "<p>Thank you for choosing BeeTasked.</p>\n"+
                            "<p>Below is you teamcode to manage your tasks and team seamlessly.</p>\n"+
                            "<p style=\"font-size: 24px;\"><b>"+newAdmin.getTeamCode()+ "</b></p>\n\n"+
                            "<p>Share it with your prospective team members to access their tasks..</p>\n\n"+
                            "<p><b> Best Regards,</p> </b>\n"+
                            "<p><b>BeeTasked.</p></b>"+
                            "</body></html>"
            );
            return Optional.of(newAdmin);
        }
        }catch (Exception e){
            throw new RuntimeException("User Registration failed", e);
        }

    }

    public Optional<Object> addUser(User user) {
        try {
        Optional<User> adminLogin = userRepository.findByEmailAndTeamCode(user.getEmail(), user.getTeamCode());
        if(adminLogin.isPresent()){
            User adminuser = adminLogin.get();
            return Optional.of(adminuser);
        }else{
            Optional<User> adminExists = userRepository.findByTeamCode(user.getTeamCode());
            if(adminExists.isPresent()){
                User admin = adminExists.get();

                if(admin.getMembers() != null) {
                    Member newMember = new Member();
                    boolean userExists = admin.getMembers()
                            .stream()
                            .anyMatch(m -> m.getEmail().equals(user.getEmail()));
                    if (!userExists) {
                        newMember.setId(UUID.randomUUID().toString());
                        newMember.setName(user.getName());
                        newMember.setEmail(user.getEmail());
                        newMember.setActive(user.isActive());
                        newMember.setRole(user.getRole());
                        newMember.setTitle(user.getRole());
                        newMember.setTasks(user.getTasks());

                        // userRepository.save(newUser);
                        admin.getMembers().add(newMember);
                        userRepository.save(admin);
                        memberRepository.save(newMember);

                        return Optional.of(newMember);
                    } else {
                        System.out.println("User already exists in the team");
                        return Optional.of(newMember);
                    }
                }else {
                    admin.setMembers(new ArrayList<>());
                    Member newMember = new Member();
                    newMember.setId(UUID.randomUUID().toString());
                    newMember.setName(user.getName());
                    newMember.setEmail(user.getEmail());
                    newMember.setActive(newMember.isActive());
                    newMember.setRole(newMember.getRole());
                    newMember.setTitle(user.getRole());
                    newMember.setTasks(user.getTasks());

                    // userRepository.save(newUser);
                    admin.getMembers().add(newMember);
                    userRepository.save(admin);
                    memberRepository.save(newMember);
                    return Optional.of(newMember);
                }
            }else {
                throw new RuntimeException("Invalid team code");
            }
            
        }
        }catch (Exception e){
            throw new RuntimeException("User Login failed", e);
        }
    }

    private String generateTeamCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[6];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public void sendMail(String to, String subject, String body) throws MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setFrom("BeeTasked");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            System.out.println("Mail sent successfully...");
        }catch (Exception e){
            throw new RuntimeException("Could not send mail", e);
        }
    }

    public void delete(String adminId, String memberId ) {
        try{
           Optional<User> admin = userRepository.findById(adminId);
           if(admin.isPresent()){
               User user = admin.get();
               boolean memberFound = false;
               for(Iterator<Member> iterator = user.getMembers().iterator(); iterator.hasNext();){
                   Member member = iterator.next();
                   if(member.getId() != null && member.getId().equals(memberId)){
                       iterator.remove();
                       memberFound =true;
                       break;
                   }
               }
               if(!memberFound){
                   throw new IllegalArgumentException("Member not found in admins team");
               }
               userRepository.save(user);
               userRepository.deleteById(memberId);
               System.out.println("Member Deleted");
           }else{
               throw new IllegalArgumentException("Admin not found");
           }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Member> getTeamList(String userId) {
        try {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.isAdmin() && user.isActive()){
                return user.getMembers();
            }else{
                throw new RuntimeException("Not permitted");
            }
        }else{
            throw new RuntimeException("User not found");
        }
        }catch (Exception e){
            throw new RuntimeException("Could not fetch team Members", e);
        }
    }

    public List<Notification> getNotifications(User user, Notification notification) {
        String userId = user.getId();
        return noticeRepository.findByTeamAndIsReadNotContaining(notification.getTeam(), userId);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void markNotificationRead(User user, Notification notification, String isReadType) {
        if ("all".equals(isReadType)) {
            noticeCustom.updateMany(user);
        } else {
            noticeCustom.updateOne(notification, user);
        }
}

    public User activateUserProfile(String id, User user) {
        try{
            Optional<User> userOptional = userRepository.findById(id);
            if(userOptional.isPresent()){
                User user1 = userOptional.get();
                user1.setActive(user.isActive());
                userRepository.save(user1);
                System.out.println("User account has been " + (user.isActive()? "activated ": " Deactivated"));
                return user;
            }else{
                System.out.println("User not found");
            }

        }catch (Exception e){
            e.getMessage();
        }
      return null;
    }

    public User save(User updateUser) {
        return userRepository.save(updateUser);
    }
}
