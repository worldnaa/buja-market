package com.bujamarket.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "user", roles = "USER")
    public void auditingTest() {

        //given
        Member newMember = Member.builder()
                                    .name("이유저")
                                    .email("user@email.com")
                                    .password("1234")
                                    .address("서울시 강남구")
                                    .role(Role.USER)
                                    .build();
        memberRepository.save(newMember);

        em.flush();
        em.clear();

        //when
        Member member = memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);

        //then
        System.out.println("register time : " + member.getRegTime());
        System.out.println("update time : " + member.getUpdateTime());
        System.out.println("create member : " + member.getCreatedBy());
        System.out.println("modify member : " + member.getModifiedBy());
    }
}