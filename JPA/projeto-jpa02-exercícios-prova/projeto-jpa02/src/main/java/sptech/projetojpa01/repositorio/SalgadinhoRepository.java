package sptech.projetojpa01.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.projetojpa01.entidade.Salgadinho;
import sptech.projetojpa01.resposta.SalgadinhoSimplesResposta;

import javax.transaction.Transactional;
import java.util.List;

public interface SalgadinhoRepository
    extends JpaRepository<Salgadinho, Integer> {
  /*
  A @Query aceita instruções na linguagem JPQL (Java Persistence Query Language)
  A diferença é que nela devemos usar os nomes das Entidades e não tabelas
   */
  @Query("select new sptech.projetojpa01.resposta.SalgadinhoSimplesResposta(s.codigo, s.nome) from Salgadinho s")
  List<SalgadinhoSimplesResposta> listaSimples();

  /*
    Quando precisamos realizar uma operação que modifica valores
    sempre devemos utilziar o @Modifying e @Transactional
   */
  @Transactional // -- pacote springframework
  @Modifying // -- pacote springframework
  @Query("update Salgadinho s set s.preco=?2 where s.codigo=?1")
  void atualizarPreco(Integer codigo, Double novoPreco);

  @Transactional // -- pacote springframework
  @Modifying // -- pacote springframework
  @Query("update Salgadinho s set s.preco=?2, s.apimentado=?3 where s.codigo=?1")
  void atualizarPrecoApimentado(Integer codigo, Double preco, Boolean apimentado);

  List<Salgadinho> findByApimentadoTrue();

  int countByApimentadoFalse();

  List<Salgadinho> findByNivelSalLessThan(Integer nivelSal);

  List<Salgadinho> findByPrecoGreaterThanEqual(Double preco);
}
