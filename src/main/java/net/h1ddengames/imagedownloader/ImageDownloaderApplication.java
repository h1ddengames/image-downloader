package net.h1ddengames.imagedownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ImageDownloaderApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ImageDownloaderApplication.class, args);
	}
}
