package sptech.projetojpa08.controle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import sptech.projetojpa08.entidade.Funcionario;
import sptech.projetojpa08.repositorio.FuncionarioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
Esta anotação indica que o teste precisa um contexto Spring Boot para poder funcionar
Precisamos fazer isso para testes de integração (ex: um endpoint que usa uma Repository)
O atributo "classes" indica quais classes serão realmente testadas

Sem esse anotação não é possível criar Mocks do Spring nos testes de integração
 */
@SpringBootTest(classes = {FuncionarioController.class})
class FuncionarioControllerTest {

    /*
Usamos @Autowired no(s) objeto(s) que realmente vamos testar
     */
    @Autowired
    FuncionarioController controller;

/*
Usamos @MockBean no(s) objeto(s) que seão "mockados"
Um "mock" é um objeto que se comporta como sendo outro, mas não faz o que o de verdade faz.
Nesse caso aqui temos um mock de FuncionarioRepository. Ele só faz de conta que vai no banco de dados, mas não vai.
Podemos (e devemos) indicar ao mock o que ele deve fazer caso seus métodos sejam invocados
*/
    @MockBean
    FuncionarioRepository repository;

    @Test
    @DisplayName("Tabela de funciários vazia, deve retornar 204 sem corpo")
    void getSemResultado() {

        // aqui estamos 'ensinando' ao mock de FuncionarioRepository o que ele deve fazer caso alguém, neste teste, invoque seu "findAll()"
        // neste caso seu "findAll()" vai retornar uma lista vazia
        when(repository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Funcionario>> resposta = controller.get();

        // verificando se o status da resposta é 204
        assertEquals(204, resposta.getStatusCodeValue());

        // verificando se a resposta não tem corpo (corpo == null)
        assertNull(resposta.getBody());
        // assertEquals(null, resposta.getBody());
    }

    @Test
    @DisplayName("Tabela de funciários com valores deve retornar 200 COM corpo")
    void getComResultado() {

        Funcionario f1 = new Funcionario();
        Funcionario f2 = new Funcionario();

// aqui estamos 'ensinando' ao mock de FuncionarioRepository o que ele deve fazer caso alguém, neste teste, invoque seu "findAll()"
// neste caso seu "findAll()" vai retornar uma lista com 2 funcionários (o f1 e o f2)
        when(repository.findAll()).thenReturn(
            List.of(f1, f2)
        );

        ResponseEntity<List<Funcionario>> resposta = controller.get();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(2, resposta.getBody().size());
        assertEquals(f1, resposta.getBody().get(0));
    }

    @Test
    @DisplayName("No filtro por nome, se não encontrar, deve retornar 204 sem corpo")
    void filtroNomeSemResultado() {

// aqui estamos 'ensinando' ao mock de FuncionarioRepository o que ele deve fazer caso alguém, neste teste, invoque seu "findByNomeContains()" com "Zé Ruela" como parâmetro
// neste caso seu "findByNomeContains()" vai retornar uma lista vazia
        String filtro = "Zé Ruela";
        when(repository.findByNomeContains(filtro)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Funcionario>> resposta
                = controller.getPorNome(filtro);

        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
        // assertEquals(null, resposta.getBody());
    }


    @Test
    @DisplayName("No filtro por nome, se encontrar, deve retornar 200 COM corpo")
    void filtroNomeComResultado() {

        // aqui estamos criando um mock de Funcionario (parece, mas não é um Funcionario de verdade)
        Funcionario f1 = mock(Funcionario.class);
        Funcionario f2 = mock(Funcionario.class);

// aqui estamos 'ensinando' ao mock de FuncionarioRepository o que ele deve fazer caso alguém, neste teste, invoque seu "findByNomeContains()" com "Zé Buduia" como parâmetro
// neste caso seu "findByNomeContains()" vai retornar uma lista com 2 mocks de Funcionario
        String filtro = "Zé Buduia";
        when(repository.findByNomeContains(filtro)).thenReturn(
                List.of(f1, f2)
        );

        ResponseEntity<List<Funcionario>> resposta
                = controller.getPorNome(filtro);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(2, resposta.getBody().size());
        assertEquals(f1, resposta.getBody().get(0));
    }

}