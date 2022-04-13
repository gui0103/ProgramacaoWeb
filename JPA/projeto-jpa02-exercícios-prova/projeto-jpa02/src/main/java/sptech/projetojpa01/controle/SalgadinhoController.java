/*
1. Baixe o Projeto-jpa02 (API de salgadinhos, do dia 28/03) e melhore ele:

a) Crie um data.sql para que a API já inicie com pelo menos 5 registros.
O "nivelSal" deve estar entre 0 e 10 em todos.

b) Crie o EndPoint GET /apimentados -> retorna uma lista de todos que são apimentados

c) Crie o EndPoint GET /contagem-nao-apimentados -> retorna uma contagem (apenas um número)
de quantos não são apimentados

d) Crie o EndPoint GET /pouco-sal -> retorna uma lista de todos cujo nível de sal é menor que 4

e) Crie o EndPoint GET /caros -> retorna uma lista de todos cujo preco de sal é maior ou igual a 20

f) Crie o EndPoint GET /relatorio-geral -> retorna um arquivo no formato CSV que contém o
código, o nome, o nível de sal e o preço de cada salgaginho. O nome do arquivo deve ser "salgadinhos.csv"

g) A API deve ter uma documentação swagger acessível em http://localhost:8080/swagger-ui/index.html
 */

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

  /*
b) Crie o EndPoint GET /apimentados -> retorna uma lista de todos que são apimentados
*/
  @GetMapping("/apimentados")
  public ResponseEntity<List<Salgadinho>> getApimentados() {

    return ResponseEntity.status(200).body(repository.findByApimentadoTrue());
  }

/*
c) Crie o EndPoint GET /contagem-nao-apimentados -> retorna uma contagem (apenas um número)
de quantos não são apimentados
*/

/*
d) Crie o EndPoint GET /pouco-sal -> retorna uma lista de todos cujo nível de sal é menor que 4
*/

/*
e) Crie o EndPoint GET /caros -> retorna uma lista de todos cujo preco de sal é maior ou igual a 20
*/

/*
f) Crie o EndPoint GET /relatorio-geral -> retorna um arquivo no formato CSV que contém o
código, o nome, o nível de sal e o preço de cada salgaginho. O nome do arquivo deve ser "salgadinhos.csv"
*/

/*
g) A API deve ter uma documentação swagger acessível em http://localhost:8080/swagger-ui/index.html
*/
}
