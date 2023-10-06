package com.gdu.app08.service;

import java.util.List;

import com.gdu.app08.dto.ShopDto;

public interface ShopService {
  public List<ShopDto> shopList(String query, String display, String sort); 
}
