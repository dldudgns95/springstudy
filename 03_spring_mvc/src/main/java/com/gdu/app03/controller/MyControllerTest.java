package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyControllerTest {

  @GetMapping("/test/main.do")
  public String main() {
    return "test/main";
  }
  
  @GetMapping("/test/list.do")
  public String list(HttpServletRequest request, Model model) {
    model.addAttribute("id",request.getParameter("id"));
    model.addAttribute("pw",request.getParameter("pw"));
    model.addAttribute("name",request.getParameter("name"));
    model.addAttribute("age",request.getParameter("age"));
    return "test/result";
  }
  
}
