package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import java.time.Duration;

public class CardPage {

    private SelenideElement inputCity = $("[data-test-id='city'] input");
    private SelenideElement inputCityError = $(By.xpath("//*[@data-test-id='city' and contains(@class, 'input_invalid')]//span[@class='input__sub']"));

    private SelenideElement inputDate = $("[data-test-id='date'] input");
    private SelenideElement inputDateError = $(By.xpath("//*[@data-test-id='date']//span[contains(@class, 'input_invalid')]"));

    private SelenideElement inputName = $("[data-test-id='name'] input");
    private SelenideElement inputNameError = $(By.xpath("//*[@data-test-id='name' and contains(@class, 'input_invalid')]//span[@class='input__sub']"));

    private SelenideElement inputPhone = $("[data-test-id='phone'] input");
    private SelenideElement inputPhoneError = $(By.xpath("//*[@data-test-id='phone' and contains(@class, 'input_invalid')]//span[@class='input__sub']"));

    private SelenideElement agreemetCheck = $("[data-test-id='agreement'] span.checkbox__box");
    private SelenideElement agreemetText = $("[data-test-id='agreement']");
    private SelenideElement submitButton = $(".button");
    private SelenideElement message = $("[data-test-id='notification'] div.notification__content");


    public CardPage() {
    }


    //Заполнение формы
    public void fillOrderForm(String city, String date, String name, String phone, Boolean accept) {
        // Заполнение полей
        inputCity.setValue(city);

        inputDate.doubleClick();
        inputDate.sendKeys(date);

        inputName.setValue(name);
        inputPhone.setValue(phone);

        if (accept) {
            agreemetCheck.click();
        }
        submitButton.click();
    }

    public void checkOrderSuccessMessage(String message) {
         this.message.should(visible, Duration.ofSeconds(15)).shouldBe(text(message));
    }

    public void checkErrorMessageForCity(String error) {
        inputCityError.shouldBe(visible, text(error));
    }

    public void checkErrorMessageForDate(String error) {
        inputDateError.shouldBe(visible, text(error));
    }

    public void checkErrorMessageForName(String error) {
        inputNameError.shouldBe(visible, text(error));
    }

    public void checkErrorMessageForPhone(String error) {
        inputPhoneError.shouldBe(visible, text(error));
    }

    public void agreementIsInvalid() {
        agreemetText.shouldHave(visible);
    }
}
