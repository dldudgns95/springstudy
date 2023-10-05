package com.gdu.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.test.service.UserService;

@Controller
public class UserController {
  
  private UserService userService;
  
  
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }


  @RequestMapping(value="/user/list.do", method=RequestMethod.GET)
  public String getUserList(Model model) {
    model.addAttribute("userList",userService.getUserList());
    return "user/list";
  }

}
