package com.BeeTasked.TaskManagerServer.collections;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Data
@Document
public class Member {
    @Id
    private String id;
    private String name;
    private String email;
    private String role;
    private String title;
    private List<Task> tasks;
    private boolean isActive = true;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
