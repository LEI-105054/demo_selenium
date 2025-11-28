package iscteiul.ista.demo_selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragAndDropTest {
    private WebDriver driver;
    private DragAndDropPage dragPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        dragPage = new DragAndDropPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testDragAndDrop() {
        // Validar estado inicial
        assertEquals("A", dragPage.getColumnAText());
        assertEquals("B", dragPage.getColumnBText());

        // Executar drag and drop
        dragPage.dragAToB();

        // Validar que os elementos trocaram de posição
        assertEquals("B", dragPage.getColumnAText());
        assertEquals("A", dragPage.getColumnBText());
    }
}
