package com.bujamarket.domain.cart;

import com.bujamarket.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                //장바구니 코드

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;                          //회원 코드

    @Builder
    public Cart(Member member) {
        this.member = member;
    }
}

/*
* @OneToOne: 회원 엔티티와 일대일로 매핑한다
* @JoinColumn(name = "member_id"):
    - 매핑할 외래키를 지정한다
    - name 속성에는 매핑할 외래키의 이름을 설정한다
    - name 을 명시하지 않으면 JPA 가 알아서 ID 를 찾지만, 컬럼명이 원하는 대로 생성되지 않을 수 있기에 직접 지정한다

* 회원 엔티티에는 장바구니 엔티티와 관련된 코드가 전혀 없다
* 즉 장바구니 엔티티가 일방적으로 회원 엔티티를 참조하고 있는 것이다
* 결론: 장바구니 엔티티가 회원 엔티티를 참조하는 일대일 단방향 매핑이다
*/