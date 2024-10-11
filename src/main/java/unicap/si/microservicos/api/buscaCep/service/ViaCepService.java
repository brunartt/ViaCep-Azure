package unicap.si.microservicos.api.buscaCep.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unicap.si.microservicos.api.buscaCep.model.PointData;

@Service
public class ViaCepService {

    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private  Gson gson = new Gson();

    public PointData getCepInfo(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);

        String jsonResponse = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        PointData pointData = new PointData();
        pointData.setCep(jsonObject.get("cep").getAsString());
        pointData.setLogadouro(jsonObject.get("logradouro").getAsString());
        pointData.setBairro(jsonObject.get("bairro").getAsString());
        pointData.setLocalidade(jsonObject.get("localidade").getAsString());
        pointData.setUf(jsonObject.get("uf").getAsString());

        return pointData;
    }
}
