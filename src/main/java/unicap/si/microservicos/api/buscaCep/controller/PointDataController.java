package unicap.si.microservicos.api.buscaCep.controller;

import org.apache.coyote.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import unicap.si.microservicos.api.buscaCep.exception.CepNotFoundException;
import unicap.si.microservicos.api.buscaCep.model.PointData;
import unicap.si.microservicos.api.buscaCep.repository.PointDataRepository;
import unicap.si.microservicos.api.buscaCep.service.PointDataService;
import unicap.si.microservicos.api.buscaCep.service.ViaCepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("point")
public class PointDataController {

    @Autowired
    private  ViaCepService viaCepService;

    @Autowired
    private  PointDataService pointDataService;

    private static final Logger logger = LoggerFactory.getLogger(PointDataController.class);

    @GetMapping("{cep}")
    public ResponseEntity<PointData> fetchAndSave(@PathVariable String cep) {
        logger.info("Recebendo requisição para o CEP: {}", cep);

        try {
            PointData pointDataToSave = pointDataService.fetchAndSavePointData(cep);
            logger.info("Dados do CEP salvos com sucesso: {}", pointDataToSave);
            return ResponseEntity.ok(pointDataToSave);

        } catch (CepNotFoundException e) {
            logger.warn("CEP não encontrado: {}", cep);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    public List<PointData> getAllPoint()
    {
        return pointDataService.getAllPointData();
    }

    @DeleteMapping("/delete/{cep}")
    public ResponseEntity<PointData> deleteByCep(@PathVariable String cep){
        logger.info("Recebendo requisição para deletar o CEP: {}", cep);
        try {
            PointData pointDeleted = pointDataService.deletePointDataByCep(cep);
            return ResponseEntity.ok(pointDeleted);
        } catch (CepNotFoundException e) {
            logger.warn("CEP não encontrado: {}", cep);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<PointData> addPointData(@RequestBody PointData pointData)
    {
        return ResponseEntity.ok(pointDataService.createPointData(pointData));
    }

    @PutMapping("alterLogradouro/{cep}")
    public ResponseEntity<PointData> alterLogradouroPointData(@PathVariable String cep, @RequestBody String logradouro) throws CepNotFoundException
    {
        try {
            return ResponseEntity.ok(pointDataService.updatePointDataLogradouro(cep, logradouro));
        } catch (CepNotFoundException e) {
            throw new CepNotFoundException(e.getMessage());
        }
    }
}
