package net.h1ddengames.imagedownloader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ImageDownloaderController {
    private String savePath = "C:\\Users\\h1ddengames\\Desktop\\Downloaded Images\\"; // Where do you want to save the images?
    private boolean showBrowserWindow = false; // Should the browser that will download the images be visible?
    private WebDriver driver; // A handle to pass instructions to the browser.

    private void setup() {
        WebDriverManager.chromedriver().setup(); // Setup Chrome.
        ChromeOptions chromeOptions = new ChromeOptions(); // Setup options for the browser.
        if(showBrowserWindow) {
            // If showBrowserWindow == true that means you want the browser to be visible.
            // In this case, you will need an ad blocker.
            chromeOptions.addExtensions(new File("src\\main\\resources\\ublock_origin_1_22_4_0.crx"));
        } else {
            // If showBrowserWindow == false that means you want the browser to be invisible.
            // In this case you will not need ad blocker since headless chrome does not support extensions.
            chromeOptions.addArguments("--headless");
        }

        driver = new ChromeDriver(chromeOptions); // Open the browser.

        // Tell the browser how long to wait for each type of situation.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    }

    private void tearDown() {
        try { driver.quit(); // Close the browser window.
        } catch (Exception e) { }
    }

    @PostMapping("/download/default/single")
    private String downloadImage(@RequestBody String url) {
        List<String> urls = new ArrayList<>();
        urls.add(url);

        List<String> urlList = downloadImages(urls);
        return urlList.get(0);
    }

    @PostMapping("/download/default/batch")
    private List<String> downloadImages(@RequestBody List<String> urlList) {
        setup(); // Start the browser.
        HelperMethods.generateDirectory(savePath); // Generate the folder that will hold all the images.

        for (int i = 0; i < urlList.size(); i++) {
            driver.navigate().to(urlList.get(i)); // Go to each URL given in the data.
            WebElement image = driver.findElement(By.tagName("img")); // Find the image to download.
            String imgSrc = image.getAttribute("src"); // Find the URL listed in the src attribute.

            try {
                URL imageURL = new URL(imgSrc); // Store the string as URL object.
                BufferedImage saveImage = ImageIO.read(imageURL); // Save the image in memory.
                // Save image to disk.
                ImageIO.write(saveImage, "png",
                        new File(savePath + "image" + "-" + HelperMethods.generateRandomString() +".png"));
            } catch (Exception e) { }

            urlList.add(i, urlList.get(i) + "Downloaded.");
        }

        tearDown();

        return urlList;
    }
}
