package com.loc.electronics_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
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

    Set<String> roles; // Ví dụ: ROLE_USER, ROLE_ADMIN

    @Builder.Default
    boolean enabled = true;

    @OneToMany(mappedBy = "user")
    List<Token> tokens;
}


