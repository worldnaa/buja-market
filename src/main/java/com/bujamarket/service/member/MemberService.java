package com.bujamarket.service.member;

import com.bujamarket.domain.member.Member;
import com.bujamarket.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //회원 가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }


    //회원 중복체크
    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }


    //회원 로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        //UserDetail 을 구현하고 있는 User 객체를 반환해준다
        //User 객체를 생성하기 위해, 생성자로 회원의 이메일, 비밀번호, role 을 파라미터로 넘겨준다
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }




}

/*
* @RequiredArgsConstructor: 'final' 혹은 '@NonNull' 이 붙은 필드에 생성자를 생성해준다
* @Transactional: 로직을 처리하다 에러가 발생하면, 변경된 데이터를 로직 수행 이전 상태로 콜백 시켜준다
* @Service: 비즈니스 로직을 담당하는 서비스 계층 클래스에 선언한다

* UserDetailsService: 데이터베이스에서 회원 정보를 가져오는 역할을 담당하는 인터페이스
    - loadUserByUsername() 메소드를 가지고 있으며, 해당 메소드는
    - 회원정보를 조회하여 사용자의 정보와 권한을 갖는 UserDetails 인터페이스를 반환한다
    - 스프링 시큐리티에서 UserDetailsService 를 구현하고 있는 클래스를 통해 로그인 기능을 구현한다

* UserDetails: 스프링 시큐리티에서 회원의 정보를 담기 위해서 사용하는 인터페이스
    - 이 인터페이스를 직접 구현하거나, 스프링 시큐리티에서 제공하는 User 클래스를 사용한다
    - User 클래스는 UserDetails 인터페이스를 구현하고 있는 클래스이다
*/