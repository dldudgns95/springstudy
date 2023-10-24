package com.gdu.staff.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.staff.dto.StaffDto;
import com.gdu.staff.service.StaffService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StaffController {
  
  private final StaffService staffService;
  
  @PostMapping(value="/add.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> add(StaffDto staff) {
    return staffService.registerStaff(staff);
  }
  
  @GetMapping(value="/list.do", produces="application/json")
  public ResponseEntity<List<StaffDto>> staffList() {
    return staffService.staffList();
  }
  
  @PostMapping(value="/detail.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> detail(@RequestParam String sno) {
    return staffService.staffDetail(sno);
  }
  
}
