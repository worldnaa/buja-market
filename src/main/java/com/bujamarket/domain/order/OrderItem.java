package com.bujamarket.domain.order;

import com.bujamarket.domain.BaseEntity;
import com.bujamarket.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter @Setter
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue
    private Long id;                  //주문상품 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;                //상품 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;              //주문 코드

    private int orderPrice;           //주문 가격
    private int count;                //주문 수량
}

/*
* private Item item;
    - 하나의 상품은 여러 주문상품으로 들어갈 수 있다
    - 그러므로 주문상품 엔티티 기준에서 @ManyToOne 을 이용하여 다대일 단방향 매핑을 한다
    - fetch 속성의 값은 LAZY(지연로딩) 로 설정한다

* private Order order;
    - 한 번의 주문에 여러 개의 상품을 주문할 수 있다
    - 그러므로 주문상품 엔티티와 주문 엔티티를 다대일 단방향 매핑으로 먼저 설정한다
    - fetch 속성의 값은 LAZY(지연로딩) 로 설정한다
*/