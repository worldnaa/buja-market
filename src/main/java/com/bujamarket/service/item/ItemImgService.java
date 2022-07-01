package com.bujamarket.service.item;

import com.bujamarket.domain.item.ItemImg;
import com.bujamarket.domain.item.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemImgService {

    //application.properties 파일에 등록한 itemImgLocation 값을 가져와 변수에 넣어준다
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;


    //상품 이미지 저장
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

        String oriImgName = itemImgFile.getOriginalFilename(); //업로드했던 상품의 원본 이미지 파일명
        String imgName = "";                                   //실제 로컬에 저장된 상품의 이미지 파일명
        String imgUrl = "";                                    //업로드 후 로컬에 저장된 이미지 파일을 불러오는 경로

        //상품 이미지 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/buja-market_item_img/" + imgName; //저장한 상품 이미지를 불러올 경로를 설정한다
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }


    //상품 이미지 수정
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {

        //상품 이미지를 수정한 경우
        if (!itemImgFile.isEmpty()) {
            //기존에 저장했던 상품이미지(itemImg) 엔티티 조회
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            //기존에 저장한 상품이미지파일(savedItemImg)이 있을 경우 해당 파일을 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            //업데이트한 상품이미지파일(itemImgFile)을 업로드
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/buja-market_item_img/" + imgName;

            //변경된 상품 이미지 정보를 세팅한다. 여기서는 itemImgRepository.save() 로직을 호출하지 않는다
            //savedItemImg 엔티티는 현재 영속상태이므로, 데이터를 변경하는 것만으로 더티체킹이 동작하여 트랜잭션이 끝날 때 update 쿼리가 실행된다
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

}

