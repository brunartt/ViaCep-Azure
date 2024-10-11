package unicap.si.microservicos.api.buscaCep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicap.si.microservicos.api.buscaCep.exception.CepNotFoundException;
import unicap.si.microservicos.api.buscaCep.exception.ProductNotFound;
import unicap.si.microservicos.api.buscaCep.model.Product;
import unicap.si.microservicos.api.buscaCep.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @RequestParam Long pointDataId) {
        try {
            return ResponseEntity.ok(productService.createProduct(product, pointDataId));
        } catch (CepNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
       try{
           return ResponseEntity.ok(productService.removeProductById(id));
       }
       catch (ProductNotFound e){
            return ResponseEntity.status(400).body(null);
       }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) throws ProductNotFound {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        }
        catch (ProductNotFound e){
            throw new ProductNotFound(e.getMessage());
        }
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Double price) throws ProductNotFound {
        try {
            return ResponseEntity.ok(productService.updateProductPrice(id,price));
        }catch (ProductNotFound e){
            throw new ProductNotFound(e.getMessage());
        }
    }
}
