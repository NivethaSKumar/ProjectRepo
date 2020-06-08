package com.example.demo;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = { "com.feedback" })
public class FeedbackSystemApplicationTest {

	@Test
	public void contextLoads() {
	}

}
