package com.bujamarket.repository.item;

import com.bujamarket.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName); //상품명으로 데이터 조회
}

/*
* JpaRepository<T, ID> : T에는 엔티티 타입의 클래스를 넣는다
* JpaRepository<T, ID> : ID에는 기본키 타입을 넣는다 (Item 클래스의 기본키인 id의 타입은 Long 이다)

* JpaRepository 에는 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어 있다
*/