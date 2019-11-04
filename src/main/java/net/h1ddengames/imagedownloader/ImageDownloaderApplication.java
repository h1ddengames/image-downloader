package net.h1ddengames.imagedownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ImageDownloaderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ImageDownloaderApplication.class, args);
		ctx.getBean(ImageDownloaderController.class);
	}

}
