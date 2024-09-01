package com.Flipkart.Controller;


import com.Flipkart.Entity.Product;
import com.Flipkart.Entity.PurchaseOrder;
import com.Flipkart.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return inventoryService.saveProduct(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/order")
    public PurchaseOrder placeOrder(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            return inventoryService.placeOrder(productId, quantity);
        } catch (Exception e) {
            throw new RuntimeException("Order could not be placed: " + e.getMessage());
        }
    }
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) throws Exception {
        return inventoryService.getProductById(id);
    }
}

