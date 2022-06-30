package com.bujamarket.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";

        //현재 로그인한 사용자의 정보를 조회하여, 사용자 이름을 등록자와 수정자로 지정한다
        if (authentication != null) {
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}

/*
* AuditorAware: 현재 로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해 해당 인터페이스를 구현한다
*/
