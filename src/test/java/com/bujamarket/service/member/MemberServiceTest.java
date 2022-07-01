package com.bujamarket.service.member;

import com.bujamarket.domain.member.Member;
import com.bujamarket.dto.member.MemberFormDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("이부자");
        memberFormDto.setEmail("bujaa@email.com");
        memberFormDto.setPassword("1234");
        memberFormDto.setAddress("서울시 강남구");
        return Member.createMember(memberFormDto, passwordEncoder);
    }


    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        //given
        Member member = createMember();

        //when
        Member savedMember = memberService.saveMember(member);

        //then
        assertThat(member.getName()).isEqualTo(savedMember.getName());
        assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(member.getAddress()).isEqualTo(savedMember.getAddress());
        assertThat(member.getRole()).isEqualTo(savedMember.getRole());
    }


    @Test
    @DisplayName("회원 중복체크 테스트")
    public void saveDuplicateMemberTest() {
        //given
        Member member1 = createMember();
        Member member2 = createMember();

        //when
        memberService.saveMember(member1);

        //then
        Throwable e = assertThrows(IllegalStateException.class,
                                    () -> {memberService.saveMember(member2);});

        assertEquals("이미 가입된 회원입니다", e.getMessage());
    }
}