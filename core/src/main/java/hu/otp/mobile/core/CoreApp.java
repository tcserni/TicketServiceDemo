package hu.otp.mobile.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class CoreApp {

	public static void main(String[] args) {
		SpringApplication.run(CoreApp.class, args);
	}
}
