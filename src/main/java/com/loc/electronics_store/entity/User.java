package com.loc.electronics_store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String role; // Ví dụ: ROLE_USER, ROLE_ADMIN
    private boolean enabled = true;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}

