package com.bujamarket.web.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    //상품등록 페이지 이동
    @GetMapping(value = "/admin/item/new")
    public String itemForm() {
        return "/item/itemForm";
    }


}
