package com.gdu.prj02.cotroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj02.dto.BoardDto;
import com.gdu.prj02.service.BoardService;
import com.gdu.prj02.service.BoardServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // private final BoardService boardService에 Bean이 반드시 전달됨을 보증하는 Annotation이다
@Controller
public class BoardController {
  
  /*
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(HttpServletRequest request) { // 요청 파라미터는 메소드 파라미터로 받습니다.
    int boardNo = Integer.parseInt(request.getParameter("boardNo"));
    String title = request.getParameter("title");
    System.out.println(boardNo + ", " + title);
    return "";
  }
  */
  
  /*
  // @RequestParam 은 생략 가능
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(int boardNo, String title) {
    System.out.println(boardNo + ", " + title);
    return "";
  }
  */
  
  /*
  // 커맨드 객체를 이용한 방식
  @RequestMapping(value="/add.do", method=RequestMethod.GET)
  public String add(BoardDto boardDto) {
    System.out.println(boardDto);
    return "";
  }
  */
  
  // 1. 필드 DI
  // @Autowired  // @Service로 만들어진 Bean을 가져오세요.
  // private BoardService boardService;
  
  //  // 2. Setter 형식 메소드 DI
  //  private BoardService boardService;
  //  
  //  @Autowired  // @Service로 만들어진 Bean을 매개변수로 가져오세요.
  //  public void setBoardService(BoardService boardService) {
  //    this.boardService = boardService;
  //  }
  
  //  // 3. 생성자 DI (권장하는 버전)
  //  private BoardService boardService;
  //  
  //  @Autowired  // @Service로 만들어진 Bean을 매개변수로 가져오세요. (@Autowired 생략 가능)
  //  public BoardController(BoardService boardService) {
  //    super();
  //    this.boardService = boardService;
  //  }
  
  private final BoardService boardService;

  @RequestMapping(value="/add1.do", method=RequestMethod.GET)
  public String add1(HttpServletRequest request, Model model) { // 요청 파라미터는 메소드 파라미터로 받습니다.
    int result = boardService.add1(request);
    model.addAttribute("result", result); // JSP로 전달하는 result 속성입니다.
    return "output";  // output.jsp로 forward (forward JSP 경로를 작성합니다.)
  }
  
  // @RequestParam 은 생략 가능
  @RequestMapping(value="/add2.do", method=RequestMethod.GET)
  public String add2(int boardNo, String title, Model model) {
    model.addAttribute("result",boardService.add2(boardNo, title));
    return "output";
  }
  
  // 커맨드 객체를 이용한 방식
  @RequestMapping(value="/add3.do", method=RequestMethod.GET)
  public String add3(BoardDto boardDto, Model model, RedirectAttributes redirectAttributes) {
    int result = boardService.add3(boardDto);
    // model.addAttribute("result",result); // Model은 redirect 할 때 보내지 못한다.
    redirectAttributes.addFlashAttribute("result", result);  // RedirectAttributes는 redirect 할 때 값을 전달한다.
    return "redirect:/output.do"; // output.do라는 새로운 요청을 처리하시오.
  }
  
  
  @RequestMapping(value="/output.do",method=RequestMethod.GET)
  public String output() {
    return "output";
  }
  
  
  
  
  
  
  
}
