package iscteiul.ista.demo_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicContentPage {
    private WebDriver driver;

    public DynamicContentPage(WebDriver driver) {
        this.driver = driver;
    }

    private By contentBox = By.cssSelector("#content > div:nth-child(1)"); // primeiro bloco dinâmico
    private By refreshButton = By.cssSelector("a[href='/dynamic_content?with_content=static']");

    public String getContentText() {
        return driver.findElement(contentBox).getText();
    }

    public void clickRefresh() {
        driver.findElement(refreshButton).click();
    }
}
