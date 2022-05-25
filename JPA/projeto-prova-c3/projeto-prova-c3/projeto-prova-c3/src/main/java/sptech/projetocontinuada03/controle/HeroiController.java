package sptech.projetocontinuada03.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetocontinuada03.entidade.Heroi;
import sptech.projetocontinuada03.entidade.NivelAmeaca;
import sptech.projetocontinuada03.repositorio.ClasseHeroiRepository;
import sptech.projetocontinuada03.repositorio.HeroiRepository;
import sptech.projetocontinuada03.repositorio.NivelAmeacaRepository;
import sptech.projetocontinuada03.servico.HeroiService;

import java.util.List;
import java.util.Optional;

    /*
    i) (1pt) Faça todas as correções necessárias para que a API inicie
    */

@RestController
@RequestMapping("/herois")
public class HeroiController {

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private ClasseHeroiRepository classeHeroiRepository;

    @Autowired
    private NivelAmeacaRepository nivelAmeacaRepository;

    @Autowired
    private HeroiService heroiService;

    /*
    b) (0.5pt) Tenha um EndPoint POST /herois {JSON de Herói} que cadastra um herói.
    Retorne o status HTTP correto para cada situação que pode ocorrer.
     */
    @PostMapping
    public ResponseEntity<Void> postHeroi(@RequestBody Heroi novoHeroi) {
        novoHeroi.setSalario(heroiService.getSalario(novoHeroi));
        heroiRepository.save(novoHeroi);
        return ResponseEntity.status(201).build();
    }

    /*
    c) (0.5pt) Tenha um EndPoint PUT /herois/{id} {JSON de Herói} que atualiza um herói.
    Retorne o status HTTP correto para cada situação que pode ocorrer.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> postHeroi(@PathVariable Long id,
                                          @RequestBody Heroi heroi) {
        if (heroiRepository.existsById(id)) {
            heroi.setId(id);
            heroiRepository.save(heroi);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping
    public ResponseEntity<Void> putHeroi(@RequestBody Heroi heroi) {
        heroi.setSalario(heroiService.getSalario(heroi));
        heroiRepository.save(heroi);
        return ResponseEntity.status(200).build();
    }

    /*
    g) (0,5pt) Deve haver um EndPoint GET /classe/{classe} que traz uma lista de todos os heróis
    de determinada classe (use o nome da classe). Use Dynamic finder ou @Query para resolver.
    Retorne o status HTTP correto para cada retorno possível
     */
    @GetMapping("/classe/{classe}")
    public ResponseEntity<List<Heroi>> getHerois(@PathVariable String classe) {

        return heroiRepository.findByClasseNomeEquals(classe).isEmpty() ?
                ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(heroiRepository.findByClasseNomeEquals(classe));
    }

    /*
    h) (1,5pt) Deve haver um EndPoint POST /vitoria/{idHeroi}/{idNivelAmeaca} que registra uma vitória
    de um herói sobre um tipo de ameaça. Retorne o status HTTP 200 se tudo deu certo
    ou 404 caso um dos "ids" seja inválido.
    Se tudo deu certo, aumente a pontuação do herói conforme os pontos do nível de ameaça
    (olhe o código já existente no projeto, todos esses campos já existem lá).
    Atualize a classe do herói conforme sua nova pontuação.
    É possível verificar qual classe de herói é a correta para uma pontuação usando o método
    findTop1ByPontuacaoMinimaLessThanEqualOrderByPontuacaoMinimaDesc() da ClasseHeroiRepository.
     */
/*    @PatchMapping("/vitoria/{idHeroi}/{idNivelAmeaca}")
    public ResponseEntity patchVitoria(@PathVariable long idHeroi,
                                       @PathVariable long idNivelAmeaca) {

        if (heroiRepository.existsById(idHeroi)
                && nivelAmeacaRepository.existsById(idNivelAmeaca)) {

            Heroi heroi = heroiRepository.findByHeroiIdEquals(idHeroi);
            NivelAmeaca ameaca = nivelAmeacaRepository.findByNivelAmeacaIdEquals(idNivelAmeaca);

            Integer pontos = ameaca.getPontuacao();

            heroi.setPontos(+pontos);

            heroi.setClasse(
                    classeHeroiRepository.
                            findTop1ByPontuacaoMinimaLessThanEqualOrderByPontuacaoMinimaDesc
                                    (heroi.getPontos()
                                    )
            );

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }*/

    /*
    a) (1,5pt) Tenha um EndPoint GET /herois que pode retornar status 200 ou 204, dependendo
    se achar ou não heróis na API. Caso retorne, o JSON, o corpo deve ser como este:
    [
      {
        "id": 1,
        "nome": "Saitama",
        "apelido": "capa careca",
        "pontos": 5500,
        "salario": 85000,
        "classe": {
          "id": 3,
          "nome": "B",
          "pontuacaoMinima": 5000,
          "salarioBase": 80000
        }
      }
    ]
     */
    @GetMapping
    public ResponseEntity<List<Heroi>> getAllHerois() {

        return heroiRepository.findAll().isEmpty() ? ResponseEntity.status(204).build() :
                ResponseEntity.status(200).body(heroiRepository.findAll());
    }

}
