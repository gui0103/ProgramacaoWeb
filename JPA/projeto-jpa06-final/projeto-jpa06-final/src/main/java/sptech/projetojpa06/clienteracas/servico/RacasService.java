package sptech.projetojpa06.clienteracas.servico;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sptech.projetojpa06.clienteracas.resposta.Raca;

@FeignClient(value = "racas", url = "https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/racas/")
public interface RacasService {

    // ex de mapeamento para https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/racas/{id}
    @GetMapping("{id}")
    Raca getRaca(@PathVariable Integer id);

    // outros endpoints mapeados, como POST, PUT, DELETE etc
}
