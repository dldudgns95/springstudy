package com.gdu.app12.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MyInterceptor implements HandlerInterceptor {
  
  // afterCompletion : 작업이 끝난 후 실행
  // postHandle      : 호출 된 후 실행
  // preHandle       : 호출 전에 실행
  
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    // servlet-context에 사용하기 위해 @component 해야함
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<script>");
    out.println("alert('인터셉터가 동작했습니다.')");
    out.println("history.back()");
    out.println("</script>");
    out.flush();
    out.close();
    
    return false;  // return이 false면 컨트롤러의 요청이 처리되지 않는다.
    
  }

}
