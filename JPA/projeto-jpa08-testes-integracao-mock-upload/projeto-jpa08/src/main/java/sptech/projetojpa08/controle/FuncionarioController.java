package sptech.projetojpa08.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.projetojpa08.entidade.Funcionario;
import sptech.projetojpa08.repositorio.FuncionarioRepository;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Funcionario>> get() {
        List<Funcionario> lista = repository.findAll();
        return lista.isEmpty() ?
                ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/filtro-nome/{nome}")
    public ResponseEntity<List<Funcionario>> getPorNome(
            @PathVariable String nome
    ) {
        List<Funcionario> lista
                = repository.findByNomeContains(nome);
        return lista.isEmpty() ?
                ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(lista);
    }

/*
Endpoint para fazer o envio somente do conteúdo da foto
No Postman usa-se o tipo "binary" e indica-se o arquivo
*/
    @PatchMapping(value = "/foto/{id}", consumes = "image/jpeg")
    public ResponseEntity patchFoto(@PathVariable long id,
                                    @RequestBody byte[] novaFoto) {
        Funcionario funcionario = repository.findById(id).get();

        funcionario.setFoto(novaFoto);

        repository.save(funcionario);

        return ResponseEntity.status(200).build();
    }

    /*
Endpoint para obter somente o conteúdo da foto
Sua URL completa pode até user usada numa tag <img>, visto que seu conteúdo é uma imagem
     */
    @GetMapping(value = "/foto/{id}", produces = "image/jpeg")
    public ResponseEntity getFoto(@PathVariable long id) {
        Funcionario funcionario = repository.findById(id).get();

        return ResponseEntity.status(200)
                .body(funcionario.getFoto());
    }
}
