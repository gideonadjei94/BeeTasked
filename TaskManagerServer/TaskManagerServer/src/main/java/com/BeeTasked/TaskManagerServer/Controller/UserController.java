package com.BeeTasked.TaskManagerServer.Controller;

import com.BeeTasked.TaskManagerServer.Repository.UserRepository;
import com.BeeTasked.TaskManagerServer.Services.UserService;
import com.BeeTasked.TaskManagerServer.collections.Member;
import com.BeeTasked.TaskManagerServer.collections.Notification;
import com.BeeTasked.TaskManagerServer.collections.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
       Optional<User> newUser = userService.registerUser(user, user.getName(), user.getEmail(), user.getRole());
         return ResponseEntity.status(201).body(newUser);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
          Optional<User> newUser = userService.addUser(user);
           return ResponseEntity.status(201).body(newUser);
        }catch (RuntimeException e) {
            return  ResponseEntity.status(400).body(e.getMessage());

        }
    }

    @GetMapping("/members/{userId}")
    public ResponseEntity<?> getTeamList(@PathVariable String userId){
        try {
        List<Member> teamMembers = userService.getTeamList(userId);
        return ResponseEntity.status(201).body(teamMembers);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Notification> getNotificationsList(@RequestParam User user,Notification notification) {
        return userService.getNotifications(user, notification);
    }

    @DeleteMapping("delete/{adminId}/{memberId}")
    public ResponseEntity<?> deleteUser(@PathVariable String adminId, @PathVariable String memberId){
        try {
        userService.delete(adminId, memberId);
        return  ResponseEntity.status(201).body("Member Deleted successfully");
        }catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile")
    public User updateUserProfile(@RequestBody User user, @RequestParam String id, @RequestParam boolean isAdmin) {
         id = user.getId();

        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent() && isAdmin) {
            User updateUser = existingUser.get();
            updateUser.setName(user.getName() != null ? user.getName() : updateUser.getName());
            updateUser.setRole(user.getRole() != null ? user.getRole() : updateUser.getRole());
            return userService.save(updateUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @PostMapping("/markRead")
    public void markNotificationRead(@RequestParam User user, @RequestParam Notification notification, @RequestParam String isReadType) {
        userService.markNotificationRead(user, notification, isReadType);
    }
    @PutMapping("/activate/{id}")
    public User activateUser(@PathVariable String _id, @RequestBody User user){
        return userService.activateUserProfile(_id, user);
    }

}
