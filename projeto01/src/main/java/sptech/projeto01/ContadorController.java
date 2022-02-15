package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
1. Numa classe Rest Controller que quiser,  crie as seguintes chamadas:

/registrar-vitoria
/registrar-derrota
/registrar-empate
sendo que:

vitoria = registra 3 pontos;
empate = registra 1 ponto;
derrota = registra 0 pontos;
Cada chamada citada acima também registra 1 partida.

Crie outro endpoint:

/pontuacao
que retorna a seguinte frase:

"Ola você tem X pontos e Z partidas"

onde X representa o total de pontos obtidos e Z representa a quantidade de partidas.
 */

@RestController
public class ContadorController {

    private int cont = 0;

    @GetMapping("/contar")
    public String contar() {

        return  String.format("Acessos: %d", cont++);
    }

    @GetMapping("/zerar")
    public String zerar() {

        cont = 0;

        return  String.format("Contador zerado");
    }

    private int vitorias =0;
    private int empates =0;
    private int derrotas =0;

    @GetMapping("/cadastrar-vitoria")
    public String cadastrarVitoria() {

        vitorias++;

        return  String.format("Vitória registrada com sucesso!");
    }

    @GetMapping("/cadastrar-empate")
    public String cadastrarEmpate() {

        empates++;

        return  String.format("Empate registrado com sucesso!");
    }

    @GetMapping("/cadastrar-derrota")
    public String cadastrarDerrota() {

        derrotas++;

        return  String.format("Derrota registrada com sucesso!");
    }

    /*
2. Melhore a API que começou a fazer em #1 de tal forma que:

a) A frase da chamada /pontuacao contenha também a porcentagem de aproveitamento do time OU uma frase dizendo que nenhum dado foi informado, caso nenhuma das outras chamadas tenha sido invocada antes.
ex: "Ola você tem 30 pontos e 10 partidas - Aproveitamento: 100%" Esse aproveitamento é a quantidade de pontos obtidos pela quantidade máxima de pontos possíveis (ou seja, se tivesse apenas vencido)

ex: Caso nenhuma das outras chamadas tenha sido invocada, ou seja, caso o time não tenha disputado nenhuma partida: "Cadastre pelo menos uma vitória, empate ou derrota"

     */

    @GetMapping("/pontuacao")
    public String cadastrarPontuacao() {

        Integer vit = vitorias * 3;

        Integer pontos = vit + empates;

        Integer partidas = vitorias + empates + derrotas;

        Double aproveitamento = (pontos / (partidas * 3.0)) * 100;

        return  String.format("Você tem %d pontos em %d partidas -> Aproveitamento: %.2f%%", pontos, partidas, aproveitamento);
    }

}
