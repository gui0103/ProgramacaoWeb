package sptech.projetojpa08.servico;

public class CalculosService {

    public Double calcularInss(Double salarioBruto) {
        if (salarioBruto < 500) {
            throw new IllegalArgumentException("Salário dever ser a partir de 500");
        }
        return salarioBruto <= 2000 ? 0.05 * salarioBruto : 0.10 * salarioBruto;
    }

    public boolean receberaAuxilio(double salario, int dependentes) {
        if (salario < 500 || dependentes < 0) {
            throw new IllegalArgumentException("O salário deve ser a partir de 500 e os dependentes a partir de 0");
        }
        return salario < 2000 || (dependentes > 3 && salario < 4000);
    }
    /*
O auxílio emergencial deve ser concedido somente se
o salário for menor que 2000 ou
se tiver mais de 3 dependentes e o salário for menor que 4000.
Caso o salário for menor que 500 ou o número de filhos for menor que 0,
lance uma IllegalArgumentException com a mensagem
"O salário deve ser a partir de 500 e os dependentes a partir de 0"
     */
}
