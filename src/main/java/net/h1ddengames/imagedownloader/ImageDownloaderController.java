package net.h1ddengames.imagedownloader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ImageDownloaderController {
    private String savePath = "C:\\Users\\h1ddengames\\Desktop\\Downloaded Images\\"; // Where do you want to save the images?
    private boolean showBrowserWindow = false; // Should the browser that will download the images be visible?
    private WebDriver driver; // A handle to pass instructions to the browser.

    private void setup() {
        WebDriverManager.chromedriver().version("78.0.3904.70").setup(); // Setup Chrome.

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
        // Wrap the given String as a list, then use downloadImages method to download, finally display the result to the user.
        return downloadImages(url).get(0);
    }

    @PostMapping("/download/default/batch")
    private List<String> downloadImages(@RequestBody String urls) {
        setup(); // Start the browser.
        HelperMethods.generateDirectory(savePath); // Generate the folder that will hold all the images.

        List<String> urlsList = HelperMethods.convertStringToListBySeparatorRemoveEmpty(urls, "\n");
        List<Boolean> downloaded = new ArrayList<>();

        for (int i = 0; i < urlsList.size(); i++) {
            HelperMethods.print(urlsList.get(i));
            driver.navigate().to(urlsList.get(i)); // Go to each URL given in the data.
            WebElement image = driver.findElement(By.tagName("img")); // Find the image to download.
            String imgSrc = image.getAttribute("src"); // Find the URL listed in the src attribute.

            try {
                URL imageURL = new URL(imgSrc); // Store the string as URL object.
                BufferedImage saveImage = ImageIO.read(imageURL); // Save the image in memory.
                ImageIO.write(saveImage, "png", // Save image to disk.
                        new File(savePath + "image" + "-" + HelperMethods.generateRandomString() +".png"));
            } catch (Exception e) { downloaded.add(false); }

            downloaded.add(true);
        }

        tearDown(); // Close the browser.

        return urlsList;
    }
}
