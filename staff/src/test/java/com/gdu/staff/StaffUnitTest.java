package com.gdu.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.staff.dao.StaffMapper;
import com.gdu.staff.dto.StaffDto;

/*
 * JUnit 처리 방법
 * 1. spring-test dependency를 추가한다.
 * 2. @Runwith를 추가한다.
 * 3. @ContextConfiguration을 추가한다.
 *    ContactDao 객체(Bean)을 생성한 방법에 따라서 아래 3가지 방식 중 선택한다.
 *   1) <bean> 태그 : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
 *   2) @bean       : @ContextConfiguration(classes=AppConfig.class)
 *   3) @Component  : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")  
 */

//JUnit4를 이용한다.
@RunWith(SpringJUnit4ClassRunner.class) 

// ContactDao Bean 생성 방법을 알려준다.
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")

// 테스트 메소드의 이름 오름차순(알파벳순)으로 테스트를 수행한다.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StaffUnitTest {
  
  @Autowired  // Spring Container에서 StaffMapper 객체(Bean)을 가져온다.
  private StaffMapper staffMapper;
  
  
  @Before  // 테스트 수행 전에 실행
  public void 삽입테스트() {
    StaffDto staff = new StaffDto("99999", "김기획", "기획부", 5000);
    int addResult = staffMapper.insertStaff(staff);
    assertEquals(1, addResult);
  }
  
  @Test  // 테스트를 수행한다.
  public void 조회테스트() {
    String sno = "99999";
    StaffDto staff = staffMapper.getStaff(sno);
    assertNotNull(staff);
  }
 

}
