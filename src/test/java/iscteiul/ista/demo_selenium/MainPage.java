package iscteiul.ista.demo_selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {

    // Menu "Developer Tools"
    @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
    public WebElement toolsMenu;

    @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
    public WebElement seeDeveloperToolsButton;

    // Botão "Find your tool"
    @FindBy(xpath = "//*[@data-test='suggestion-action'] | //a[contains(@href, '/products/') and contains(@class, 'mainSubmenuSuggestion')]")
    public WebElement findYourToolsButton;

    // CORREÇÃO: Aceita múltiplos seletores para o botão de pesquisa para garantir compatibilidade
    @FindBy(css = "button[data-test='site-header-search-action'], button[data-test='search-button'], [aria-label='Open search']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}