package com.gdu.staff.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.staff.dto.StaffDto;

@Mapper
public interface StaffMapper {
  public int insertStaff(StaffDto staffDto);
  public StaffDto getStaff(String sno);
  public List<StaffDto> getStaffList();
}
