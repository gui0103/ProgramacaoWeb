package sptech.projetojpa01.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetojpa01.entidade.Salgadinho;
import sptech.projetojpa01.repositorio.SalgadinhoRepository;
import sptech.projetojpa01.requisicao.SalgadinhoPrecoApimentadoRequisicao;
import sptech.projetojpa01.resposta.SalgadinhoSimplesResposta;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/salgadinhos")
public class SalgadinhoController {

  @Autowired
  private SalgadinhoRepository repository;

  /*
    @Valid do pacote javax.validation
   */
  @PostMapping
  public ResponseEntity postSalgadinho(
      @RequestBody @Valid Salgadinho novoSalgadinho) {
    repository.save(novoSalgadinho);
    return ResponseEntity.status(201).build();
  }

  @GetMapping
  public ResponseEntity getSalgadinhos() {

    List<Salgadinho> lista = repository.findAll();

    return ResponseEntity.status(200).body(lista);
  }

  @GetMapping("/simples")
  public ResponseEntity getSimples() {
    List<SalgadinhoSimplesResposta> lista =
        repository.listaSimples();

    return ResponseEntity.status(200).body(lista);
  }

  @GetMapping("/contagem")
  public ResponseEntity getContagem() {
// .count() faz um "select count(*)" p/ saber quantos registros tem na tabela
    long contagem = repository.count();
    return ResponseEntity.status(200).body(contagem);
  }

  @PatchMapping("/{codigo}/preco/{novoPreco}")
  public ResponseEntity patchSalgadinho(
      @PathVariable Integer codigo,
      @PathVariable Double novoPreco
  ) {

    if (repository.existsById(codigo)) {
      repository.atualizarPreco(codigo, novoPreco);
      return ResponseEntity.status(200).build();
    }

    return ResponseEntity.status(404).build();
  }

  @PatchMapping("/{codigo}/preco-apimentado")
  public ResponseEntity patchSalgadinhoPrecoApimentado(
      @PathVariable Integer codigo,
      @RequestBody SalgadinhoPrecoApimentadoRequisicao requisicao
  ) {

    if (repository.existsById(codigo)) {
      repository.atualizarPrecoApimentado(
          codigo, requisicao.getPreco(), requisicao.getApimentado());
      return ResponseEntity.status(200).build();
    }

    return ResponseEntity.status(404).build();
  }
}
