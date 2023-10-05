package com.gdu.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.test.dao.UserDao;
import com.gdu.test.dto.UserDto;

public class UserServiceImpl implements UserService {

  private UserDao userDao;
  
  
  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }



  @Override
  public List<UserDto> getUserList() {
    return userDao.getUserList();
  }

}
