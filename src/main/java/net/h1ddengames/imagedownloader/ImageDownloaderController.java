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
import java.util.concurrent.TimeUnit;

@RestController
public class ImageDownloaderController {
    private String savePath = "C:\\Users\\h1ddengames\\Desktop\\Downloaded Images\\"; // Where do you want to save the images?
    private int lengthOfRandomString = 10; // How long should the random string be for the filename?
    private boolean useLettersInRandomString = true; // Should there be letters in the random string?
    private final boolean useNumbersInRandomString = true; // Should there be numbers in the random string?

    private boolean showBrowserWindow = false; // Should the browser that will download the images be visible?
    private WebDriver driver; // A handle to pass instructions to the browser.

    private void setup() {
        WebDriverManager.chromedriver().version("76.0.3809.68").setup(); // Set the version of chrome and open the browser.
        ChromeOptions chromeOptions = new ChromeOptions();
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
        try {
            driver.quit(); // Close the browser window.
        } catch (Exception e) { }
    }

    @PreDestroy
    private void onStopApplication() {
        System.out.println("Stopping the application.");
        // Save all URLs downloaded in this session into a file and save to local disk.
    }

    @PostMapping("/download/batch")
    private String downloadImages(@RequestBody String urls) {
        String[] urlArray = urls.split("\n"); // Split the data given from REST client by new line into an array.
        setup(); // Start the browser
        generateDirectory(savePath); // Generate the folder that will hold all the images.

        for(String s : urlArray) {
            driver.navigate().to(s); // Go to each URL given in the data.
            WebElement image = driver.findElement(By.tagName("img")); // Find the image to download.
            String imgSrc = image.getAttribute("src"); // Find the URL listed in the src attribute.

            try {
                URL imageURL = new URL(imgSrc); // Store the string as URL object.
                BufferedImage saveImage = ImageIO.read(imageURL); // Save the image in memory.
                // Save image to disk.
                ImageIO.write(saveImage, "png",
                        new File(savePath + "image" + "-" + generateRandomString() +".png"));
            } catch (Exception e) { }

            // Download the image found at the given URL.
            System.out.println("On page: " + driver.getCurrentUrl());
        }

        tearDown();

        return convertToString(urlArray);
    }

    // Generates a directory with the given file path.
    public void generateDirectory(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()){
            directory.mkdir();
        } else { System.out.println("Already created the file."); }
    }

    // The default version of generateRandomString(), it will use the values listed near the top of this class.
    private String generateRandomString() {
        return generateRandomString(lengthOfRandomString, useLettersInRandomString, useNumbersInRandomString);
    }

    // Generate random string with the given length.
    private String generateRandomString(int length, boolean letters, boolean numbers) {
        return RandomStringUtils.random(length, letters, numbers);
    }

    // Print the contents of the data the user of the REST API gave.
    private String convertToString(String[] arr) {
        StringBuilder result = new StringBuilder();

        for(String s : arr ) {
            result.append("Downloaded: ").append(s).append("\n");
        }

        return result.toString();
    }
}
