package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidades") // faz o "/cidades" aparecer em todos os @GetMapping
public class CidadesController {

    @GetMapping // localhost:8080/cidades
    public String getCidades(){

        return "Listagem de todas as cidades do Brasil";
    }

    @GetMapping("/{nome}")
    public String getCidadePorNome(@PathVariable String nome){
        return "Retornaria a cidade: " + nome;
    }

    @GetMapping("/cadastrar/{nome}")
    public String cadastrarCidade(@PathVariable String nome){
        return "Cadastro da cidade: " + nome;
    }

    @GetMapping("/remover/{id}")
    public String removerCidade(@PathVariable String id){
        return "Removendo a cidade: " + id;
    }
}
