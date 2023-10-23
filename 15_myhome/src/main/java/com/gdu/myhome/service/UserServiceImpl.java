package com.gdu.myhome.service;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyJavaMailUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional      // 다중 insert를 위해 사용
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final MySecurityUtils mySecurityUtils;
  private final MyJavaMailUtils myJavaMailUtils;
  
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
        // request.getParameter("referer") = http://localhost:8080/myhome/
        response.sendRedirect(request.getParameter("referer"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("history.go(-1)");
        //out.println("location.href='"+request.getContextPath()+"/login.do'");
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
  
  // 성능 향상을 위해 DB수정이 없는(select만 하는) 메소드들은 @Transactional(readOnly = true)를 사용한다.
  @Transactional(readOnly = true)
  @Override
  public ResponseEntity<Map<String, Object>> checkEmail(String email) {
    
    Map<String, Object> map = Map.of("email", email);
    
    boolean enableEmail = userMapper.getUser(map) == null
                       && userMapper.getLeaveUser(map) == null
                       && userMapper.getInactiveUser(map) == null;
    
    return new ResponseEntity<>(Map.of("enableEmail", enableEmail), HttpStatus.OK);
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(String email) {
    
    // RandomString 생성(6자리, 문자 사용, 숫자 사용)
    String code = mySecurityUtils.getRandomString(6, true, true);
    
    // 메일 전송
    myJavaMailUtils.sendJavaMail(email
                               , "myhome 인증 코드"
                               , "<div>인증코드는 <strong>" + code + "</strong>입니다.</div>");
    
    return new ResponseEntity<>(Map.of("code", code), HttpStatus.OK);
  }
  
  @Override
  public void join(HttpServletRequest request, HttpServletResponse response) {
    
    String email = request.getParameter("email");
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    String event = request.getParameter("event");
    
    UserDto user = UserDto.builder()
                    .email(email)
                    .pw(pw)
                    .name(name)
                    .gender(gender)
                    .mobile(mobile)
                    .postcode(postcode)
                    .roadAddress(roadAddress)
                    .jibunAddress(jibunAddress)
                    .detailAddress(detailAddress)
                    .agree(event.equals("on") ? 1 : 0)
                    .build();
    
    int joinResult = userMapper.insertUser(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(joinResult == 1) {
        request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email)));
        userMapper.insertAccess(email);
        out.println("alert('회원 가입 되었습니다.')");
        out.println("location.href='"+ request.getContextPath() +"/main.do'");
      } else {
        out.println("alert('회원 가입을 실패했습니다.')");
        out.println("history.go(-2)");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    }catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request) {
    
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    String event = request.getParameter("event");
    int agree = event.equals("on") ? 1 : 0;
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                    .name(name)
                    .gender(gender)
                    .mobile(mobile)
                    .postcode(postcode)
                    .roadAddress(roadAddress)
                    .jibunAddress(jibunAddress)
                    .detailAddress(detailAddress)
                    .agree(agree)
                    .userNo(userNo)
                    .build();
    
    int modifyResult = userMapper.updateUser(user);
    if(modifyResult == 1) {
      HttpSession session = request.getSession();
      UserDto sessionUser = (UserDto)session.getAttribute("user");
      sessionUser.setName(name);
      sessionUser.setGender(gender);
      sessionUser.setMobile(mobile);
      sessionUser.setPostcode(postcode);
      sessionUser.setRoadAddress(roadAddress);
      sessionUser.setJibunAddress(jibunAddress);
      sessionUser.setDetailAddress(detailAddress);
      sessionUser.setAgree(agree);
    }
    
    return new ResponseEntity<>(Map.of("modifyResult", modifyResult), HttpStatus.OK);
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> modifyPw(HttpServletRequest request) {
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                    .pw(pw)
                    .userNo(userNo)
                    .build();
    
    int modifyResult = userMapper.updateUserPw(user);
    
    return new ResponseEntity<>(Map.of("modifyResult", modifyResult), HttpStatus.OK);
  }
  
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("userNo"));
    int userNo = Integer.parseInt(opt.orElse("0"));
    
    UserDto user = userMapper.getUser(Map.of("userNo", userNo));
    
    System.out.println(user);
    
    if(user == null) {
      try {
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴를 수행할 수 없습니다.')");
        out.println("location.href='"+ request.getContextPath() +"/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    int insertLeaveResult = userMapper.insertLeaveUser(user);
    int deleteUserResult = userMapper.deleteUser(user);
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertLeaveResult == 1 && deleteUserResult == 1) {
        HttpSession session = request.getSession();
        session.invalidate();
        out.println("alert('회원 탈퇴 되었습니다.')");
        out.println("location.href='"+ request.getContextPath() +"/main.do'");
      } else {
        out.println("alert('회원 탈퇴를 실패했습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    }catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  @Override
  public void findId(HttpServletRequest request, HttpServletResponse response) {
    
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String mobile = request.getParameter("mobile");
    
    Map<String, Object> map = Map.of("name", name, "mobile", mobile);
    UserDto user = userMapper.getUser(map);
    
    if(user != null) {
      try {
        response.sendRedirect(user.getEmail());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원이 없습니다.')");
        out.println("history.back()");
        out.println("</script>");
        
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
