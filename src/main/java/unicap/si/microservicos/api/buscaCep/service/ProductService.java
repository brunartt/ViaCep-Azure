package unicap.si.microservicos.api.buscaCep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.si.microservicos.api.buscaCep.exception.CepNotFoundException;
import unicap.si.microservicos.api.buscaCep.exception.ProductNotFound;
import unicap.si.microservicos.api.buscaCep.model.PointData;
import unicap.si.microservicos.api.buscaCep.model.Product;
import unicap.si.microservicos.api.buscaCep.repository.PointDataRepository;
import unicap.si.microservicos.api.buscaCep.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PointDataRepository pointDataRepository;

    public Product createProduct(Product product, Long pointDataId) throws CepNotFoundException
    {
        Optional<PointData> pointDataOP = pointDataRepository.findById(pointDataId);
        if (pointDataOP.isPresent()) {
            product.setDeliveryAddress(pointDataOP.get());
            return productRepository.save(product);
        } else {
            throw new CepNotFoundException("CEP NOT FOUND");
        }
    }

    public Product removeProductById(Long id) throws ProductNotFound
    {
        Optional<Product> productOP = productRepository.findById(id);
        if(productOP.isPresent())
        {
            productRepository.delete(productOP.get());
            return productOP.get();
        }
        else{
            throw new ProductNotFound("Product not found");
        }
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws ProductNotFound
    {
        Optional<Product> productOP = productRepository.findById(id);
        if(productOP.isPresent())
        {
            return productOP.get();
        }
        else{
            throw new ProductNotFound("Product not found");
        }
    }

    public Product updateProductPrice(Long id, Double price) throws ProductNotFound
    {
        Optional<Product> productOP = productRepository.findById(id);
        if(productOP.isPresent())
        {
            Product product = productOP.get();
            product.setPreco(price);
            return productRepository.save(product);
        }else{
            throw new ProductNotFound("Product not found");
        }
    }
}
