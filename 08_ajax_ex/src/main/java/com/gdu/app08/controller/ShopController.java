package com.gdu.app08.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app08.dto.ShopDto;
import com.gdu.app08.service.ShopService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ShopController {
  
  public final ShopService shopService;
  
  @GetMapping(value="/shop/list.do")
  public List<ShopDto> getShopList(@RequestParam("query") String query
                                 , @RequestParam("display") String display
                                 , @RequestParam("sort") String sort){
    return shopService.shopList(query, display, sort);
  }
  
}
