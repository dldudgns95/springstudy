package com.gdu.myhome.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myhome.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/user")
@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
  
  @GetMapping(value="/login.form")
  public String loginForm() {
    return "user/login";
  }
  
  @PostMapping(value="/login.do")
  // 반환타입 void일 경우 controller를 거치지 않고 serviceImpl에서 바로 넘어감 (응답이 다양할 경우 사용)
  public void login(HttpServletRequest request, HttpServletResponse response) {
    userService.login(request, response);
  }
  
  @GetMapping(value="/logout.do")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    userService.logout(request, response);
  }
  
  
}
