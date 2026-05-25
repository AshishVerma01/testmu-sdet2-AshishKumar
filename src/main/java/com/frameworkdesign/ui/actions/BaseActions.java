package com.frameworkdesign.ui.actions;

import com.frameworkdesign.ui.utility.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static com.frameworkdesign.ui.pages.ShoppingProcessPage.*;

public class BaseActions {
    public static String runType = (System.getProperty("runType") == null) ? "local" : System.getProperty("runType");
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public WebDriver getDriver() {
        return this.driver.get();
    }
    public WebDriverWait getWait() {
        return this.wait.get();
    }

    public WebDriver initiateDriver(String url) throws IOException {
        WebDriver localDriver;
        String browserName = System.getProperty("browser");
        if (browserName == null || browserName.isBlank()) {
            browserName = ConfigManager.getProperty("browser");
        }
        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            localDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            localDriver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            localDriver = new EdgeDriver();
        } else {
            throw new RuntimeException("Invalid browser: " + browserName);
        }

        driver.set(localDriver);
        getDriver().get(url);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait.set(new WebDriverWait(localDriver, Duration.ofSeconds(5)));
        return getDriver();
    }

    public WebDriver initiateDriver(String url, String runType) throws IOException, URISyntaxException {
        if(runType.equals("Grid")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox"); //"chrome", "firefox", "safari", "edge"
//            capabilities.setCapability(CapabilityType.BROWSER_VERSION, "114.0.1");
//            capabilities.setCapability(CapabilityType.PLATFORM_NAME, "Linux"); //"Windows 11", "macOS Sonoma", "Linux"

            WebDriver localDriver = new RemoteWebDriver(new URI("http://192.168.1.7:4444").toURL(), capabilities);

            driver.set(localDriver);
            getDriver().get(url);
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            wait.set(new WebDriverWait(localDriver, Duration.ofSeconds(5)));
            return getDriver();
        } else {
            return initiateDriver(url);
        }
    }

    public String login(String username, String password) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(loginForm)).isDisplayed();
        getWait().until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);
        getWait().until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(password);
        getWait().until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-title"))).getText();
    }

    public void tearDown() {
        getDriver().quit();
        driver.remove();
        wait.remove();
    }
}
