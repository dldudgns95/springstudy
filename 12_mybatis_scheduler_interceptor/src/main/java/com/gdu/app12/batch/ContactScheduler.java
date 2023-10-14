package com.gdu.app12.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app12.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContactScheduler {
  
  private final ContactService contactService;
  
  @Scheduled(cron="0 0 0/1 * * ?")
  public void doSomething() {
    
    // 한 시간마다 가장 예전에 등록된 연락처를 삭제하는 스케쥴
    // DELETE - ContactMapper / contactMapper - contactService/ContactServiceImpl
    int result = contactService.schedulerDelete();
    if(result == 1) {
      log.info("삭제 성공!");
    } else if(result == 0) {
      log.info("삭제 실패");
    }
  }
  // http://www.cronmaker.com/ 크론포멧 만들어주는 사이트

}
