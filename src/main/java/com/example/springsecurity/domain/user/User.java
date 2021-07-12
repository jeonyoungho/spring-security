package com.example.springsecurity.domain.user;


import com.example.springsecurity.domain.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(255) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isEnable = true;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updateEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
