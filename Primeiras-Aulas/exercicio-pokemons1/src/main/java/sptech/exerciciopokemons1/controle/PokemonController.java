package sptech.exerciciopokemons1.controle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.exerciciopokemons1.entidade.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private List<Pokemon> pokemons = new ArrayList<>(List.of(
            new Pokemon("Pikachu", "elétrico", 90_000.0, false),
            new Pokemon("Bubasaur", "aquático", 22_000.0, true),
            new Pokemon("ReisFish", "aquático", 1_000.0, true)
    ));

    @GetMapping("/remover/{indice}")
    public String remover(@PathVariable int indice) {
        pokemons.remove(indice);
        return "Pokemon X removido com sucesso";
    }

    @GetMapping("/atualizar/{indice}/{nome}/{tipo}/{forca}/{capturado}")
    public String atualizar(@PathVariable int indice,
                            @PathVariable String nome,
                            @PathVariable String tipo,
                            @PathVariable double forca,
                            @PathVariable boolean capturado) {
        pokemons.set(indice, new Pokemon(nome, tipo, forca, capturado));
        return "Pokemon X atualizado com sucesso";
    }


    @GetMapping("/{tipo}/contagem")
    public String contarPorTipo(@PathVariable String tipo) {
        // solução "clássica"
        /*
        int contador = 0;

        for (Pokemon pokemon : pokemons) {
            if (pokemon.getTipo().equals(tipo)) {
                contador++;
            }
        }
        */

        // solução moderna
        long contador = pokemons.stream()
                        .filter(pokemon -> pokemon.getTipo().equals(tipo))
                        .count();

        return String.format(
                "Foram encontrados %d Pokemons do tipo %s", contador, tipo);
    }

    @GetMapping("/capturados")
    public List<Pokemon> capturados() {
        // solução "clássica"

        /*
        List capturados = new ArrayList();

        for (Pokemon pokemon : pokemons) {
            if (pokemon.getCapturado()) {
                capturados.add(pokemon);
            }
        }

        return capturados;
         */


        // solução moderna
        return pokemons.stream()
                        .filter(pokemon -> pokemon.getCapturado())
                        .collect(Collectors.toList());

        // solução moderna v2
        /*
        return pokemons.stream()
                        .filter(Pokemon::getCapturado)
                        .collect(Collectors.toList());
        */
    }
}
