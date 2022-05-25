package sptech.projetocontinuada03.servico;

import org.springframework.stereotype.Service;
import sptech.projetocontinuada03.entidade.Heroi;

@Service
public class HeroiService {

    /*
    d) (0,5pt) Tanto no POST quanto no PUT dos itens anteriores o salário deve ser calculado conforme
    a classe do herói, usando o método "getSalario()" da classe HeroiService. Ou seja, o salario,
    caso venha no JSON do corpo da requisição, deve ser ignorado,
    já que será usado o valor calculado pela HeroiService.

    e) (2pt) O método "getSalario()" da classe HeroiService deve funcionar da seguinte forma:
    O salário retornado deve ser o salário base da classe do herói mais um bônus de 100,00
    por ponto que ele tem. Caso o parâmetro "heroi" seja nulo,
    o método deve lancar uma IllegalArgumentException com a mensagem "Herói é obrigatório"
     */
    public double getSalario(Heroi heroi) {
        if (heroi == null) {
            throw new IllegalArgumentException("Herói é obrigatório");
        }
        return (heroi.getClasse().getSalarioBase()) + (heroi.getPontos() * 100.00);
    }
}
