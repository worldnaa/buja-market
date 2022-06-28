package com.bujamarket.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // [이메일]로 데이터 조회
    Member findByEmail(String email);
}
