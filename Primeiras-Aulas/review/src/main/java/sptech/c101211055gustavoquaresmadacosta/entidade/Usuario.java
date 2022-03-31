package sptech.c101211055gustavoquaresmadacosta.entidade;

public class Usuario {
private String login;
private String nome;
private Boolean autenticado = false;
private String senha;

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAutenticado() {
        return autenticado;
    }

    public String recuperaSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAutenticado(Boolean autenticado) {
        this.autenticado = autenticado;
    }
}
