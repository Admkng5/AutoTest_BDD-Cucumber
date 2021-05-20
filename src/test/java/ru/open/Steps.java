package ru.open;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import io.qameta.allure.Step;

public class Steps {

    WebDriver wd;

    @Step
    public void openWD() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        wd.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);
        wd.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    }

    @Step
    public void closeWD() {
        wd.close();
    }

}
