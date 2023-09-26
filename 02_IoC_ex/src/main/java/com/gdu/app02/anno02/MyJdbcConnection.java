package com.gdu.app02.anno02;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyJdbcConnection {
  
  private String driver;    // oracle.jdbc.OracleDriver
  private String url;       // jdbc:oracle:thin:@127.0.0.1:1521:xe
  private String user;      // GD
  private String password;  // 1111
  
  public Connection getConnection() {
    Connection con = null;
    try {
      Class.forName(driver);  // 어떤 DB를 사용하는지 정하는 driver(그에 따른 url도 정해져있음)
      con = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return con;
  }
  
}

