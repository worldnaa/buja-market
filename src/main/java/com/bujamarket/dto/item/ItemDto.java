package com.bujamarket.dto.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;                  //상품 코드
    private String itemName;          //상품 명
    private Integer price;            //상품 가격
    private String itemDetail;        //상품 상세설명
    private String sellStatCd;        //상품 판매상태
    private LocalDateTime regTime;    //상품 등록시간
    private LocalDateTime updateTime; //상품 수정시간

}
