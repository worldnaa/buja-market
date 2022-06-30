package com.bujamarket.domain.cart;

import com.bujamarket.domain.BaseEntity;
import com.bujamarket.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue
    private Long id;                //장바구니 상품 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;              //장바구니 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;              //상품 코드

    private int count;              //같은 상품을 장바구니에 몇 개 담을지 저장

    @Builder
    public CartItem(Cart cart, Item item, int count) {
        this.cart = cart;
        this.item = item;
        this.count = count;
    }
}

/*
* private Cart cart;
    - 하나의 장바구니에는 여러 개의 상품을 담을 수 있다
    - 그러므로 @ManyToOne 을 이용하여 다대일 관계로 매핑한다
    - fetch 속성의 값은 LAZY(지연로딩) 로 설정한다

* private Item item;
    - 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있다
    - 그러므로 @ManyToOne 을 이용하여 다대일 관계로 매핑한다
    - fetch 속성의 값은 LAZY(지연로딩) 로 설정한다
*/
