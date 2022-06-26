package com.bujamarket.domain.item;

import com.bujamarket.domain.item.Item;
import com.bujamarket.domain.item.ItemRepository;
import com.bujamarket.domain.item.ItemSellStatus;
import com.bujamarket.domain.item.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    // 영속성 컨텍스트를 사용하기 위해 EntityManager 빈을 주입한다
    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    // 테스트 내용 초기화 (테스트 메서드 실행 이후에 수행된다)
    @AfterEach
    void cleanup() {
        itemRepository.deleteAll();
    }


    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {

        //given
        String itemName = "테스트 상품";

        itemRepository.save(Item.builder()
                .itemName(itemName)
                .price(10000)
                .stockNumber(100)
                .itemDetail("테스트 상품 상세설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Item item = itemList.get(0);
        assertThat(item.getItemName()).isEqualTo(itemName);
        assertThat(item.getPrice()).isEqualTo(10000);
        assertThat(item.getItemDetail()).isEqualTo("테스트 상품 상세설명");

    }


    public void createItemList() {
        for (int i=1; i<=10; i++){
            itemRepository.save(Item.builder()
                    .itemName("테스트 상품" + i)
                    .itemDetail("테스트 상품 상세설명" + i)
                    .price(10000 + i)
                    .stockNumber(100)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build());
        }
    }


    @Test
    @DisplayName("[상품명] 조회 테스트")
    public void findByItemNameTest() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByItemName("테스트 상품1");

        //then
        Item item = itemList.get(0);
        assertThat(item.getItemName()).isEqualTo("테스트 상품1");
        assertThat(item.getPrice()).isEqualTo(10001);
        assertThat(item.getItemDetail()).isEqualTo("테스트 상품 상세설명1");
    }


    @Test
    @DisplayName("[상품명] or [상품상세설명] 조회 테스트")
    public void findByItemNameOrItemDetailTest() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품2", "테스트 상품 상세설명5");

        //then
        Item item1 = itemList.get(0);
        Item item2 = itemList.get(1);
        assertThat(item1.getItemName()).isEqualTo("테스트 상품2");
        assertThat(item1.getItemDetail()).isEqualTo("테스트 상품 상세설명2");
        assertThat(item2.getItemName()).isEqualTo("테스트 상품5");
        assertThat(item2.getItemDetail()).isEqualTo("테스트 상품 상세설명5");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("[특정 가격보다 값이 작은 상품] 조회 테스트")
    public void findByPriceLessThanTest() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);

        //then
        assertThat(itemList.get(0).getPrice()).isEqualTo(10001);
        assertThat(itemList.get(1).getPrice()).isEqualTo(10002);
        assertThat(itemList.get(2).getPrice()).isEqualTo(10003);
        assertThat(itemList.get(3).getPrice()).isEqualTo(10004);

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("[특정 가격보다 값이 작은 상품]을 [내림차순] 조회 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        //then
        assertThat(itemList.get(0).getPrice()).isEqualTo(10004);
        assertThat(itemList.get(1).getPrice()).isEqualTo(10003);
        assertThat(itemList.get(2).getPrice()).isEqualTo(10002);
        assertThat(itemList.get(3).getPrice()).isEqualTo(10001);

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세설명");

        //then
        assertThat(itemList.get(0).getItemDetail()).isEqualTo("테스트 상품 상세설명10");
        assertThat(itemList.get(1).getItemDetail()).isEqualTo("테스트 상품 상세설명9");
        assertThat(itemList.get(2).getItemDetail()).isEqualTo("테스트 상품 상세설명8");
        assertThat(itemList.get(3).getItemDetail()).isEqualTo("테스트 상품 상세설명7");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemNameByNative() {

        //given
        this.createItemList();

        //when
        List<Item> itemList = itemRepository.findByItemNameByNative("테스트 상품");

        //then
        assertThat(itemList.get(0).getItemName()).isEqualTo("테스트 상품1");
        assertThat(itemList.get(1).getItemName()).isEqualTo("테스트 상품2");
        assertThat(itemList.get(2).getItemName()).isEqualTo("테스트 상품3");
        assertThat(itemList.get(3).getItemName()).isEqualTo("테스트 상품4");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("Querydsl 조회 테스트 1")
    public void queryDslTest1() {

        this.createItemList();

        // JPAQueryFactory 를 이용해 쿼리를 동적으로 생성한다 (생성자 파라미터로 EntityManager 객체를 넣는다)
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // Querydsl 을 통해 쿼리를 생성하기 위해, 플러그인을 통해 자동으로 생성된 QItem 객체를 이용한다
        QItem qItem = QItem.item;

        // 자바 소스코드지만 SQL 문과 비슷하게 작성할 수 있다
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세설명" + "%"))
                .orderBy(qItem.price.desc());

        // JPAQuery 메소드 중 하나인 fetch() 를 이용해 쿼리 결과를 리스트로 반환한다
        // fetch() 메소드 실행 시점에 쿼리문이 실행된다
        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


    public void createItemList2() {
        for (int i=1; i<=5; i++){
            itemRepository.save(Item.builder()
                    .itemName("테스트 상품" + i)
                    .itemDetail("테스트 상품 상세설명" + i)
                    .price(10000 + i)
                    .stockNumber(100)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build());
        }
        for (int i=6; i<=10; i++){
            itemRepository.save(Item.builder()
                    .itemName("테스트 상품" + i)
                    .itemDetail("테스트 상품 상세설명" + i)
                    .price(10000 + i)
                    .stockNumber(100)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build());
        }
    }


    @Test
    @DisplayName("Querydsl 조회 테스트 2")
    public void queryDslTest2() {

        ///given
        this.createItemList2();
        String itemDetail = "테스트 상품 상세설명";
        String itemSellStatus = "SELL";
        int price = 10003;
        // Querydsl 을 통해 쿼리를 생성하기 위해, 플러그인을 통해 자동으로 생성된 QItem 객체를 이용한다
        QItem qItem = QItem.item;
        // BooleanBuilder 는 쿼리에 들어갈 조건을 만들어주는 빌더다. Predicate 를 구현하고있으며, 메소드 체인 형식으로 사용한다
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // 상품을 조회하는데 필요한 조건을 "and" 로 추가한다
        booleanBuilder.and(qItem.itemDetail.like("%" + itemDetail + "%")); // "테스트 상품 상세설명" 문구를 포함하는 것
        booleanBuilder.and(qItem.price.gt(price));                             // 가격이 10003 보다 큰 것
        // 상품의 판매상태가 'SELL' 일 때만 itemSellStatus 를 동적으로 추가하도록 한다
        if (StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }
        // 데이터를 페이징해 조회하도록 PageRequest.of() 메소드를 이용해 Pageable 객체를 생성한다
        // 1번 인자는 '조회할 페이지 번호' / 2번 인자는 '한 페이지당 조회할 데이터 개수' 를 넣어준다
        Pageable pageable = PageRequest.of(0,5);

        ///when
        // QueryDslPredicateExecutor 인터페이스에서 정의한 findAll()를 이용해 조건에 맞는 데이터를 Page 객체로 받아온다
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        List<Item> resultItemList = itemPagingResult.getContent();

        ///then
        assertThat(resultItemList.get(0).getPrice()).isEqualTo(10004);
        assertThat(resultItemList.get(1).getPrice()).isEqualTo(10005);
        System.out.println("total elements : " + itemPagingResult.getTotalElements());
        for (Item resultItem : resultItemList) {
            System.out.println(resultItem.toString());
        }
    }












}