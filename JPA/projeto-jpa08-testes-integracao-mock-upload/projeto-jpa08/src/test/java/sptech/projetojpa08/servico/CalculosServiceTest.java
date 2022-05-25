package sptech.projetojpa08.servico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
Uma classe de teste deve estar na pasta src/test
O nome da classe deve terminar com Test (é uma convenção do Maven)
 */
class CalculosServiceTest {

    // este objeto será usado em todos os testes da classe
    CalculosService service = new CalculosService();

    @Test // esta anotação indica para o JUnit que o método é de teste
    @DisplayName("calcularInss() deve calcular corretamente")
    void calcularInss_deveriaCalcularCorretamente() {
        double salario1 = 500;
        double inss1 = 25;
        double inssCalculado1 = service.calcularInss(salario1);
        // assert -> verifique
        // verificando se o inssCalculado1 é igual a inss1
        // o 1º parâmetro é o valor ESPERADO
        // o 2º parâmetro é o valor DA EXECUÇÃO
        assertEquals(inss1, inssCalculado1);

        double salario2 = 2000;
        double inss2 = 100;
        double inssCalculado2 = service.calcularInss(salario2);
        assertEquals(inss2, inssCalculado2);

        // forma mais curta
        // verificando se o service.calcularInss(5000.0) é igual a 500
        assertEquals(500, service.calcularInss(5000.0));
    }

    @Test
    @DisplayName("calcularInss() com salário menor que 500 deve dar erro")
    void calcularInss_salarioInvalidoErro() {
        /*
        verificando se a execução de service.calcularInss(-100.0)
        lança uma IllegalArgumentException
         */
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularInss(-100.0);
        });

        /*
        verificando se a execução de service.calcularInss(499.99)
        lança uma IllegalArgumentException com a mensagem de erro
        "Salário dever ser a partir de 500"
         */
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            service.calcularInss(499.99);
        });

        assertEquals("Salário dever ser a partir de 500", excecao.getMessage());
    }

    @Test
    @DisplayName("receberaAuxilio() deve verificar o direito corretamente")
    void receberaAuxilio_deveVerificarCorretamente() {
        // este de baixo pode ser simplificado pelos da sequência
        assertEquals(true, service.receberaAuxilio(500, 0));
        assertTrue(service.receberaAuxilio(1000, 2));
        assertTrue(service.receberaAuxilio(1999.99, 0));
        assertTrue(service.receberaAuxilio(3000, 4));
        assertTrue(service.receberaAuxilio(3999.99, 4));

        // assertEquals(false, service.receberaAuxilio(3000, 0));
        assertFalse(service.receberaAuxilio(3000, 0));
        assertFalse(service.receberaAuxilio(3000, 2));
    }

    @Test
    @DisplayName("receberaAuxilio() com salário menor que 500 ou dependentes menores que 0 deve dar erro")
    void receberaAuxilio_salarioDependentesInvalidosErro() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            service.receberaAuxilio(499.0, 1);
        });
        assertEquals("O salário deve ser a partir de 500 e os dependentes a partir de 0", excecao.getMessage());

        excecao = assertThrows(IllegalArgumentException.class, () -> {
            service.receberaAuxilio(1000, -2);
        });
        assertEquals("O salário deve ser a partir de 500 e os dependentes a partir de 0", excecao.getMessage());

        excecao = assertThrows(IllegalArgumentException.class, () -> {
            service.receberaAuxilio(499, -2);
        });
        assertEquals("O salário deve ser a partir de 500 e os dependentes a partir de 0", excecao.getMessage());
    }
}