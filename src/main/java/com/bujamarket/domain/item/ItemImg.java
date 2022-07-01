package com.bujamarket.domain.item;

import com.bujamarket.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Table(name = "item_img")
@Entity
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;     //이미지 파일명
    private String oriImgName;  //원본 이미지 파일명
    private String imgUrl;      //이미지 조회 경로
    private String repImgYn;    //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //이미지 업데이트
    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}

/*
* private Item item;
    - 상품 엔티티와 대대일 단방향 관계로 매핑한다 (@ManyToOne)
    - 지연로딩을 설정하여 매핑된 상품 엔티티 정보가 필요할 경우에 데이터를 조회하도록 한다
*/
