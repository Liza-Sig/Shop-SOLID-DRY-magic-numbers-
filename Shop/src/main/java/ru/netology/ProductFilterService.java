package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// SRP: Класс отвечает ТОЛЬКО за фильтрацию товаров
public class ProductFilterService {
    public List<Product> filterByName(List<Product> products, String keyword){
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Product> filterByManufacturer(List<Product> products, String manufacturer){
        return products.stream()
                .filter(product -> product.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }
    public List<Product> filterByPrice(List<Product> products, double min, double max){
        return products.stream()
                .filter(product -> product.getPrice() >= min && product.getPrice() <= max)
                .collect(Collectors.toList());
    }
    public List<Product> findAll(List<Product> products){
        return new ArrayList<>(products);
    }
}
