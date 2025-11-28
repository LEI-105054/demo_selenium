package heroku;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicContentTest {
    private WebDriver driver;
    private DynamicContentPage dynamicPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/dynamic_content");
        dynamicPage = new DynamicContentPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void contentChangesAfterRefresh() {
        String initialContent = dynamicPage.getContentText();

        dynamicPage.clickRefresh();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !dynamicPage.getContentText().equals(initialContent));

        String newContent = dynamicPage.getContentText();
        assertNotEquals(initialContent, newContent);

        System.out.println("Conteúdo inicial: " + initialContent);
        System.out.println("Novo conteúdo: " + newContent);
    }
}
