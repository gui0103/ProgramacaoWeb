package sptech.projetojpa06.clientecep.servico;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sptech.projetojpa06.clientecep.resposta.Cep;

@FeignClient(value = "viacep", url = "https://viacep.com.br/ws/")
public interface ViaCepService {

    // ex de mapeamento para https://viacep.com.br/ws/04301000/json/
    @GetMapping("{cep}/json/")
    Cep getCep(@PathVariable String cep);

    // outros endpoints mapeados, como POST, PUT, DELETE etc
}
