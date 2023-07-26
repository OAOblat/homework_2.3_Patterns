package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate currentDate = LocalDate.now(); // Получаем текущую дату
        LocalDate shiftedDate = currentDate.plusDays(shift); // Добавляем сдвиг к текущей дате
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Задаем формат даты
        String date = shiftedDate.format(formatter); // Форматируем сдвинутую дату в строку
        return date;
    }

    public static String generateCity(String locale) {
        String[] cities = {"Москва", "Санкт-Петербург", "Екатеринбург", "Новосибирск", "Майкоп", "Уфа", "Краснодар"};
        Random random = new Random(); // Создаем объект класса Random для выбора случайного города из массива
        int index = random.nextInt(cities.length); // Получаем случайный индекс города из массива
        String city = cities[index]; // Получаем случайный город
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new java.util.Locale(locale)); // Создаем объект Faker с указанной локалью
        String name = faker.name().fullName(); // Генерируем случайное имя
        return name;
    }

    public static String generatePhone(String locale) {
        String[] phones = {"+79218887766", "+79110001122", "+78123667788"};
        Random random = new Random(); // Создаем объект класса Random для выбора случайного номера телфона
        int index = random.nextInt(phones.length); // Получаем случайный номер телефона из массива
        String phone = phones[index]; // Получаем случайный номер телефона
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            String name = generateName(locale); // Генерируем случайное имя
            String city = generateCity(locale); // Генерируем случайный город
            String phone = generatePhone(locale); // Генерируем случайный номер телефона
            UserInfo user = new UserInfo(city, name, phone); // Создаем объект пользователя
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
