package com.bujamarket.service.item;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Log
@Service
public class FileService {

    //파일 업로드
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        //UUID(Universally Unique ldentifier): 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용
        UUID uuid = UUID.randomUUID();

        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //원본 파일의 확장자
        String savedFileName = uuid.toString() + extension;          //저장될 파일명 (uuid 값 + 원본 파일의 확장자)
        String fileUploadFullUrl = uploadPath + "/" + savedFileName; //저장될 파일의 Full Url 경로

        //FileOutputStream: 바이트 단위의 출력을 내보내는 클래스
        //생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만든다
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        //fileData 를 파일 출력 스트림에 입력한다
        fos.write(fileData);
        fos.close();

        return savedFileName; //업로드된 파일의 이름을 반환한다
    }


    //파일 삭제
    public void deleteFile(String filePath) throws Exception {

        //파일이 저장된 경로를 이용하여 File 객체를 생성한다
        File deleteFile = new File(filePath);

        //해당 파일이 존재하면 파일을 삭제한다
        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다");
        } else {
            log.info("파일이 존재하지 않습니다");
        }
    }
}
