package ru.netology;

import java.util.ArrayList;
import java.util.List;

// SRP: хранилище только хранит товары
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        // OCP: можно заменить на базу данных без изменений в Main
        return new ArrayList<>(products);
    }
}
