package iscteiul.ista.demo_selenium;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys; // IMPORTANTE: Adiciona esta importação
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.jetbrains.com/");

        mainPage = new MainPage(driver);
        mainPage.acceptCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void search() throws InterruptedException {
        // 1. Clicar na lupa
        System.out.println("A clicar na lupa...");
        mainPage.searchButton.click();

        // 2. Pequena pausa para garantir que a barra abriu totalmente
        Thread.sleep(2000);

        // 3. ESTRATÉGIA DO LOOP:
        // Vamos buscar TODOS os inputs da página.
        java.util.List<WebElement> allInputs = driver.findElements(By.tagName("input"));

        System.out.println("Inputs encontrados: " + allInputs.size());

        boolean encontrou = false;

        for (WebElement input : allInputs) {
            // Verifica se o input está visível para o utilizador e se é editável
            if (input.isDisplayed() && input.isEnabled()) {
                try {
                    // Tenta limpar e escrever
                    System.out.println("Tentando escrever no input visível...");
                    input.sendKeys("Selenium");
                    input.sendKeys(Keys.ENTER);
                    encontrou = true;
                    break;
                } catch (Exception e) {
                    System.out.println("Este input estava visível mas deu erro. A tentar o próximo...");
                }
            }
        }

        if (!encontrou) {
            fail("Erro: Não foi encontrado nenhum campo de input visível na página após clicar na lupa.");
        }

        // 4. Validar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Selenium"));
        assertTrue(driver.getTitle().contains("Selenium"));
    }

    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Verifica se um item do menu aparece (ex: IntelliJ IDEA)
        WebElement menuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.partialLinkText("IntelliJ IDEA")
        ));
        assertTrue(menuItem.isDisplayed());
    }

    @Test
    public void navigationToAllTools() {
        mainPage.seeDeveloperToolsButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(mainPage.findYourToolsButton)).click();

        WebElement productsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-page")));
        assertTrue(productsList.isDisplayed());
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }
}