package sptech.projetojpa05.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.projetojpa05.entidade.TipoAnimal;

public interface TipoAnimalRepository extends JpaRepository<TipoAnimal, Integer> {
}
