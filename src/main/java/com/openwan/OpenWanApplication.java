package com.openwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by   on 2017/3/10.
 */
@SpringBootApplication
@ServletComponentScan
public class OpenWanApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenWanApplication.class,args);
  }

}
