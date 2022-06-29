package com.bujamarket.config;

import com.bujamarket.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/members/login")                          //로그인 페이지 URL 설정
                .defaultSuccessUrl("/")                               //로그인 성공 시 이동할 URL 설정
                .usernameParameter("email")                           //로그인 시 사용할 파라미터 이름으로 email 지정
                .failureUrl("/members/login/error") //로그인 실패 시 이동할 URL 설정
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 URL 설정
                .logoutSuccessUrl("/");                                                    //로그아웃 성공 시 이동할 URL 설정


        //http.authorizeRequests(): 시큐리티 처리에 HttpServletRequest 를 이용한다는 것
        http.authorizeRequests()
                //permitAll(): 모든 사용자가 인증(로그인) 없이 해당 경로에 접근할 수 있도록 설정
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                //'/admin' 으로 시작하는 경로는 해당 계정이 'ADMIN' Role 일 경우에만 접근할 수 있도록 설정
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                //위에서 설정한 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정
                .anyRequest().authenticated();


        //인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러 등록
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }


    // BCryptPasswordEncoder 의 해시함수를 이용해 비밀번호를 암호화하여 저장한다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //static 폴더의 하위 파일들은 인증을 무시하도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
}
