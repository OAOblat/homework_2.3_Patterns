package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static io.qameta.allure.Allure.step;

import io.qameta.allure.selenide.AllureSelenide;


class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and rePlan meeting")
    void shouldSuccessfulPlanAndRePlanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        SelenideElement form = $(".form_theme_alfa-on-white");

        step("Ввод города", () -> {
            form.$("[data-test-id=city] input").sendKeys(validUser.getCity());
        });

        step("Ввод даты", () -> {
            form.$("[data-test-id=date] input").sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
            form.$("[data-test-id=date] input").sendKeys(firstMeetingDate);
        });

        step("Ввод имени", () -> {
            form.$("[data-test-id=name] input").sendKeys(validUser.getName());
        });
        step("Ввод телефона", () -> {
            form.$("[data-test-id=phone] input").sendKeys(validUser.getPhone());
        });
        step("Согласие с условиями", () -> {
            form.$("[data-test-id=agreement]").click();
        });
        step("Нажатие на кнопку", () -> {
            form.$(".button_theme_alfa-on-white").click();
        });
        step("Проверка успешной записи первой встречи", () -> {
            $(".notification_status_ok .notification__content")
                    .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))
                    .shouldBe(Condition.visible);
        });
        step("Обновление страницы", () -> {
            refresh();
        });
        step("Повторный ввод города", () -> {
            form.$("[data-test-id=city] input").sendKeys(validUser.getCity());
        });
        step("Ввод новой даты", () -> {
            form.$("[data-test-id=date] input").sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
            form.$("[data-test-id=date] input").sendKeys(secondMeetingDate);
        });
        step("Повторный ввод имени", () -> {
            form.$("[data-test-id=name] input").sendKeys(validUser.getName());
        });
        step("Повторный ввод телефона", () -> {
            form.$("[data-test-id=phone] input").sendKeys(validUser.getPhone());
        });
        step("Согласие с условиями", () -> {
            form.$("[data-test-id=agreement]").click();
        });
        step("Нажатие на кнопку", () -> {
            form.$(".button_theme_alfa-on-white").click();
        });
        step("Вывод предупреждения об уже запланированной встрече", () -> {
            $(".notification_status_error .notification__content")
                    .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15))
                    .shouldBe(Condition.visible);
        });
        step("Подтверждение перепланирования на новую дату", () -> {
            $(".notification_status_error .button_theme_alfa-on-white").click();
        });
        step("Проверка успешного изменения даты встречи", () -> {
            $(".notification_status_ok .notification__content")
                    .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15))
                    .shouldBe(Condition.visible);
        });
    }

}
