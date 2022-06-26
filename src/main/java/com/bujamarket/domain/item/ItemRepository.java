package com.bujamarket.domain.item;

import com.bujamarket.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    // [상품명]으로 데이터 조회
    List<Item> findByItemName(String itemName);

    // [상품명]이나 [상품상세설명]으로 데이터 조회
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    // [특정 가격보다 값이 작은 상품]으로 데이터 조회
    List<Item> findByPriceLessThan(Integer price);

    // [특정 가격보다 값이 작은 상품]을 [가격이 높은 순]으로 데이터 조회
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // [상품상세설명]을 파라미터로 받아서, [해당 상세설명을 포함하는 데이터]를 [가격 내림차순]으로 조회
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // [상품명]을 파라미터로 받아서, [해당 상품명을 포함하는 데이터]를 [가격 오름차순]으로 조회
    @Query(value = "select * from item i where i.item_Name like %:itemName% order by i.price", nativeQuery = true)
    List<Item> findByItemNameByNative(@Param("itemName") String itemName);

}

/*
* JpaRepository 에는 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어 있다
* JpaRepository<T, ID> : T에는 엔티티 타입의 클래스를 넣는다
* JpaRepository<T, ID> : ID에는 기본키 타입을 넣는다 (Item 클래스의 기본키인 id의 타입은 Long 이다)

* QuerydslPredicateExecutor<Item> : Predicate 를 파라미터로 전달하기 위해 추가하는 인터페이스
* Predicate : '이 조건이 맞다' 고 판단하는 근거를 함수로 제공하는 것
*/