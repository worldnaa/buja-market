package com.bujamarket.domain.order;

import com.bujamarket.domain.item.Item;
import com.bujamarket.domain.item.ItemRepository;
import com.bujamarket.domain.item.ItemSellStatus;
import com.bujamarket.domain.member.Member;
import com.bujamarket.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    public Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setStockNumber(100);
        item.setItemDetail("테스트 상품 상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }


    public Order createOrder() {
        Order order = new Order();

        for (int i=0; i<3; i++) {
            Item item = itemRepository.save(this.createItem());

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            //아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담는다
            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        //given
        Order order = new Order();

        for (int i=0; i<3; i++) {
            Item item = itemRepository.save(this.createItem());

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            //아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담는다
            order.getOrderItems().add(orderItem);
        }

        //when
        orderRepository.saveAndFlush(order); //order 엔티티를 저장하면서 flush 를 호출하여 객체들을 DB에 반영한다
        em.clear();                          //영속성 컨텍스트 상태를 초기화한다

        //영속성 컨텍스트를 초기화했기 때문에 데이터베이스에서 주문 엔티티를 조회한다
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);

        //then
        assertEquals(3, savedOrder.getOrderItems().size());
    }


    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();

        //order 엔티티에서 관리하고 있는 orderItem 리스트의 0번째 인덱스 요소를 제거한다
        order.getOrderItems().remove(0);

        em.flush();
    }


    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        //given
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();

        em.flush(); //객체 데이터베이스에 반영
        em.clear(); //영속성 컨텍스트 상태 초기화

        //when
        //order 엔티티에 저장했던 orderItemId 를 이용하여, orderItem 을 데이터베이스에서 다시 조회
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);

        //then
        //orderItem 엔티티에 있는 order 객체의 클래스 출력
        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("================================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("================================================");
    }
}