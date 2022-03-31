package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paises") // faz o "/paises" aparecer em todos os @GetMapping
public class PaisesController {

    Pais teste = new Pais();

    @GetMapping("/medalhas")
    public Pais getPais(){

        teste.setMedalhasOuro(2);
        teste.setMedalhasPrata(0);
        teste.setMedalhasBronze(10);

        return teste;
    }

    /* Lista Imutável
    List<Pais> listaPaises = List.of(
            new Pais("Alemanha", 1, 2, 5),
            new Pais("Brasil", 4, 10, 35),
            new Pais("Canadá", 0, 10, 4),
            new Pais("Japão", 2, 30, 34)
    );
     */

    List<Pais> listaPaises = new ArrayList<>();

    @GetMapping("/listar")
    public List<Pais> getTodosPaises(){
        return listaPaises;
    }

    @GetMapping // localhost:8080/paises
    public String getPaises(){

        return "Listagem de todos os países do Brasil";
    }

    @GetMapping("/cadastrar/{nome}")
    public String cadastrarPais(@PathVariable String nome){

        //mais avançado
        //listaPaises.add(new Pais(nome, 0, 0, 0));

        Pais paisNovo = new Pais(nome, 0, 0, 0);
        listaPaises.add(paisNovo);

        return "Cadastro do país: " + nome + " realizado com sucesso";
    }

    @GetMapping("/buscar/{indiceLista}")
    public Pais buscarPais(@PathVariable Integer indiceLista){

        return listaPaises.get(indiceLista);
    }

    @GetMapping("/remover/{id}")
    public String removerPais(@PathVariable String id){
        return "Removendo o país: " + id;
    }
}
