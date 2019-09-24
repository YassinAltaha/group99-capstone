package ca.sheridancollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class CapstoneSiteApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CapstoneSiteApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CapstoneSiteApplication.class, args);
	}

}
