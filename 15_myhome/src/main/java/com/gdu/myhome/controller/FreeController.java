package com.gdu.myhome.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myhome.service.FreeService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/free")
@RequiredArgsConstructor
@Controller
public class FreeController {

  private final FreeService freeService;
  
  @PostMapping("/add.do")
  public String add(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = freeService.addFree(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/free/list.do";
  }
  
  @GetMapping("/write.form")
  public String writeForm() {
    return "free/write";
  }
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    freeService.loadFreeList(request, model);
    return "free/list";
  }
  
}
