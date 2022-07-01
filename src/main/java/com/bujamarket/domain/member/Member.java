package com.bujamarket.domain.member;

import com.bujamarket.domain.BaseEntity;
import com.bujamarket.dto.member.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@ToString
@Getter @Setter
@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                //회원 코드

    private String name;                            //회원 이름

    @Column(unique = true)
    private String email;                           //회원 이메일

    private String password;                        //회원 비밀번호

    private String address;                         //회원 주소

    @Enumerated(EnumType.STRING)
    private Role role;                              //회원 롤


    // 회원정보 생성
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        member.setRole(Role.ADMIN); //관리자 회원가입 만들고 USER 로 바꾸기

        return member;
    }
}
