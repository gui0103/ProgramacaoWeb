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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetojpa01.entidade.Salgadinho;
import sptech.projetojpa01.repositorio.SalgadinhoRepository;
import sptech.projetojpa01.requisicao.SalgadinhoPrecoApimentadoRequisicao;
import sptech.projetojpa01.resposta.SalgadinhoSimplesResposta;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/salgadinhos")
public class SalgadinhoController {

    public static void gravaArquivoCsv(List<Salgadinho> lista, String nomeArq) {
        FileWriter arq = null; // arq é o objeto que corresponde ao arquivo
        Formatter saida = null; // obj que será usado para escrever no arquivo
        nomeArq += ".csv";
        Boolean deuRuim = false;

        // Bloco try catch para abrir no arquivo
        try {
            arq = new FileWriter(nomeArq); // colocar o ',true' pós nomeArq para acresentar + 1
            saida = new Formatter(arq);
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try catch para gravar no arquivo
        try {

            // Percorro a lista de salgadinhos
            for (int i = 0; i < lista.size(); i++) {

                Salgadinho salgadinho = lista.get(i);

                saida.format("%d;%s;%d;%.2f;%b\n",
                        salgadinho.getCodigo(), salgadinho.getNome(), salgadinho.getNivelSal(),
                        salgadinho.getPreco(), salgadinho.getApimentado()); // uma linha do arquivo
            }
        }
        catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo!");
            deuRuim = true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo!");
                deuRuim = true;
            }
            if (deuRuim) {

                System.exit(1);
            }
        }
    }

    public static void leExibeArquivoCsv (String nomeArq){
        FileReader arq = null;
        Scanner entrada = null;
        nomeArq += ".csv";
        Boolean deuRuim = false;

        //Bloco try catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        }
        catch (FileNotFoundException erro) {
            System.out.println("Arquivo não encontrado");
            System.exit(1);
        }

        // Bloco try catch para ler o arquivo
        try {
            System.out.printf("%6s %25s %9s %7s %10s\n", "CODIGO", "NOME", "NÍVEL-SAL", "PREÇO", "APIMENTADO");
            while (entrada.hasNext()) {
                Integer codigo = entrada.nextInt();
                String nome = entrada.next();
                Integer nivelSal = entrada.nextInt();
                Double preco = entrada.nextDouble();
                Boolean apimentado = entrada.nextBoolean();
                System.out.printf("%6d %25s %9d %7.2f %10b\n", codigo, nome, nivelSal, preco, apimentado);
            }
        }
        catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            deuRuim = true;
        }
        catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;
        }
        finally {
            entrada.close();
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo!");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

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

    // b) Crie o EndPoint GET /apimentados -> retorna uma lista de todos que são apimentados
    @GetMapping("/apimentados")
    public ResponseEntity<List<Salgadinho>> getApimentados() {

        return ResponseEntity.status(200).body(repository.findByApimentadoTrue());
    }

    // c) Crie o EndPoint GET /contagem-nao-apimentados -> retorna uma contagem (apenas um número)
    // de quantos não são apimentados
    @GetMapping("/contagem-nao-apimentados")
    public int getQntdNaoApimentados() {

        return repository.countByApimentadoFalse();
    }

    // d) Crie o EndPoint GET /pouco-sal -> retorna uma lista de todos cujo nível de sal é menor que 4
    @GetMapping("/pouco-sal")
    public ResponseEntity<List<Salgadinho>> getPoucoSal() {

        return ResponseEntity.status(200).body(repository.findByNivelSalLessThan(4));
    }

    // e) Crie o EndPoint GET /caros -> retorna uma lista de todos cujo preço é maior ou igual a 20
    @GetMapping("/caros")
    public ResponseEntity<List<Salgadinho>> getCaros() {

        return ResponseEntity.status(200).body(repository.findByPrecoGreaterThanEqual(20.0));
    }

    // f) Crie o EndPoint GET /relatorio-geral -> retorna um arquivo no formato CSV que contém o
    // código, o nome, o nível de sal e o preço de cada salgadinho.
    // O nome do arquivo deve ser "salgadinhos.csv"

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "text/csv"))
    })
    @GetMapping("/relatorio-geral")
    public ResponseEntity getRelatorio() {
        List<Salgadinho> lista = repository.findAll();
        String relatorio = "";
        for (Salgadinho salgadinho : lista) {
            relatorio += salgadinho.getCodigo()+";"+salgadinho.getNome()+";"+
                    salgadinho.getNivelSal()+";"+salgadinho.getPreco()+"\n";
        }
        return ResponseEntity.status(200)
                .header("content-type", "text/csv")
                .header("content-disposition", "filename=\"salgadinhos.csv\"")
                .body(relatorio);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "text/csv"))
    })
    @GetMapping("/relatorio-geral-alternativo")
    public void getRelatorioAlternativo() {

        List<Salgadinho> lista = repository.findAll();

        gravaArquivoCsv(lista, "salgadinhos");

        leExibeArquivoCsv("salgadinhos");
    }

    // g) A API deve ter uma documentação swagger acessível em http://localhost:8080/swagger-ui/index.html

    // h) Crie o EndPoint GET /filtro-preco/{preco1}/{preco2} ->
    // retorna uma lista de todos cujo preco está entre {preco1} e {preco2}
    @GetMapping("/filtro-preco/{preco1}/{preco2}")
    public ResponseEntity<List<Salgadinho>> getFiltroPreco
    (@PathVariable Double preco1, @PathVariable Double preco2) {

        return ResponseEntity.status(200).body(repository.findByPrecoBetween(preco1, preco2));
    }

    // i) Crie o EndPoint GET /filtro-nome/{nome} ->
    // retorna uma lista de todos cujo nome contém as letras enviadas em {nome}
    @GetMapping("/filtro-nome/{nome}")
    public ResponseEntity<List<Salgadinho>> getFiltroNome(@PathVariable String nome) {

        return ResponseEntity.status(200).body(repository.findByNomeContains(nome));
    }
}
