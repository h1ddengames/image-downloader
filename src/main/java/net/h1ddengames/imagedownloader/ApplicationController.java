package net.h1ddengames.imagedownloader;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RestController
public class ApplicationController implements ApplicationContextAware {
    private static ApplicationContext context;

    @PostMapping("/shutdown")
    public String shutdownContext() {
        System.out.println("Shutting down the application.");
        ((ConfigurableApplicationContext) context).close();
        return "Shutting down the application.";
    }

    @PostMapping("/restart")
    public String restartContext() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            ((ConfigurableApplicationContext) context).close();
            context = SpringApplication.run(ImageDownloaderApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
        return "Restarting the application.";
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }

    @PostConstruct
    public void onStartApplication() {
        System.out.println("Startup operations have finished.");
    }

    @PreDestroy
    public void onStopApplication() {
        System.out.println("Clean up operations have finished.");
    }
}