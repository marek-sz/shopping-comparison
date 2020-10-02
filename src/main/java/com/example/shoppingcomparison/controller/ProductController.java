package com.example.shoppingcomparison.controller;

import com.example.shoppingcomparison.model.Product;
import com.example.shoppingcomparison.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/index")
    public String populateLandingPageWithRandomProducts(Model model) {
        List<Product> products = productRepository.findAllById(generateRandomIds());
        model.addAttribute("products", products);
        return "index";
    }

    private List<Long> generateRandomIds() {
        long[] longs = ThreadLocalRandom.current().longs(100, 1, productRepository.count()).toArray();
        return Arrays.stream(longs)
                .boxed()
                .collect(Collectors.toList());
    }

    @GetMapping("/allProducts")
    public String getAllProducts(
            @RequestParam("category") String category, Model model) {
        List<Product> products = productRepository.findAll();
        List<Product> filteredProducts =
                products.stream()
                        .filter(p -> p.getCategory().toString().equals(category))
                        .collect(Collectors.toList());
        model.addAttribute("products", filteredProducts);
        return "index";
    }

    @GetMapping("/products")
    public String findByName(
            @RequestParam("productName") String productName, Model model) {
        List<Product> products = productRepository.findAll();
        List<Product> filteredProducts =
                products.stream()
                        .filter(p -> p.getBrand().toLowerCase().contains(productName.toLowerCase().trim())
                                || p.getModel().toLowerCase().contains(productName.toLowerCase().trim()))
                        .collect(Collectors.toList());
        model.addAttribute("products", filteredProducts);
        return "index";
    }

    @GetMapping("/allProducts/byPrice")
    public String sortByPrice(Model model) {
        Sort sort = Sort.by("price");
        List<Product> products = productRepository.findAll(sort);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/allProducts/byName")
    public String sortByName(Model model) {
        Sort sort = Sort.by("brand").and(Sort.by("model"));
        List<Product> products = productRepository.findAll(sort);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/allProducts/byShop")
    public String sortByShop(Model model) {
        Sort sort = Sort.by("shop");
        List<Product> products = productRepository.findAll(sort);
        model.addAttribute("products", products);
        return "index";
    }
}
