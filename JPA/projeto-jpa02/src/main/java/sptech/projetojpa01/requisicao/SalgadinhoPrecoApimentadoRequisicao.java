package sptech.projetojpa01.requisicao;

/*
Esta classe é uma classe do tipo DTO
Data Transfer Object
É um DTO usado na resposta de chamadas
 */
public class SalgadinhoPrecoApimentadoRequisicao {

  private Double preco;
  private Boolean apimentado;

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Boolean getApimentado() {
    return apimentado;
  }

  public void setApimentado(Boolean apimentado) {
    this.apimentado = apimentado;
  }
}
