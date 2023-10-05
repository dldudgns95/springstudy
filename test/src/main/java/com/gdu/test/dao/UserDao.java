package com.gdu.test.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.test.dto.UserDto;

public class UserDao {
  
  private UserDto userDto1;
  private UserDto userDto2;
  private UserDto userDto3;
  
  @Autowired
  public void setUserDto1(UserDto userDto1, UserDto userDto2, UserDto userDto3) {
    this.userDto1 = userDto1;
    this.userDto2 = userDto2;
    this.userDto3 = userDto3;
  }
  
  public List<UserDto> getUserList() {
    return Arrays.asList(userDto1, userDto2, userDto3);
  }
  
  

}
