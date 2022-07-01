package com.bujamarket.web.item;

import com.bujamarket.dto.item.ItemFormDto;
import com.bujamarket.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;


    //상품 등록 페이지 이동
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }


    //상품 등록
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList) {

        //상품 등록 시 필수 값이 없다면, 상품 등록 페이지로 전환한다
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        //상품 등록 시 첫번째 이미지가 없다면, 에러 메시지와 함께 상품 등록 페이지로 전환한다
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지 등록은 필수입니다");
        }

        //매개 변수로 '상품정보' 와 '상품이미지정보' 를 넘기며, 상품 저장 로직을 호출한다
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다");
            return "item/itemForm";
        }

        //상품이 정상적으로 등록되었다면 메인 페이지로 이동한다
        return "redirect:/";
    }


    //상품 수정 페이지 이동   ==> 상품 등록, 수정 페이지 따로 만들어서 수정하기!
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {

        try {
            model.addAttribute("itemFormDto", itemService.getItemDtl(itemId));
        }catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }


    //상품 수정
    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {

        //상품 수정 시 필수 값이 없다면, 상품 수정 페이지로 전환한다
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        //상품 수정 시 첫번째 이미지가 없다면, 에러 메시지와 함께 상품 수정 페이지로 전환한다
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지 등록은 필수입니다");
            return "item/itemForm";
        }

        //매개 변수로 '상품정보' 와 '상품이미지정보' 를 넘기며, 상품 수정 로직을 호출한다
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        }catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다");
            return "item/itemForm";
        }

        //상품이 정상적으로 수정되었다면 메인 페이지로 이동한다
        return "redirect:/";
    }


}
