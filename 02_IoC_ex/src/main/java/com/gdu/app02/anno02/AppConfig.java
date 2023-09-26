package com.gdu.app02.anno02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public MyJdbcConnection myJdbcConnection() {
    MyJdbcConnection myCon = new MyJdbcConnection();
    myCon.setDriver("oracle.jdbc.OracleDriver");
    myCon.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe");
    myCon.setUser("GD");
    myCon.setPassword("1111");
    return myCon;
  }
  
  @Bean
  public MyJdbcDao myJdbcDao() {
    return new MyJdbcDao();
  }
  
  @Bean
  public MyJdbcService myJdbcService() {
    return new MyJdbcService(myJdbcDao());
  }
  
}
