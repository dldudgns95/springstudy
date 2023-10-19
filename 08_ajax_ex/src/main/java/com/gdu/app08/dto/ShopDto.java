package com.gdu.app08.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopDto {
  private String title;
  private String link;
  private String image;
  private int lprice;
  private String mallName;
}
