package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputsPage {
    private WebDriver driver;

    // Locator para o campo de input (input[type='number'])
    private By inputField = By.cssSelector("input[type='number']");

    public InputsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void typeNumber(String number) {
        WebElement input = driver.findElement(inputField);
        input.clear();
        input.sendKeys(number);
    }

    // Exemplo de interação diferente: usar as setas do teclado
    public void pressArrowUp() {
        driver.findElement(inputField).sendKeys(Keys.ARROW_UP);
    }

    // Nota: Em inputs, usamos getAttribute("value") e não getText()
    public String getInputValue() {
        return driver.findElement(inputField).getAttribute("value");
    }
}