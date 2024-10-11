package unicap.si.microservicos.api.buscaCep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.si.microservicos.api.buscaCep.exception.CepNotFoundException;
import unicap.si.microservicos.api.buscaCep.model.PointData;
import unicap.si.microservicos.api.buscaCep.repository.PointDataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PointDataService {

    @Autowired
    private PointDataRepository pointDataRepository;

    @Autowired
    private ViaCepService viaCepService;

    public PointData createPointData(PointData pointData)
    {
        return pointDataRepository.save(pointData);
    }

    public PointData deletePointDataByCep(String cep) throws CepNotFoundException
    {
        Optional<PointData> optionalPointData = pointDataRepository.findByCep(cep);

        if(optionalPointData.isPresent())
        {
            pointDataRepository.delete(optionalPointData.get());
            return optionalPointData.get();
        }else
        {
            throw new CepNotFoundException("CEP not found");
        }
    }

    public List<PointData> getAllPointData()
    {
        return pointDataRepository.findAll();
    }

    public PointData getPointData(String cep)
    {
        Optional<PointData> optionalPointData = pointDataRepository.findByCep(cep);

        return optionalPointData.orElse(null);
    }

    public PointData updatePointDataLogradouro(String cep, String logradouro) throws CepNotFoundException
    {
        StringBuilder cepString = new StringBuilder(cep);
        cepString.insert(5,"-");
        Optional<PointData> optionalPointData = pointDataRepository.findByCep(cepString.toString());

        if(optionalPointData.isPresent())
        {
            PointData pointData = optionalPointData.get();
            pointData.setLogadouro(logradouro);
            return pointDataRepository.save(pointData);
        }
        throw new CepNotFoundException("CEP not found");
    }

    public PointData fetchAndSavePointData(String cep) throws CepNotFoundException {
        PointData pointDataSearched = viaCepService.getCepInfo(cep);

        if (pointDataSearched == null || pointDataSearched.getCep() == null) {
            throw new CepNotFoundException("CEP not found");
        }

        return pointDataRepository.save(pointDataSearched);
        }


}
