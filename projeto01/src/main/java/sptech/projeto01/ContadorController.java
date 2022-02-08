package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pontuacao")
    public String cadastrarPontuacao() {

        Integer vit = vitorias * 3;

        Integer pontos = vit + empates;

        Integer partidas = vitorias + empates + derrotas;

        return  String.format("Você tem %d pontos em %d partidas", pontos, partidas);
    }
}
