package com.resume2role.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "profiles")
public class Profile {

    @Id
    private String id;

    private String email;
    private String fullName;
    private String targetRole;
    private String techStack;
    private String phone;
    private String city;
    private String avatar;
}