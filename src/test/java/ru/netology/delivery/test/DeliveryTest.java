package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;


class DeliveryTest {

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
        form.$("[data-test-id=city] input").sendKeys(validUser.getCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").sendKeys(firstMeetingDate);
        form.$("[data-test-id=name] input").sendKeys(validUser.getName());
        form.$("[data-test-id=phone] input").sendKeys(validUser.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button_theme_alfa-on-white").click();
        $(".notification_status_ok .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        refresh();
        form.$("[data-test-id=city] input").sendKeys(validUser.getCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").sendKeys(secondMeetingDate);
        form.$("[data-test-id=name] input").sendKeys(validUser.getName());
        form.$("[data-test-id=phone] input").sendKeys(validUser.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button_theme_alfa-on-white").click();
        $(".notification_status_error .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $(".notification_status_error .button_theme_alfa-on-white").click();
        $(".notification_status_ok .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
