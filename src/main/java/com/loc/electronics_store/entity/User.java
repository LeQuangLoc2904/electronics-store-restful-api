package com.loc.electronics_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;
    String password;
    String email;
    String fullName;
    String phone;
    String address;

    @ManyToMany
    Set<Role> roles; // Ví dụ: ROLE_USER, ROLE_ADMIN

    @Builder.Default
    boolean enabled = true;
}


