package sptech.exerciciopokemons1.entidade;

public class Pokemon {

    private String nome;
    private String tipo;
    private Double forca;
    private Boolean capturado;

    public Pokemon(String nome, String tipo, Double forca, Boolean capturado) {
        this.nome = nome;
        this.tipo = tipo;
        this.forca = forca;
        this.capturado = capturado;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getForca() {
        return forca;
    }

    public Boolean getCapturado() {
        return capturado;
    }
}
