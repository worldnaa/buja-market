package com.bujamarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA 의 Auditing 기능 활성화
@Configuration
public class AuditConfig {

    @Bean //등록자와 수정자를 처리해주는 AuditorAware 를 빈으로 등록
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
