package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstudanteController {

/*

3. Crie uma API com as seguintes chamadas

a) /cadastrar-estudante/{nome} onde você vai "cadastrar" um estudante. Essa chamada retorna a frase "Estudante NOME cadastrado(a) com sucesso"

b) /cadastrar-notas/{n1}/{n2} onde você vai "cadastrar" as 2 notas de um estudante. Essa chamada retorna a frase "Notas cadastradas com sucesso".

c) /resultado que pode retornar uma dessas 3 frases:

    "Cadastre o nome e as notas antes" - Caso o nome ou as notas não tenham sido informados ainda.
    "Estudante aprovado com a média X" - Caso a média entre as notas for >= 6
    "Com a média X infelizmente não há aprovação" - Caso a média entre as notas for < 6

    */

    @GetMapping("/cadastrar-estudante/{nome}")
    public String nome(@PathVariable String nome) {
        gNome = nome;
        return String.format("Estudante %s cadastrado(a) com sucesso", nome);
    }

    private Double media;

    private String gNome = "";

    @GetMapping("/cadastrar-notas/{n1}/{n2}")
    public String media(@PathVariable double n1, @PathVariable double n2) {

        media = (n1 + n2) / 2;
        return String.format("Notas cadastradas com sucesso");
        //return String.format("A soma entre %.2f e %.2f é %.2f", n1, n2, media);
    }

    @GetMapping("/resultado")
    public String resultado() {

        String frase = "não há";

        if(media == null || gNome.equals("")) {
            frase = String.format("Cadastre o nome e as notas antes");
        }
        else if (media >= 6){
            frase = String.format("Estudante aprovado com a média %.2f", media);
        }
        else if (media < 6) {
            frase = String.format("Com a média %.2f infelizmente não há aprovação", media);
        }

        return frase;

    }

    /*

4. Melhore a API da questão anterior de tal maneira que a chamada  /cadastrar-notas/{n1}/{n2} retorne a frase "Ambas as notas devem estar entre 0 e 10", caso uma das notas informadas não estiver entre 0 e 10.
    */

}
