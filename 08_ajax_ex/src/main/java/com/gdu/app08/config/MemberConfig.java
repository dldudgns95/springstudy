package com.gdu.app08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app08.dto.MemberDto;

@Configuration
public class MemberConfig {
  
  @Bean
  public MemberDto member1() {
    return new MemberDto(1, "손흥민", 180, 70);
  }
  
  @Bean
  public MemberDto member2() {
    return new MemberDto(2, "황희찬", 190, 90);
  }
  
  @Bean
  public MemberDto member3() {
    return new MemberDto(3, "이강인", 160, 50);
  }

}
