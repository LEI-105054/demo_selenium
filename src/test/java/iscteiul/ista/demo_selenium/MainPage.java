package iscteiul.ista.demo_selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
    public WebElement seeDeveloperToolsButton;

    // Seletor corrigido para evitar interceção
    @FindBy(css = "[data-test='suggestion-link']")
    public WebElement findYourToolsButton;

    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
    public WebElement toolsMenu;

    @FindBy(css = "[data-test='site-header-search-action']")
    public WebElement searchButton;

    @FindBy(xpath = "//button[contains(text(), 'Accept All')]")
    public WebElement acceptCookiesButton;

    public void acceptCookies() {
        try {
            if (acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
            }
        } catch (Exception e) {

        }
    }

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}