package com.gdu.myhome.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final MySecurityUtils mySecurityUtils;
  
  @Override
  public void login(HttpServletRequest request, HttpServletResponse response) {
    
    String email = request.getParameter("email");
    String pw =  mySecurityUtils.getSHA256(request.getParameter("pw"));
    
    Map<String, Object> map = Map.of("email", email, "pw", pw);
    UserDto user = userMapper.getUser(map);
    
    if(user != null) {
      // 로그인이 되었다면 session에 user가 있어야 한다.
      request.getSession().setAttribute("user", user);
      userMapper.insertAccess(email); 
    
    // 응답이 다양할 경우에는 controller를 거치지 않고 response로 직접 반환한다.
      try {
        response.sendRedirect(request.getContextPath() + "/main.do");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='"+request.getContextPath()+"/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    
    HttpSession session = request.getSession();
    System.out.println(session.getAttribute("user"));
    session.invalidate();   // 세션 초기화
    try {
      response.sendRedirect(request.getContextPath() + "/main.do");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
