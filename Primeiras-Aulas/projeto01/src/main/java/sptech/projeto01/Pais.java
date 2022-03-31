package sptech.projeto01;

public class Pais {

    private String nome;
    private Integer medalhasOuro;
    private Integer medalhasPrata;
    private Integer medalhasBronze;

    public Pais(String nome, Integer medalhasOuro, Integer medalhasPrata, Integer medalhasBronze) {
        this.nome = nome;
        this.medalhasOuro = medalhasOuro;
        this.medalhasPrata = medalhasPrata;
        this.medalhasBronze = medalhasBronze;
    }

    public Pais() {
    }

    //Cada Get se torna uma linha no Json

    public Integer getMedalhasOuro() {
        return medalhasOuro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMedalhasOuro(Integer medalhasOuro) {
        this.medalhasOuro = medalhasOuro;
    }

    public Integer getMedalhasPrata() {
        return medalhasPrata;
    }

    public void setMedalhasPrata(Integer medalhasPrata) {
        this.medalhasPrata = medalhasPrata;
    }

    public Integer getMedalhasBronze() {
        return medalhasBronze;
    }

    public void setMedalhasBronze(Integer medalhasBronze) {
        this.medalhasBronze = medalhasBronze;
    }

    public Integer getTotalMedalhas(){
        return(this.medalhasBronze + this.medalhasPrata + this.medalhasOuro);
    }


}
