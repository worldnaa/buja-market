package com.bujamarket.web.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN") //현재 인증된 사용자 Role 을 'ADMIN' 으로 세팅한다
    public void itemFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //상품등록 페이지에 GET 요청을 보낸다
               .andDo(print())                                                   //요청과 응답 메시지를 콘솔창에 출력해준다
               .andExpect(status().isOk());                                      //응답 상태 코드가 정상인지 확인한다
    }


    @Test
    @DisplayName("상품등록 페이지 일반회원 접근 테스트")
    @WithMockUser(username = "user", roles = "USER") //현재 인증된 사용자 Role 을 'USER' 로 세팅한다
    public void itemFormNotAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
               .andDo(print())
               .andExpect(status().isForbidden()); //상품등록 페이지 진입 요청 시 'Forbidden' 예외가 발생하면 테스트가 성공적으로 통과한다
    }

}