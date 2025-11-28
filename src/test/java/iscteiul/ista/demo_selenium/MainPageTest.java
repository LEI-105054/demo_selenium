package iscteiul.ista.demo_selenium;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    private void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }

    public void acceptCookiesIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Espera o botão "Accept All" estar presente e clicável
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.ch2-allow-all-btn")
            ));
            acceptButton.click();

            // Opcional: espera o diálogo sumir
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.id("ch2-dialog")
            ));

            System.out.println("Cookies accepted.");
        } catch (Exception e) {
            // Se o banner não aparecer, ignora e continua
            System.out.println("No cookie banner found.");
        }
    }
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.jetbrains.com/");

        waitForPageLoad();
        acceptCookiesIfPresent();
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void smartClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    // MÉTODO NOVO: Procura explicitamente o botão visível
    private WebElement getVisibleSearchButton() {
        // Procura todos os botões que pareçam ser de pesquisa
        List<WebElement> candidates = driver.findElements(By.cssSelector("button[data-test='site-header-search-action'], [aria-label='Open search']"));

        System.out.println("Botões encontrados: " + candidates.size());

        for (WebElement btn : candidates) {
            // Verifica se tem tamanho e está visível
            if (btn.isDisplayed() && btn.getSize().getWidth() > 0) {
                System.out.println("Botão visível encontrado!");
                return btn;
            }
        }
        throw new RuntimeException("Nenhum botão de pesquisa visível encontrado.");
    }
    
    @Test
    public void search() throws InterruptedException {
        // 1. Clicar na lupa
        System.out.println("A clicar na lupa...");
        mainPage.searchButton.click();

        WebElement searchField = driver.findElement(By.cssSelector("[data-test-id='search-input']"));
        searchField.sendKeys("Selenium");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='full-search-button']")));
        smartClick(submitButton);

        WebElement searchPageField = driver.findElement(By.cssSelector("input[data-test-id='search-input']"));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement visibleMenu = wait.until(driver -> {
            for (WebElement el : driver.findElements(By.cssSelector("div[data-test='main-submenu']"))) {
                if (el.isDisplayed()) {
                    return el; 
                }
            }
            return null;
        });

        assertTrue(visibleMenu.isDisplayed());
    }


    @Test
    public void navigationToAllTools() {
        // Open the submenu
        mainPage.seeDeveloperToolsButton.click();

        // Wait for the visible submenu
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement visibleMenu = wait.until(driver -> {
            for (WebElement el : driver.findElements(By.cssSelector("div[data-test='main-submenu']"))) {
                if (el.isDisplayed()) {
                    return el;
                }
            }
            return null;
        });

        // Find the first clickable suggestion-action in the visible menu
        WebElement suggestionAction = wait.until(ExpectedConditions.elementToBeClickable(
                visibleMenu.findElement(By.cssSelector("a[data-test='suggestion-link']"))
        ));

        // Click it
        suggestionAction.click();

        // Assert products page loaded
        WebElement productsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-page")));
        assertTrue(productsList.isDisplayed());
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }

}
