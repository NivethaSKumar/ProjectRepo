package com.feedback.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class CorsConfiguration.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration /*extends WebMvcConfigurerAdapter*/ implements WebMvcConfigurer{

  /**
   * Adds the cors mappings.
   *
   * @param registry the registry
   */
  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("PUT", "GET", "POST", "DELETE", "HEAD", "OPTIONS");
  }
}
