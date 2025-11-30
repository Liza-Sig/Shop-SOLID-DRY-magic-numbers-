package ru.netology;

import java.util.List;

// ISP: в интерфейсе только нужные методы
// DIP: остальные классы работают с абстракцией
public interface ProductRepository {
    void add(Product product);
    List<Product> findAll();
}
