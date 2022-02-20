import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
Crie uma controler chamada PokemonController.
 */
@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    List<Pokemon> listaPokemons = new ArrayList<>();

    /*
    /pokemons/cadastrar/{nome}/{tipo}/{forca}/{capturado}
    Ao ser invocada, retorna a mensagem "Pokemon X cadastrado com sucesso" onde X é nome informado;
    */
    @GetMapping("/cadastrar/{nome}/{tipo}/{forca}/{capturado}")
    public String cadastrar(@PathVariable String nome, @PathVariable String tipo, @PathVariable Double forca, @PathVariable Boolean capturado) {

        Pokemon pokemonNovo = new Pokemon(nome, tipo , forca, capturado);
        listaPokemons.add(pokemonNovo);

        return String.format("Pokemon %s cadastrado com sucesso", nome);
    }

    /*
    /pokemons/remover/{indice}
    Ao ser invocada, retorna a seguinte mensagem, " Pokemon X removido com sucesso", onde X é nome do pokemon removido;
    Caso nao exista, retorna "Indice inexistente";
    */
    @GetMapping("/remover/{indice}")
    public String remover(@PathVariable Integer indice) {

        String frase;

        if( listaPokemons.get(indice) != null){

            Pokemon pokemonParaRemover = listaPokemons.get(indice);

            frase = String.format("Pokemon %s removido com sucesso", pokemonParaRemover.getNome());

            listaPokemons.remove(pokemonParaRemover);

        }

        else {

            frase = String.format("Indice inexistente");

        }

        return frase;

    }

    /*
    /pokemons/buscar/{indice}
    Ao ser invocada, retorna o pokemon contido no índice informado, caso não exista, retorna null;
     */
    @GetMapping("/buscar/{indice}")
    public Pokemon buscar(@PathVariable Integer indice) {

        if( listaPokemons.get(indice) == null){

            return null;
        }

        return listaPokemons.get(indice);
    }

    /*
    /pokemons/atualizar/{indice}/{nome}/{tipo}/{forca}/{capturado}
    Ao ser invocada, atualiza o pokemon e exibe o seguinte,  "Pokemon X atualizado com sucesso",  onde X é nome informado;
    Caso nao exista, exiba "Indice inexistente";
     */
    @GetMapping("/atualizar/{indice}/{nome}/{tipo}/{forca}/{capturado}")
    public String atualizar(@PathVariable Integer indice, @PathVariable String nome, @PathVariable String tipo, @PathVariable Double forca, @PathVariable Boolean capturado) {

        String frase;

        if( listaPokemons.get(indice) != null){

            Pokemon pokemonUpdate = listaPokemons.get(indice);

            frase = String.format("Pokemon %s atualizado com sucesso", pokemonUpdate.getNome());

            pokemonUpdate.setNome(nome);
            pokemonUpdate.setTipo(tipo);
            pokemonUpdate.setForca(forca);
            pokemonUpdate.setCapturado(capturado);

        }

        else {

            frase = String.format("Indice inexistente");

        }

        return frase;

    }

    /*
    /pokemons/listar
    Ao ser invocada, retorna a lista completa de pokemon;
     */
    @GetMapping("/listar")
    public void listar(){

        for(Pokemon pokemon : listaPokemons){
            System.out.println(pokemon);
        }
    }

    /*

    Desafios

    /pokemons/{tipo}/contagem
    Retorna a quantidade existente do mesmo tipo informado;

    /pokemons/capturados
    Retorna uma lista de pokemons que possuem o atributo capturado com o valor true;

    /pokemons/fortes
    Retorna uma lista de pokemons que possuem o atributo forca com o valor acima de 3.000;

    /pokemons/fracos
    Retorna uma lista de pokemons que possuem o atributo forca com o valor abaixo ou igual de 3.000;

     */
    @GetMapping("/{tipo}/contagem")
    public Integer contagem(@PathVariable String tipo){

        Integer cont = 0;

        for(Pokemon pokemon : listaPokemons){

            if(pokemon.getTipo().equals(tipo)){
                cont++;
            }
        }

        return cont;
    }

    @GetMapping("/capturados")
    public void capturados(){

        System.out.println("Lista de Pokemons capturados: \n");

        for(Pokemon pokemon : listaPokemons){

            if(pokemon.getCapturado() == true){

                System.out.println(pokemon);
            }
        }
    }

    @GetMapping("/fortes")
    public void fortes(){

        System.out.println("Lista de Pokemons fortes: \n");

        for(Pokemon pokemon : listaPokemons){

            if(pokemon.getForca() > 3000){

                System.out.println(pokemon);
            }
        }
    }

    @GetMapping("/fracos")
    public void fracos(){

        System.out.println("Lista de Pokemons fracos: \n");

        for(Pokemon pokemon : listaPokemons){

            if(pokemon.getForca() <= 3000){

                System.out.println(pokemon);
            }
        }
    }

}



