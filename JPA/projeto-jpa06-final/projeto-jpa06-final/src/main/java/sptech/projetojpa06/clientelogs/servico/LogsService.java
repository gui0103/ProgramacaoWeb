package sptech.projetojpa06.clientelogs.servico;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sptech.projetojpa06.clientelogs.resposta.RegistroLog;

@FeignClient(value = "logs",
        url = "https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/logs/")
public interface LogsService {

    // ex de mapeamento para https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/logs
    @PostMapping
    void postLog(@RequestBody RegistroLog registroLog);

    // outros endpoints mapeados, como POST, PUT, DELETE etc
}
