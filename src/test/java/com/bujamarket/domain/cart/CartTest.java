package com.bujamarket.domain.cart;

import com.bujamarket.domain.member.Member;
import com.bujamarket.domain.member.MemberRepository;
import com.bujamarket.dto.member.MemberFormDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;


    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("이부자");
        memberFormDto.setEmail("bujaa@email.com");
        memberFormDto.setPassword("1234");
        memberFormDto.setAddress("서울시 강남구");
        return Member.createMember(memberFormDto, passwordEncoder);
    }


    @Test
    @DisplayName("장바구니와 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {

        //given
        Member member = createMember();
        Cart cart = Cart.builder().member(member).build();

        //when
        memberRepository.save(member);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);

        //then
        assertEquals(savedCart.getMember().getId(), member.getId());
    }

    /*
    * em.flush();
        - JPA 는 영속성 컨텍스트에 데이터 저장 후, 트랜잭션이 끝날 때 flush()를 호출하여 데이터베이스에 반영한다
        - 회원,장바구니 엔티티를 영속성 컨텍스트에 저장 후, 엔티티 매니저로부터 강제로 flush()를 호출하여 DB에 반영한다

    * em.clear();
        - JPA 는 영속성 컨텍스트로부터 엔티티 조회 후, 영속성 컨텍스트에 엔티티가 없으면 데이터베이스를 조회한다
        - 실제 DB에서 장바구니 엔티티를 가지고 올 때 회원 엔티티도 가지고 오는지 보기 위해 영속성 컨텍스트를 비워준다

    * cartRepository.findById(cart.getId())
        - 저장된 장바구니 엔티티를 조회한다
    */

}