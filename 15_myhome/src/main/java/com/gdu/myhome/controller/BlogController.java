package com.gdu.myhome.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
import com.gdu.myhome.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class BlogController {
  
  private final BlogService blogService;
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    blogService.loadBlogList(request, model);
    return "blog/list";
  }
  
  @GetMapping("/write.form")
  public String writeForm() {
    return "blog/write";
  }
  
  @ResponseBody
  @PostMapping(value="/imageUpload.do", produces="application/json")
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {  // MultipartHttpServletRequest : 이미지를 포함한 모든 파일을 받을 수 있는 request
    return blogService.imageUpload(multipartRequest);
  }
  
  @PostMapping("/addBlog.do")
  public String addBlog(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = blogService.addBlog(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/blog/list.do";
  }
  
  @GetMapping("/increseHit.do")
  public String increseHit(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo) {
    int increseResult = blogService.increseHit(blogNo);
    if(increseResult == 1) {
      return "redirect:/blog/detail.do?blogNo=" + blogNo;
    } else {
      return "redirect:/blog/list.do";
    }
  }
  
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo
                     , Model model) {
    BlogDto blog = blogService.getBlog(blogNo);
    model.addAttribute("blog", blog);
    List<BlogImageDto> blogImageList = blogService.getBlogImageList(blogNo);
    model.addAttribute("blogImageList", blogImageList);
    System.out.println("blogImageList : " + blogImageList);
    return "blog/detail";
  }
  
  @PostMapping("/edit.form")
  public String editForm(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo
                       , Model model) {
    BlogDto blog = blogService.getBlog(blogNo);
    model.addAttribute("blog", blog);
    return "blog/edit";
    
  }
  
  @PostMapping("/modifyBlog.do")
  public String modifyBlog(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int modifyResult = blogService.modifyBlog(request);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/blog/detail.do?blogNo=" + request.getParameter("blogNo");
  }
  
  @PostMapping("/removeBlog.do")
  public String modifyBlog(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo
                         , RedirectAttributes redirectAttributes) {
    int removeResult = blogService.removeBlog(blogNo);
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/blog/list.do";
  }
  
  @GetMapping("/removeBlogImage.do")
  public String removeBlogImage(Model model, RedirectAttributes redirectAttributes) {
    System.out.println("removeBlogImage : " + model.getAttribute("blogNo"));
    int removeResult = blogService.removeBlogImage((int)model.getAttribute("blogNo"));
    redirectAttributes.addFlashAttribute("removeResult", removeResult);
    return "redirect:/blog/list.do";
  }
  
  
  @ResponseBody
  @PostMapping(value="/addComment.do", produces="application/json")
  public Map<String, Object> addComment(HttpServletRequest request) {
    return blogService.addComment(request);
  }
  
  @ResponseBody
  @GetMapping(value="/commentList.do", produces="application/json")
  public Map<String, Object> commentList(HttpServletRequest request) {
    return blogService.loadCommentList(request);
  }
  
  @ResponseBody
  @PostMapping(value="/addCommentReply.do", produces="application/json")
  public Map<String, Object> addCommentReply(HttpServletRequest request) {
    return blogService.addCommentReply(request);
  }
  
  @ResponseBody
  @PostMapping(value="/removeComment.do", produces="application/json")
  public Map<String, Object> removeComment(@RequestParam(value="commentNo", required=false, defaultValue="0") int commentNo) {
    System.out.println(commentNo);
    return blogService.removeComment(commentNo);
  }
  
  
  
  
  
  
  
  
}
