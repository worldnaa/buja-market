package com.bujamarket.domain.member;

import com.bujamarket.dto.member.MemberFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                //회원 코드

    @Column(nullable = false)
    private String name;                            //회원 이름

    @Column(nullable = false, unique = true)
    private String email;                           //회원 이메일

    @Column(nullable = false)
    private String password;                        //회원 비밀번호

    private String address;                         //회원 주소

    @Enumerated(EnumType.STRING)
    private Role role;                              //회원 롤

    @Builder
    public Member(String name, String email, String password, String address, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    // 회원정보 생성
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                        .name(memberFormDto.getName())
                        .email(memberFormDto.getEmail())
                        .address(memberFormDto.getAddress())
                        .password(passwordEncoder.encode(memberFormDto.getPassword()))
                        .role(Role.USER)
                        .build();

    }




















}
