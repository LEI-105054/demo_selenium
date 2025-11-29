package suite5;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SamplerPage {

    public void openPage() {
        open("https://demo.vaadin.com/sampler/");

        // Valida que o site carregou
        $(byText("Menu")).shouldBe(visible);
    }

    public void enterInteractionCategory() {
        // CORREÇÃO CRÍTICA:
        // Usamos scrollIntoView(false) para alinhar o elemento pelo FUNDO do ecrã.
        // Isto evita que ele fique escondido atrás do cabeçalho fixo no topo.
        $(byText("Button")).scrollIntoView(false).shouldBe(visible).click();
    }

    public void validateInteractionContents() {
        // Validação: Procura por qualquer botão com a classe Vaadin (.v-button)
        // ou tag <button> que esteja visível na página.
        // O elemento que viste "Click" será apanhado aqui.
        $$(".v-button, button").filter(visible).first().shouldBe(visible);
    }
}