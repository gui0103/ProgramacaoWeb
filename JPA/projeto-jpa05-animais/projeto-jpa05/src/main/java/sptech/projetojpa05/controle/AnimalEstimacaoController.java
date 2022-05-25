package sptech.projetojpa05.controle;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetojpa05.entidade.AnimalEstimacao;
import sptech.projetojpa05.repositorio.AnimalEstimacaoRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class AnimalEstimacaoController {

    @Autowired
    private AnimalEstimacaoRepository repository;

    @PostMapping
    public ResponseEntity postPet(
            @RequestBody @Valid AnimalEstimacao novoPet) {
        repository.save(novoPet);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacao>> getPets() {
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @ApiResponses({
        @ApiResponse(responseCode = "200",
                     content = @Content(mediaType = "text/csv"))
    })
    @GetMapping("/relatorio")
    public ResponseEntity getRelatorio() {
        List<AnimalEstimacao> lista = repository.findAll();
        String relatorio = "";
        for (AnimalEstimacao pet : lista) {
            relatorio += pet.getCodigo()+","+pet.getNome()+"\n";
        }
        return ResponseEntity.status(200)
                    .header("content-type", "text/csv")
                    .header("content-disposition", "filename=\"relatorio-de-pets.csv\"")
                    .body(relatorio);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AnimalEstimacao> getPet(@PathVariable long codigo) {
        return ResponseEntity.of(repository.findById(codigo));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletePet(@PathVariable long codigo) {
        repository.deleteById(codigo);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/filtro/{nome}")
    public ResponseEntity<List<AnimalEstimacao>> filtro(
            @PathVariable String nome
    ) {
        return ResponseEntity.status(200).body(repository.findByNome(nome));
    }

    @GetMapping("/filtro/{nome}/{castrado}")
    public ResponseEntity<List<AnimalEstimacao>> filtro(
            @PathVariable String nome,
            @PathVariable boolean castrado
    ) {
        return ResponseEntity.status(200)
                .body(repository.findByNomeAndCastrado(nome, castrado));
    }

    /*
    Todos os animais de um certo tipo (consultando pelo codigo do tipo)
     */
    @GetMapping("/tipo/{codigo}")
    public ResponseEntity<List<AnimalEstimacao>> getPorTipo(
            @PathVariable Long codigo
    ) {
        return ResponseEntity.status(200)
                .body(repository.findByTipoCodigo(codigo));
    }

    /*
    Todos os animais de um certo tipo (consultando pela descricao do tipo)
    Deve-se pesquisar mesmo se informado apenas PARTE da descrição
    ex: /pets/tipo-descricao/cho  -> traz todos cujo tipo é "Cachorro"
     */
    @GetMapping("/tipo-descricao/{descricao}")
    public ResponseEntity<List<AnimalEstimacao>> getPorTipo(
            @PathVariable String descricao
    ) {
        return ResponseEntity.status(200)
                .body(repository.findByTipoDescricaoContainsIgnoreCase(descricao));
    }
}
