package com.gdu.prj02.dao;

import java.sql.Connection;

import org.springframework.stereotype.Repository;

import com.gdu.prj02.dto.BoardDto;

@Repository  // new BoardDao()를 해서 Bean으로 저장하세요.
public class BoardDao {
  
  private Connection con = null; // DB를 연결하기 위한 Connection
  
  public int insertBoard1(BoardDto boardDto) {
    System.out.println(boardDto);
    return 1;
  }
  
  public int insertBoard2(BoardDto boardDto) {
    System.out.println(boardDto);
    return 1;
  }
  
  public int inserBoard3(BoardDto boardDto) {
    System.out.println(boardDto);
    return 1;
  }

}
