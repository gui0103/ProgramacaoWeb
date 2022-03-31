package sptech.project04.controle;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import sptech.project04.entidade.Heroi;

import java.util.ArrayList;
import java.util.List;

    // https://

    @RestController
    @RequestMapping("/herois")
    public class HeroiController{

        private List<Heroi> herois =  new ArrayList<>();

        @GetMapping
        public List<Heroi> getHerois() {
            return herois;
        }

        @PostMapping
        public String postHeroi(@RequestBody Heroi novoHeroi) {

            herois.add(novoHeroi);
            return "Heroi cadastro com sucesso!";

        }


        @GetMapping("/{indice}")
        public String getHeroi(@PathVariable int indice){


            String mensagem;

            if (indice >= herois.size()) {

                mensagem = null;

            } else {

                Heroi heroiParaBuscar = herois.get(indice);
                mensagem = String.format("%s", heroiParaBuscar.getNome());

            }

            return mensagem;

        }

        @GetMapping("objeto/{indice}")
        public Heroi getHeroiObjeto(@PathVariable int indice){

            if (indice >= herois.size()) {

                return null;

            }
            Heroi heroiSendoBuscado = herois.get(indice);

            return heroiSendoBuscado;

        }

        @DeleteMapping("/{indice}")
        public String deleteHeroi(@PathVariable int indice){

            String mensagem;

            if (indice >= herois.size()) {

                mensagem = String.format("Heroi nao encontrado!");

            } else {

                Heroi heroiParaDeletar = herois.get(indice);
                herois.remove(heroiParaDeletar);
                mensagem = String.format("Heroi excluido com sucesso!");

            }

            return mensagem;

        }

        @PutMapping("/{indice}")
        public String putHeroi(@PathVariable int indice, @RequestBody Heroi heroi){


            String mensagem;

            if (indice >= herois.size()) {

                mensagem = String.format("Heroi nao encontrado!");

            } else {

                herois.set(indice, heroi);
                mensagem = String.format("Heroi encontrado com sucesso!");

            }

            return mensagem;


        }

    }

