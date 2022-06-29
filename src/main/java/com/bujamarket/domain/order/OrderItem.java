package com.bujamarket.domain.order;

import com.bujamarket.domain.item.Item;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter @Setter
//@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue
    private Long id;                  //주문상품 코드

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;                //상품 코드

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;              //주문 코드

    private int orderPrice;           //주문 가격
    private int count;                //주문 수량
    private LocalDateTime regTime;    //주문 등록시간
    private LocalDateTime updateTime; //주문 수정시간


    /*@Builder
    public OrderItem(Item item, Order order, int orderPrice, int count, LocalDateTime regTime, LocalDateTime updateTime) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }*/
}

/*
* private Item item;
    - 하나의 상품은 여러 주문상품으로 들어갈 수 있다
    - 그러므로 주문상품 엔티티 기준에서 @ManyToOne 을 이용하여 다대일 단방향 매핑을 한다

* private Order order;
    - 한 번의 주문에 여러 개의 상품을 주문할 수 있다
    - 그러므로 주문상품 엔티티와 주문 엔티티를 다대일 단방향 매핑으로 먼저 설정한다
*/