package sptech.projetojpa06.clientelogs.resposta;

public class RegistroLog {

    private Long identificador;
    private String descricao;

    public RegistroLog(Long identificador, String descricao) {
        this.descricao = descricao;
        this.identificador = identificador;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getIdentificador() {
        return identificador;
    }
}
