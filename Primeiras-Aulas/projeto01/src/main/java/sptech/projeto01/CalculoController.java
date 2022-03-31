package sptech.projeto01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculoController {

    @GetMapping("/somar/{n1}/{n2}")
    public String somar(@PathVariable double n1, @PathVariable double n2) {
        double soma = n1 + n2;
        return String.format("A soma entre %.2f e %.2f é %.2f", n1, n2, soma);
    }
}
