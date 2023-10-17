package com.gdu.app13.util;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {

  // 파일이 저장될 경로 반환하기
  public String getPath() {
    /*  /storage/yyyy/MM/dd */
    LocalDate today = LocalDate.now();
    return "/storage/" + today.getYear() + "/" + String.format("%02d", today.getMonthValue()) + "/" + String.format("%02d", today.getDayOfMonth());
    // return "/storage/" + DateTimeFormat.ofPattern("yyyy/MM/dd").format(today);
    // 저장되는 곳은 D:\storage (파일이 C에 있다면 C:\storage)
  }
  
  //파일이 저장될 이름 반환하기
  public String getFilesystemName(String originalName) {
    
    /*  UUID.확장자  */
   
    String extName = null;
    if(originalName.endsWith("tar.gz")) {  // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {
      String[] arr = originalName.split("\\.");  // [.] 또는 \\.
      extName = arr[arr.length - 1];
    }
    
    return UUID.randomUUID().toString().replace("-", "") + "." + extName; 
  }
  
}
