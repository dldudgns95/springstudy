package com.gdu.myhome.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.InactiveUserDto;
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
  
  private final String  client_id = "4DEmLZE_bTCwOOIcBI5a";
  private final String client_secret = "wrkcYZS46r";
  
  @Override
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String email = request.getParameter("email");
    String pw =  mySecurityUtils.getSHA256(request.getParameter("pw"));
    
    Map<String, Object> map = Map.of("email", email, "pw", pw);
    
    HttpSession session = request.getSession();
    
    // 휴면 계정인지 확인
    InactiveUserDto inactiveUser = userMapper.getInactiveUser(map);
    if(inactiveUser != null) {
      // 정보저장 (inactiveUser)
      session.setAttribute("inactiveUser", inactiveUser);
      // 이동 (/user/active.form) -> user/active.jsp
      response.sendRedirect(request.getContextPath() + "/user/active.form");
    }
    
    // 정상적인 로그인 처리하기
    UserDto user = userMapper.getUser(map);
    
    if(user != null) {
      // 로그인이 되었다면 session에 user가 있어야 한다.
      session.setAttribute("user", user);
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
  public String getNaverLoginURL(HttpServletRequest request) throws Exception {
    // 네이버로그인-1 (네이버 로그인 개발 가이드 )
    // 3.4.2 네이버 로그인 연동 URL 생성하기를 위해 redirect_uri(URLEncoder, state(SecureRandom) 값의 전달이 필요하다.
    // redirect_uri : 네이버로그인-2를 처리할 서버 경로를 작성한다.
    // redirect_uri 값은 네이버 로그인 Callback URL에도 동일하게 등록해야 한다.
    String apiURL = "https://nid.naver.com/oauth2.0/authorize";
    String response_type = "code";
    String redirect_uri =  URLEncoder.encode("http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do", "UTF-8");
    String state = new BigInteger(130, new SecureRandom()).toString();
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?response_type=").append(response_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&redirect_uri=").append(redirect_uri);
    sb.append("&state=").append(state);
    
    return sb.toString();
  }

  @Override
  public String getNaverLoginAccessToken(HttpServletRequest request) throws Exception {
    // 네이버로그인-2
    // 접근 토큰 발급 요청
    // 네이버로그인-2를 수행하기 위해서는 네이버로그인-1의 응답 결과인 code와 state가 필요하다.
    
    // 네이버로그인-1의 응답 결과(access_token을 얻기 위해 요청 파라미터로 사용해야 함)
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    
    String apiURL = "https://nid.naver.com/oauth2.0/token";
    String grant_type = "authorization_code";  // access_token 발급 받을 때 사용하는 값(갱신이나 삭제시에는 값이 달라진다.)
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?grant_type=").append(grant_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&client_secret=").append(client_secret);
    sb.append("&code=").append(code);
    sb.append("&state=").append(state);
    
    // 요청
    URL url = new URL(sb.toString());
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");  // 반드시 대문자로 작성
    
    // 응답
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    JSONObject obj = new JSONObject(responseBody.toString());
    
    return obj.getString("access_token");
    
  }
  
  @Override
  public UserDto getNaverProfile(String accessToken) throws Exception {
    // 네이버 로그인-3
    // 접근 토큰을 전달한 뒤 사용자의 프로필 정보(이름, 이메일, 성별, 휴대전화번호) 받아오기
    // 요청 헤더에 Authorization: Bearer accessToken 정보를 저장하고 요청함
    
    // 요청
    String apiURL = "https://openapi.naver.com/v1/nid/me";
    URL url = new URL(apiURL);
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", "Bearer " + accessToken);
    
    // 응답
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    // 응답 결과(프로필을 JSON으로 응답) -> UserDto 객체
    JSONObject obj = new JSONObject(responseBody.toString());
    JSONObject response = obj.getJSONObject("response");
    UserDto user = UserDto.builder()
                    .email(response.getString("email"))
                    .name(response.getString("name"))
                    .gender(response.getString("gender"))
                    .mobile(response.getString("mobile"))
                    .build();
    
    return user;
  }
  
  @Override
  public UserDto getUser(String email) {
    return userMapper.getUser(Map.of("email", email));
  }
  
  @Override
  public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
    
    String email  = request.getParameter("email");
    String name   = request.getParameter("name");
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String event  = request.getParameter("event");
    
    UserDto user = UserDto.builder()
                    .email(email)
                    .name(name)
                    .gender(gender)
                    .mobile(mobile.replace("-", ""))
                    .agree(event != null ? 1 : 0) // checkbox는 체크하면 'on'이 전달되고, 체크하지 않으면 값이 아무것도 전달되지 않는다.
                    .build();
    
    int naverJoinResult = userMapper.insertNaverUser(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(naverJoinResult == 1) {
        // 회원가입을 하면 강제 로그인
        request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email)));
        userMapper.insertAccess(email);
        out.println("alert('네이버 간편 가입이 완료되었습니다.')");
      } else {
        out.println("alert('네이버 간편 가입이 실패했습니다.')");
      }
      out.println("location.href='"+ request.getContextPath() +"/main.do'");
      out.println("</script>");
      out.flush();
      out.close();
      
    }catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void naverLogin(HttpServletRequest request, HttpServletResponse response, UserDto naverProfile) throws Exception {
    
    String email = naverProfile.getEmail();
    Map<String, Object> map = Map.of("email", email);
    
    HttpSession session = request.getSession();
    
    // 정상적인 로그인 처리하기
    UserDto user = userMapper.getUser(map);
    
    if(user != null) {
      // 로그인이 되었다면 session에 user가 있어야 한다.
      session.setAttribute("user", user);
      userMapper.insertAccess(email); 
    
      // 응답이 다양할 경우에는 controller를 거치지 않고 response로 직접 반환한다.
      try {
        // request.getParameter("referer") = http://localhost:8080/myhome/
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
        // 회원가입을 하면 강제 로그인
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
  
  @Override
  public void inactiveUserBatch() {
    userMapper.insertInactiveUser();
    userMapper.deleteUserForInactive();
    
  }
  
  @Override
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    
    InactiveUserDto inactiveUser = (InactiveUserDto)session.getAttribute("inactiveUser");
    String email = inactiveUser.getEmail();
    
    int insertActiveUserResult = userMapper.insertActiveUser(email);
    int deleteInactiveUserResult = userMapper.deleteInactiveUser(email);
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertActiveUserResult == 1 && deleteInactiveUserResult == 1) {
        out.println("alert('휴면계정이 복구되었습니다. 계정 활성화를 위해 바로 로그인 해 주세요.')");
        out.println("location.href='"+request.getContextPath()+"/main.do'"); // 로그인 페이지로 보내면 로그인 후 다시 휴면 계정 복구 페이지로 돌아오므로 main으로 이동시킨다.
      } else {
        out.println("alert('휴면계정의 복구를 실패했습니다. 다시 시도해주세요.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
