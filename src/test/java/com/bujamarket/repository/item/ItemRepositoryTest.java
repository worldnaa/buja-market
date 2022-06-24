package com.bujamarket.repository.item;

import com.bujamarket.domain.item.Item;
import com.bujamarket.domain.item.ItemSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

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

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {

        //given
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

        //when
        List<Item> itemList = itemRepository.findByItemName("테스트 상품1");

        //then
        Item item = itemList.get(0);
        assertThat(item.getItemName()).isEqualTo("테스트 상품1");
        assertThat(item.getPrice()).isEqualTo(10001);
        assertThat(item.getItemDetail()).isEqualTo("테스트 상품 상세설명1");
    }
}