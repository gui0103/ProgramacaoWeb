package sptech.projetojpa08.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.projetojpa08.entidade.Funcionario;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNomeContains(String nome);
}
