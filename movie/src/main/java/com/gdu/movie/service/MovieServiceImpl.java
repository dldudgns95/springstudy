package com.gdu.movie.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

  private final MovieMapper movieMapper;
  
  @Override
  public Map<String, Object> getMovieList() {
    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    return Map.of("message", "전체 " + movieCount + "개의 목록을 가져왔습니다."
                , "list", list
                , "status", 200);
  }
  
  @Override
  public Map<String, Object> getSearchMovieListk(HttpServletRequest request) {
    String column = request.getParameter("column");
    System.out.println("column: " + column);
    String searchText = request.getParameter("searchText");
    System.out.println("searchText: " + searchText);
    Map<String, Object> map = Map.of("column", column, "searchText", searchText);
    int count = movieMapper.getMovieSearchCount(map);
    System.out.println("count: " + count);
    if(count == 0) {
      return Map.of("message", searchText + " 검색 결과가 없습니다."
                  , "list", ""
                  , "status", 500);
    } else {
      List<MovieDto> movieList = movieMapper.getSearchMovieList(map);
      return Map.of("message", "전체 " + count + "개의 목록을 가져왔습니다."
                  , "list", movieList
                  , "status", 200);
    }
    
  }
  
}
