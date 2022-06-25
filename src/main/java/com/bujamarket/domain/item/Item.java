package com.bujamarket.domain.item;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Item {

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

    private LocalDateTime regTime;                  //상품 등록시간

    private LocalDateTime updateTime;               //상품 수정시간

    @Builder
    public Item(String itemName, int price, int stockNumber, String itemDetail,
                ItemSellStatus itemSellStatus, LocalDateTime regTime, LocalDateTime updateTime) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.regTime = regTime;
        this.updateTime = updateTime;
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

* @NoArgsConstructor : 기본 생성자를 자동으로 추가한다

* @Builder : Item 클래스의 빌더 패턴 클래스를 생성한다 (생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함한다)
* 여기서는 생성자 대신 @Builder를 통해 제공되는 빌더 클래스를 사용한다.
* 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 똑같으나, 생성자의 경우 지금 채워야할 필드가 무엇인지 명확히 지정할 수 없다
*/
