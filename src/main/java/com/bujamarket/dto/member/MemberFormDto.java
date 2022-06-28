package com.bujamarket.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberFormDto {  //회원가입 화면으로부터 넘어온 가입정보를 Dto 클래스에 담는다

    @NotBlank(message = "이름 입력은 필수입니다")
    private String name;      //이름

    @NotBlank(message = "이메일 입력은 필수입니다")
    @Email(message = "이메일에 맞지 않는 형식입니다")
    private String email;     //이메일

    @NotBlank(message = "비밀번호 입력은 필수입니다")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;  //비밀번호

    private String address;   //주소

}

/*
* Entity 클래스인 'Member' 클래스와 유사한 형태의 Dto 클래스를 생성한다
* Entity 클래스는 Request, Response 클래스로 사용하면 안 된다
* Entity 클래스는 DB와 맞닿은 핵심 클래스로, 이를 기준으로 테이블이 생성되고 스키마가 변경된다
* 화면 변경은 아주 사소한 변경인데 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경이다
* 그러므로 변경이 잦은 Request, Response 용 Dto 클래스를 따로 생성한다
*
* [ 결 론 ]
* View Layer 와 Database Layer 의 역할 분리를 철저하게 하는 것이 좋다
* 그러니 반드시 Entity 클래스와 Controller 에서 사용할 Dto 클래스는 분리해서 사용하자!
*/
