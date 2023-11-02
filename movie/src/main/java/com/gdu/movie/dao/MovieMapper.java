package com.gdu.movie.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.movie.dto.MovieDto;

@Mapper
public interface MovieMapper {
  
  public List<MovieDto> getMovieList();
  public int getMovieCount();
  public int getMovieSearchCount(Map<String, Object> map);
  public List<MovieDto> getSearchMovieList(Map<String, Object> map);
  
}
