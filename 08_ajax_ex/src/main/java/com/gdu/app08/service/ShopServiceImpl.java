package com.gdu.app08.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.gdu.app08.dto.ShopDto;

@Service
public class ShopServiceImpl implements ShopService {

  @Override
  public List<ShopDto> shopList(String query, String display, String sort) {
    
    List<ShopDto> list = new ArrayList<ShopDto>();
    
    URL url = null;
    HttpURLConnection con = null;
    BufferedReader reader = null;
    
    try {
      
      String spec = "https://openapi.naver.com/v1/search/shop.json";
      String clientId = "4DEmLZE_bTCwOOIcBI5a";
      String clientSecret = "wrkcYZS46r";
      
      StringBuilder sbUrl = new StringBuilder();
      sbUrl.append(spec);
      sbUrl.append("?query=").append(URLEncoder.encode(query, "UTF-8"));
      sbUrl.append("&display=").append(display);
      sbUrl.append("&sort=").append(sort);
      
      url = new URL(sbUrl.toString());
      con = (HttpURLConnection) url.openConnection();
      
      // 요청 메소드
      con.setRequestMethod("GET");  // 반드시 대문자 GET
      
      // 요청 헤더
      con.setRequestProperty("X-Naver-Client-Id", clientId);
      con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
      
      int responseCode = con.getResponseCode();
      if(responseCode != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException(responseCode + " 발생");
      }
      
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      
      StringBuilder sb = new StringBuilder();
      String line = null;
      while((line = reader.readLine()) != null) {
        sb.append(line);
      }
      
      JSONParser jsonParser = new JSONParser();
      JSONObject obj = (JSONObject)jsonParser.parse(sb.toString());
      JSONArray items = (JSONArray)obj.get("items");
      
      System.out.println(items);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
}
