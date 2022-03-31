package sptech.c101211055gustavoquaresmadacosta.controller;


import org.springframework.web.bind.annotation.*;
import sptech.c101211055gustavoquaresmadacosta.entidade.Usuario;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping
    public String cadastraUsuario(@RequestBody Usuario u){
        u.setSenha(u.recuperaSenha());
        usuarios.add(u);
        return String.format("Usuário %s cadastrado no sistema.", u.getNome());
    }

    @PostMapping("/autenticacao/{login}/{senha}")
    public String autenticaUsuario(@PathVariable String login, @PathVariable String senha){
        for(Usuario u : usuarios){
            if(u.getLogin().equals(login)){
                if(u.recuperaSenha().equals(senha)){
                    u.setAutenticado(true);
                    return String.format("Usuário %s agora está autenticado.", u.getNome());
                }
            }
        }
        return String.format("Usuário %s não encontrado.", login);
    }

    @GetMapping
    public List<Usuario> getUsuarios(){
        return usuarios;
    }

    @DeleteMapping("/autenticacao/{login}")
    public String deletarUsuario(@PathVariable String login){
        for (Usuario u : usuarios){
            if(u.getLogin().equals(login)){
                if(u.getAutenticado()){
                    u.setAutenticado(false);
                    return String.format("Usuário %s está autenticado.", u.getNome());
                }
                return String.format("Usuário %s NÃO está autenticado.", u.getNome());
            }
        }
        return String.format("Usuário %s não encontrado.", login);
    }

    //EndPoint para retornar apenas usuários autenticados.
    @GetMapping("/autenticados")
    public List<Usuario> getAutenticados(){
        List<Usuario> autenticados = new ArrayList<>();
        for(Usuario u : usuarios){
            if(u.getAutenticado()){
                autenticados.add(u);
            }
        }
        return autenticados;
    }
};