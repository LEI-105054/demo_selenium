package iscteiul.ista.demo_selenium;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--window-size=1920,1080"); // Força layout Desktop

        driver = new ChromeDriver(options);
        // Timeout implícito a 0 para não atrasar as verificações manuais
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.jetbrains.com/");

        // Espera estabilização do Chrome
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // Remove cookies
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "const banners = document.querySelectorAll('[data-test=\"cookie-banner\"], .ch2-container');" +
                            "banners.forEach(b => b.remove());"
            );
        } catch (Exception e) { /* Ignora */ }

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
    public void search() {
        // 1. Encontrar o botão certo
        WebElement visibleButton = getVisibleSearchButton();

        // 2. Clicar
        smartClick(visibleButton);

        // 3. Esperar pelo input (com retry se necessário)
        WebElement searchField;
        try {
            searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='search-input']")));
        } catch (Exception e) {
            System.out.println("Input não abriu. Tentando clicar novamente no botão visível...");
            smartClick(visibleButton);
            // Procura genérica
            searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search']")));
        }

        // 4. Interagir
        searchField.clear();
        searchField.sendKeys("Selenium");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='full-search-button']")));
        smartClick(submitButton);

        // 5. Validar
        WebElement searchPageField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@data-test='search-input' or @type='search']")
        ));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void toolsMenu() {
        smartClick(mainPage.toolsMenu);
        WebElement menuPopup = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='main-submenu']")));
        assertNotNull(menuPopup);
    }

    @Test
    public void navigationToAllTools() {
        smartClick(mainPage.seeDeveloperToolsButton);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-test='suggestion-action'] | //a[contains(@href, '/products/')]")));
        smartClick(mainPage.findYourToolsButton);

        WebElement productsList = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("products-page")));
        assertTrue(productsList.isDisplayed());
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }
}