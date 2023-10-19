package com.gdu.app08.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app08.dto.ShopDto;
import com.gdu.app08.service.ShopService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ShopController {
  
  public final ShopService shopService;
  
  @ResponseBody
  @GetMapping(value="/shop/list.do", produces="application/json; charset=UTF-8")
  public List<ShopDto> getShopList(@RequestParam("query") String query
                                 , @RequestParam("display") String display
                                 , @RequestParam("sort") String sort){
    List<ShopDto> searchList = shopService.shopList(query, display, sort);
    for(int i = 0; i < searchList.size(); i++) {
      System.out.println(searchList.get(i).getTitle());
    }
    return shopService.shopList(query, display, sort);
  }
  
}
