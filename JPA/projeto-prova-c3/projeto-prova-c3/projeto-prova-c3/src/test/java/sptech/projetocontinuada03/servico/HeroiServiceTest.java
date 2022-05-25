package sptech.projetocontinuada03.servico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.projetocontinuada03.entidade.ClasseHeroi;
import sptech.projetocontinuada03.entidade.Heroi;

import static org.junit.jupiter.api.Assertions.*;

/*
    f) (2pt) Crie uma classe de testes unitários automatizados para HeroiService,
    com cenários de teste para o método "getSalario()".
    Faça uma quantidade suficiente de cenários de teste
    */
public class HeroiServiceTest {

    HeroiService heroiService = new HeroiService();

    @Test
    @DisplayName("getSalario() deveria calcular corretamente")
    void calcularInssCenarioValido() {

        /*assertEquals(50, heroiService.getSalario(Heroi);*/
    }
}
