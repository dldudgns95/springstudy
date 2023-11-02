package com.gdu.movie.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/movie")
@RequiredArgsConstructor
@RestController
public class MovieController {

  private final MovieService movieService;
  
  @GetMapping(value="/searchAllMovies", produces="application/json")
  public Map<String, Object> getMovieList() {
    return movieService.getMovieList();
  }
  
  @GetMapping(value="/searchMovie", produces="application/json")
  public Map<String, Object> getSearchMoiveList(HttpServletRequest request) {
    System.out.println(movieService.getSearchMovieListk(request));
    return movieService.getSearchMovieListk(request);
  }
  
}
