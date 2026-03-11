package com.vetlife.api.modules.store;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/store") @RequiredArgsConstructor
public class StoreController {
    private final ProductRepository repository;
    private final StoreService service;
    @GetMapping public List<Product> list() { return repository.findAll(); }
    @PostMapping public Product addProduct(@RequestBody Product product) { return repository.save(product); }
    @PostMapping("/{id}/sell/{quantity}") 
    public String sell(@PathVariable Long id, @PathVariable Integer quantity) {
        service.sell(id, quantity);
        return "Venda realizada e estoque atualizado!";
    }
}