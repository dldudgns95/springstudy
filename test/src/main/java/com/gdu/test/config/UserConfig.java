package com.gdu.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.test.dao.UserDao;
import com.gdu.test.dto.UserDto;
import com.gdu.test.service.UserService;
import com.gdu.test.service.UserServiceImpl;

@Configuration
public class UserConfig {

  @Bean
  public UserDto userDto1() {
    return new UserDto(1, "아이디1", "이름1");
  }
  @Bean
  public UserDto userDto2() {
    return new UserDto(2, "아이디2", "이름2");
  }
  @Bean
  public UserDto userDto3() {
    return new UserDto(3, "아이디3", "이름3");
  }
  
  @Bean
  public UserService setUserService() {
    return new UserServiceImpl();
  }
  
  @Bean
  public UserDao serUserDao() {
    return new UserDao();
  }
  
}
