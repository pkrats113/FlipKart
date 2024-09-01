package com.Flipkart.Service;


import com.Flipkart.Entity.Product;
import com.Flipkart.Entity.PurchaseOrder;
import com.Flipkart.Repository.ProductRepository;
import com.Flipkart.Repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public PurchaseOrder placeOrder(Long productId, int quantity) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new Exception("Not enough stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        PurchaseOrder order = new PurchaseOrder();
        order.setProductId(productId);
        order.setQuantity(quantity);
        return purchaseOrderRepository.save(order);
    }

    @Cacheable(value = "products", key = "#productId")
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

