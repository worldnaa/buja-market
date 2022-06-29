package com.bujamarket.domain.order;

import com.bujamarket.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
//@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long id;                                                                //주문 코드

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;                                                          //회원 코드

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();                         //주문 상품

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;                                                //주문 상태


    private LocalDateTime orderDate;                                                //주문 일
    private LocalDateTime regTime;                                                  //주문 등록시간
    private LocalDateTime updateTime;                                               //주문 수정시간


    /*@Builder
    public Order(Member member, List<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime orderDate, LocalDateTime regTime, LocalDateTime updateTime) {
        this.member = member;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }*/
}

/*
* private Member member;
    - 한 명의 회원은 여러 번 주문을 할 수 있다
    - 그러므로 주문 엔티티 기준에서 @ManyToOne 을 이용하여 다대일 단방향 매핑을 한다

* private List<OrderItem> orderItems = new ArrayList<>();
    - 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해 매핑한다
    - 주문상품(OrderItem) 엔티티와 일대다 매핑을 한다
    - 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티이다
    - Order 엔티티가 주인이 아니므로 'mappedBy' 속성으로 연관 관계의 주인을 설정한다
    - 'mappedBy'      : 연관 관계의 주인인 OrderItem 의 필드 'order' 로 세팅한다
    - 'cascade'       : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정한다
    - 'orphanRemoval' : 고아 객체 제거 기능을 사용하기 위해 'true' 옵션을 설정한다
*/