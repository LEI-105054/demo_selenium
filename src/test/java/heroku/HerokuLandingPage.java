package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HerokuLandingPage {
    private WebDriver driver;
    private final String URL = "https://the-internet.herokuapp.com/";

    // Locators para os novos exemplos
    private By dropdownLink = By.linkText("Dropdown");
    private By inputsLink = By.linkText("Inputs");

    public HerokuLandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    // Métodos de navegação
    public void clickDropdown() {
        driver.findElement(dropdownLink).click();
    }

    public void clickInputs() {
        driver.findElement(inputsLink).click();
    }
}