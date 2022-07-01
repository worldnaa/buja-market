package com.bujamarket.domain.item;

import com.bujamarket.domain.BaseEntity;
import com.bujamarket.dto.item.ItemFormDto;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter @Setter
@Entity
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                //상품 코드

    @Column(nullable = false, length = 50)
    private String itemName;                        //상품 명

    @Column(nullable = false)
    private int price;                              //상품 가격

    @Column(nullable = false)
    private int stockNumber;                        //상품 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;                      //상품 상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;          //상품 판매상태


    //상품 업데이트
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

}

/*
* @Entity : 테이블과 링크될 클래스임을 나타낸다 (default: 클래스의 카멜케이스 이름을 언더스코어(_) 네이밍으로 테이블 이름을 매칭한다)

* @Id : item 테이블의 PK 필드를 나타낸다 (@Entity 로 선언한 클래스는 반드시 기본키를 가져야한다)
* @Column(name = "item_id") : Item 클래스의 id 변수와, item 테이블의 item_id 컬럼을 매핑한다
* @GeneratedValue(strategy = GenerationType.AUTO) : JPA 구현체가 자동으로 기본키 생성 전략을 결정한다

* @Column(length = 50)      : String 타입의 길이를 50으로 세팅한다 (default: 255)
* @Column(nullable = false) : null 값의 허용 여부를 설정한다 (false: NOT NULL 제약조건 추가)

* @Lob : CLOB, BLOB 타입을 매핑한다 (매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB로 매핑)
* CLOB : 사이즈가 큰 데이터를 외부 파일로 저장하기 위한 데이터 타입 (문자형 대용량 파일을 저장)
* BLOB : 바이너리 데이터를 DB 외부에 저장하기 위한 타입 (이미지, 사운드, 비디오 같은 멀티미디어 데이터)

* @Enumerated : enum 타입을 매핑한다
*/
