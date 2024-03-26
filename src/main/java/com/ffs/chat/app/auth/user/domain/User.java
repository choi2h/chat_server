package com.ffs.chat.app.auth.user.domain;

import com.ffs.chat.app.auth.user.domain.repository.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_TYPE")
    private String passwordType;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "TYPE")
    private String userType;

    @Convert(converter = RoleConverter.class)
    @Column(name = "ROLE")
    private Role role; //(대표, 점장, 매니저, 트레이너, FC, 회원)

    @Column(name = "STATUS")
    private String status; // 회원 - (일반회원, PT회원, 휴면회원, 만기회원) & 직원 - (재직중 , 퇴사)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
