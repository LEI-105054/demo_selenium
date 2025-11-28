package iscteiul.ista.demo_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropPage {
    private WebDriver driver;

    public DragAndDropPage(WebDriver driver) {
        this.driver = driver;
    }

    private By columnA = By.id("column-a");
    private By columnB = By.id("column-b");

    public void dragAToB() {
        WebElement source = driver.findElement(columnA);
        WebElement target = driver.findElement(columnB);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }

    public String getColumnAText() {
        return driver.findElement(columnA).getText();
    }

    public String getColumnBText() {
        return driver.findElement(columnB).getText();
    }
}
