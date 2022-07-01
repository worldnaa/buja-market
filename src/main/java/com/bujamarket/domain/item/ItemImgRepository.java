package com.bujamarket.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    // 상품 이미지 아이디를 오름차순으로 가져온다
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

}
