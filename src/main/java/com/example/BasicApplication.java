package com.example;

import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
//             MultipartFile file = new MultipartFile(BasicApplication.class.getResourceAsStream("/CNAB.txt"));
		SpringApplication.run(BasicApplication.class, args);
	}

}
