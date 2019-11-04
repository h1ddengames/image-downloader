# Image Downloader

- Uses Spring Boot to create a REST API.
- This API takes in URLs separated by newline and downloads them to the given file path.
- The downloading is done by Selenium.
- Selenium goes to the URL given by the data sent to the REST API then downloads the image found at that URL.

## URL Endpoints

- Please put each URL you want to download an image from on its own line.
- An example can be found in src/main/resources/RESTRequestExample.json
- This application will only download the first image it sees so make sure that the first image on the page is the one that you want to download. You can do this by right clicking an image and selecting "Open in new tab" then copying that URL and posting it into a REST API client.

    ```REST API
    POST
    localhost:8080/download/batch
    ```

    ---

- Instead of shutting down the application from the IDE or JAR file if you choose to create one, send a POST request to the following endpoint to shutdown the application properly.

    ```REST API
    POST
    localhost:8080/shutdown
    ```
