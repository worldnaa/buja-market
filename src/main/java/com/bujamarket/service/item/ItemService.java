package com.bujamarket.service.item;

import com.bujamarket.domain.item.Item;
import com.bujamarket.domain.item.ItemImg;
import com.bujamarket.domain.item.ItemImgRepository;
import com.bujamarket.domain.item.ItemRepository;
import com.bujamarket.dto.item.ItemFormDto;
import com.bujamarket.dto.item.ItemImgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;


    //상품 등록
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws  Exception {
        //상품 등록
        Item item = itemFormDto.createItem(); //상품등록폼에서 입력받은 데이터를 이용해 item 객체 생성
        itemRepository.save(item);            //상품 데이터 저장

        //이미지 등록
        for (int i=0; i<itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if (i == 0) {
                itemImg.setRepImgYn("Y"); //첫번째 이미지일 경우 대표상품 이미지 여부 Y 설정
            }else {
                itemImg.setRepImgYn("N"); //나머지 상품 이미지는 N 설정
            }

            //상품의 이미지 정보 저장
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }


    //등록된 상품 조회
    @Transactional(readOnly = true) //상품 데이터를 읽어오는 트랜잭션을 읽기 전용으로 설정(JPA가 더티체킹(변경감지)을 수행하지 않아서 성능이 향상됨)
    public ItemFormDto getItemDtl(Long itemId) {
        //해당 상품의 이미지를 등록순(이미지 아이디의 오름차순)으로 가져온다
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        //조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 List 에 추가한다
        for (ItemImg itemImg : itemImgList) {
            itemImgDtoList.add(ItemImgDto.of(itemImg));
        }

        //상품 아이디를 통해 상품 엔티티를 조회한다. 존재하지 않을 경우엔 Exception 을 발생시킨다
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }


    //등록된 상품 업데이트
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품등록화면에서 전달받은 상품아이디를 이용하여 상품 엔티티를 조회한다
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        //상품등록화면에서 전달받은 ItemFormDto 를 이용하여 상품 엔티티를 업데이트한다
        item.updateItem(itemFormDto);

        //상품 이미지 아이디 리스트를 조회한다
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //상품 이미지를 업데이트하기 위해서, '상품이미지아이디' 와 '상품이미지파일정보' 를 파라미터로 전달한다
        for (int i=0; i<itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }
}
