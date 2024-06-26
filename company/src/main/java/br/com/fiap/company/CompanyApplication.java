package br.com.fiap.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class CompanyApplication {

	public static final Logger logger = LoggerFactory.getLogger(CompanyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}

}
