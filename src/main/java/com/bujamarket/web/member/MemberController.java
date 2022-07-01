package com.bujamarket.web.member;

import com.bujamarket.domain.member.Member;
import com.bujamarket.dto.member.MemberFormDto;
import com.bujamarket.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //회원가입 페이지 이동
    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }


    //회원가입 수행
    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        //검증하려는 객체 앞에 @Valid 을 선언하고, 파라미터로 BindingResult 객체를 추가한다
        //검증한 결과는 bindingResult 객체에 담아준다

        //bindingResult 에 에러가 있다면 회원가입 페이지로 이동한다
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            //중복회원 예외 발생 시 에러 메시지를 뷰로 전달
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        //회원가입 성공 시 메인페이지로 리다이렉트
        return "redirect:/";
    }


    //로그인 페이지 이동
    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }


    //로그인 에러
    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
