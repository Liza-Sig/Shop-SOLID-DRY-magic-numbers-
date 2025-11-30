package ru.netology;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    // SRP: хранит только товары и их количество
    private final Map<Product, Integer> itemInCart = new HashMap<>();

    public void addProduct(Product product) {
        // Магические числа вынесены в Constants.DEFAULT_ADD_QUANTITY
        itemInCart.put(product, itemInCart.getOrDefault(product, 0) + Constants.DEFAULT_ADD_QUANTITY);
    }

    public void decreaseProduct(Product product) {
        if (!itemInCart.containsKey(product)) return;

        int newQuantity = itemInCart.get(product);
        if (newQuantity > 1) {
            itemInCart.put(product, newQuantity - 1);
        } else {
            itemInCart.remove(product);
        }
    }

    public void deleteProduct(Product product) {
        itemInCart.remove(product);
    }

    public double totalAmount() {
        // DRY: подсчёт суммы в одну строку
       double total = itemInCart.entrySet().stream()
               .mapToDouble(value -> value.getKey().getPrice() * value.getValue())
               .sum();
        // Магические числа
       if (total >= Constants.DISCOUNT_THRESHOLD){
           total -= total * Constants.DISCOUNT_RATE;
       }
       return total;
    }

    // LSP: возвращаем неизменяемую карту — безопасно подставлять
    public Map<Product, Integer> getItemInCart() {
        return Collections.unmodifiableMap(itemInCart);
    }

    public void clear() {
        itemInCart.clear();
    }
}
