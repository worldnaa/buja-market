package com.bujamarket.dto.item;

import com.bujamarket.domain.item.Item;
import com.bujamarket.domain.item.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명 입력은 필수입니다")
    private String itemName;

    @NotBlank(message = "가격 입력은 필수입니다")
    private Integer price;

    @NotBlank(message = "상품 상세설명 입력은 필수입니다")
    private String itemDetail;

    @NotBlank(message = "재고 입력은 필수입니다")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    //상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();

    //멤버 변수로 ModelMapper 객체 추가
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}

/*
* return modelMapper.map(~~, ~~.class);
: modelMapper 를 이용하여 Entity 객체와 Dto 객체 간의 데이터를 복사하여 복사한 객체를 반환한다
*/
