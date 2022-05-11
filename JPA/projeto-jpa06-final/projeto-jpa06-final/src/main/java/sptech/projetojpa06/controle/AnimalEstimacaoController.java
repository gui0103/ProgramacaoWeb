package sptech.projetojpa06.controle;

import feign.FeignException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetojpa06.clientecep.resposta.Cep;
import sptech.projetojpa06.clientecep.servico.ViaCepService;
import sptech.projetojpa06.clientelogs.resposta.RegistroLog;
import sptech.projetojpa06.clientelogs.servico.LogsService;
import sptech.projetojpa06.clienteracas.resposta.Raca;
import sptech.projetojpa06.clienteracas.servico.RacasService;
import sptech.projetojpa06.entidade.AnimalEstimacao;
import sptech.projetojpa06.repositorio.AnimalEstimacaoRepository;
import sptech.projetojpa06.resposta.ConsultaAnimalResposta;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/pets")
public class AnimalEstimacaoController {

    @Autowired
    private AnimalEstimacaoRepository repository;

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private RacasService racasService;

    @Autowired
    private LogsService logsService;

    @PatchMapping("/endereco/{id}/{cep}/{complemento}")
    public ResponseEntity patchEndereco(@PathVariable Long id,
                                        @PathVariable String cep,
                                        @PathVariable String complemento) {
        try {
            Cep cepEncontrado = viaCepService.getCep(cep);

            AnimalEstimacao pet = repository.findById(id).get();
            pet.setEndereco(cepEncontrado.getLogradouro() + " " + complemento);
            pet.setBairro(cepEncontrado.getBairro());
            pet.setCidade(cepEncontrado.getLocalidade());
            pet.setUf(cepEncontrado.getUf());
            pet.setCep(cep);
            repository.save(pet);

            RegistroLog registroLog = new RegistroLog(id, "Endereço atualizado");
            logsService.postLog(registroLog);

            return ResponseEntity.status(200).build();
        } catch (FeignException fe) {
            switch (fe.status()) {
                case 404:
                    return ResponseEntity.status(404).body("Cep não encontrado");
                case 400:
                    return ResponseEntity.status(400).body("Cep inválido");
                case 500:
                    return ResponseEntity.status(500).body("Serviço de Cep indisponível");
                case 0: // o Feign devolve 0 quando nem consegue se comunicar com a API
                    return ResponseEntity.status(500).body("Serviço de Cep inacessível");
                default:
                    return ResponseEntity.status(500).body("Erro no Serviço de Cep "+fe.getMessage());
            }
        }
    }

    @PatchMapping("/raca/{idPet}/{idRaca}")
    public ResponseEntity patchRaca(@PathVariable Long idPet,
                                    @PathVariable Integer idRaca) {
        try {
            if (!repository.existsById(idPet)) {
                return ResponseEntity.status(404).build();
            }

            Raca racaEncontrada = racasService.getRaca(idRaca);

            repository.atualizarRaca(idPet, racaEncontrada.getName());

            RegistroLog registroLog = new RegistroLog(idPet, "Raça atualizada");
            logsService.postLog(registroLog);

            return ResponseEntity.status(200).build();
        } catch (FeignException fe) {
            if (fe.status() == 0) {
                return ResponseEntity.status(500).body("Erro na API de raças");
            }
            return ResponseEntity.status(400).body("Raça não encontrada");
        }
    }

    @PostMapping
    public ResponseEntity postPet(
            @RequestBody @Valid AnimalEstimacao novoPet) {
        repository.save(novoPet);
        return status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacao>> getPets() {
        return status(200).body(repository.findAll());
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
        return status(200)
                    .header("content-type", "text/csv")
                    .header("content-disposition", "filename=\"relatorio-de-pets.csv\"")
                    .body(relatorio);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AnimalEstimacao> getPet(@PathVariable long codigo) {
        return of(repository.findById(codigo));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletePet(@PathVariable long codigo) {
        repository.deleteById(codigo);
        return status(200).build();
    }

    @GetMapping("/filtro/{nome}")
    public ResponseEntity<List<AnimalEstimacao>> filtro(
            @PathVariable String nome
    ) {
        return status(200).body(repository.findByNome(nome));
    }

    @GetMapping("/filtro/{nome}/{castrado}")
    public ResponseEntity<List<AnimalEstimacao>> filtro(
            @PathVariable String nome,
            @PathVariable boolean castrado
    ) {
        return status(200)
                .body(repository.findByNomeAndCastrado(nome, castrado));
    }

    /*
    GET /pets/tipo/{codigoTipo}
    Ex: /pets/tipo/1 -> traria todos os pets que são "gato"
     */
    @GetMapping("/tipo/{codigoTipo}")
    public ResponseEntity getPorTipo(@PathVariable int codigoTipo) {
        List<AnimalEstimacao> lista = repository.findByTipoCodigo(codigoTipo);
        if (lista.isEmpty()) {
            return status(204).build();
        } else {
            return status(200).body(lista);
        }
    }

    /*
    GET /pets/tipo-nome/{nomeTipo}
    Ex: /pets/tipo-nome/cho -> traria todos os pets contém "cho" na descricao do tipo
     */
    @GetMapping("/tipo-nome/{nomeTipo}")
    public ResponseEntity getPorDescricao(@PathVariable String nomeTipo) {
        List<AnimalEstimacao> lista = repository.findByTipoDescricaoContains(nomeTipo);
        if (lista.isEmpty()) {
            return status(204).build();
        } else {
            return status(200).body(lista);
        }
    }

    @GetMapping("/filtro-simples/{nome}")
    public ResponseEntity getSimplesPorNome(@PathVariable String nome) {
        List<ConsultaAnimalResposta> lista =
                repository.consultaSimplePorNome("%"+nome+"%");
        if (lista.isEmpty()) {
            return status(204).build();
        } else {
            return status(200).body(lista);
        }
    }
}
