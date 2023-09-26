package com.gdu.app02.anno01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public Calculator calc() {
    return new Calculator();
  }
  
  // Member
  public Member member() {
    Member member = new Member();
    member.setName("홍길동");
    member.setHeight(180);
    member.setWeight(90);
    member.setCalculator(calc());
    double h = member.getHeight();
    double w = member.getWeight();
    Calculator c = member.getCalculator();
    member.setBmi(c.div(w,  c.div(c.mul(h, h) , 10000)) );
    double bmi = member.getBmi();
    if(bmi < 20) member.setStatus("저체중");
    else if(bmi < 25) member.setStatus("정상");
    else if(bmi < 30) member.setStatus("과체중");
    else member.setStatus("비만");
    return member;
  }
  
  // Fitness
  @Bean
  public Fitness fitness() {
    Fitness f = new Fitness();
    f.setName("가산피트니스");
    f.setMembers(Arrays.asList(member()));
    return f;
  }

}
