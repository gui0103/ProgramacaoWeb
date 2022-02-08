package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrasesController {

    // localhost: 8080/cumprimentar
    @GetMapping("/cumprimentar")
    public String cumprimentar() {
        return "é noizzzz no REST";
    }

    // localhost: 8080/boaNoite
    @GetMapping("boa-noite")
    public String boaNoite() {
        return "Boa Noite ;)";
    }

    @GetMapping("sorteio")
    public String sorteio() {

        Integer numeroSorteado = (int) (Math.random() * 101);

        return String.format("Número sorteado: %d", numeroSorteado);
    }
}
