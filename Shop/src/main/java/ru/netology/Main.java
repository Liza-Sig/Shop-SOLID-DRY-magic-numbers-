package ru.netology;

import java.util.List;
import java.util.Scanner;

import static ru.netology.Constants.CURRENCY;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // DIP: Используем интерфейс ProductRepository, а не конкретную реализацию
        ProductRepository repository = new InMemoryProductRepository();
        // SRP: сервис занимается только фильтрацией
        ProductFilterService filterService = new ProductFilterService();
        // SRP: корзина отвечает только за товары пользователя
        Basket basket = new Basket();

        // Магические числа вынесены в константы или данные
        repository.add(new Product(1, "Хлеб", "Любава", 30));
        repository.add(new Product(2, "Молоко 5%", "Коровка", 120));
        repository.add(new Product(3, "Плавленый сыр", "Дружба", 220));
        repository.add(new Product(4, "Сыр", "Хохланд", 130));
        repository.add(new Product(5, "Кефир 1%", "Молочник", 90));
        repository.add(new Product(6, "Детский кефир", "Молочник", 50));
        repository.add(new Product(7, "Шоколад ", "Россия", 96));
        repository.add(new Product(8, "Молочный шоколад ", "Россия щедрая душа", 110));

        while (true) {
            System.out.println("\n=== МАГАЗИН ===");
            System.out.println("1 - Показать все товары");
            System.out.println("2 - Фильтр по названию");
            System.out.println("3 - Фильтр по производителю");
            System.out.println("4 - Фильтр по цене");
            System.out.println("5 - Добавить товар в корзину");
            System.out.println("6 - Показать корзину");
            System.out.println("7 - Очистить корзину");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Ошибка: пустой ввод");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Ошибка: введите число");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("=== Список товаров ===");
                    for (Product p : repository.findAll()) {
                        System.out.println(p.getId() + ". " + p.getName() +
                                " — " + p.getManufacturer() + ": " + p.getPrice() + " руб.");
                    }
                }
                case 2 -> {
                    System.out.print("Введите название: ");
                    String keyword = scanner.nextLine();
                    filterService.filterByName(repository.findAll(), keyword)
                            .forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("Введите производителя: ");
                    String keyword = scanner.nextLine();
                    filterService.filterByManufacturer(repository.findAll(), keyword)
                            .forEach(System.out::println);
                }
                case 4 -> {
                    try {
                        System.out.print("Минимальная цена: ");
                        double min = Double.parseDouble(scanner.nextLine());
                        System.out.print("Максимальная цена: ");
                        double max = Double.parseDouble(scanner.nextLine());
                        filterService.filterByPrice(repository.findAll(), min, max)
                                .forEach(System.out::println);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: нужно ввести число");
                    }
                }
                case 5 -> {
                    System.out.print("Ведите ID товара: ");
                    String idStr = scanner.nextLine().trim();
                    int id = Integer.parseInt(idStr);;
                    // DRY: переиспользуем findAll()
                    Product p = repository.findAll().stream()
                            .filter(product -> product.getId() == id)
                            .findFirst()
                            .orElse(null);
                    if (p != null) {
                        basket.addProduct(p);
                        System.out.println("Товар добавлен");
                    } else {
                        System.out.println("Товар не найден");
                    }
                }
                case 6 -> {
                    System.out.println("Ваша корзина: ");
                    basket.getItemInCart().forEach((product, newQuantity) ->
                            System.out.println(product + "-" + newQuantity + "шт."));
                    System.out.println("Сумма: " + basket.totalAmount() + CURRENCY); // Магические числа заменены константами
                }
                case 7 -> {
                    basket.clear();
                    System.out.println("Корзина очищена");
                }
                case 0 -> {
                    System.out.println("Выход");
                    return;
                }
                default ->
                    System.out.println("Ошибка ввода");
            }
        }
    }
}