package com.bujamarket.dto.item;

import com.bujamarket.domain.item.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;
    private String imgName;    //이미지 파일명
    private String oriImgName; //원본 이미지 파일명
    private String imgUrl;     //이미지 조회 경로
    private String repImgYn;   //대표 이미지 여부

    //멤버 변수로 ModelMapper 객체 추가
    private static ModelMapper modelMapper = new ModelMapper();

    //ItemImg 엔티티 객체를 파라미터로 받아서
    //ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때, ItemImgDto 로 값을 복사해서 반환한다
    //static 으로 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 한다
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
