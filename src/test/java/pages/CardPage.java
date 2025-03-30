package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

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

    public String getOrderSuccessMessage() {
        return message.shouldBe(Condition.visible, Duration.ofSeconds(15)).getText();
    }

    public void checkErrorMessageForCity(String error) {
        inputCityError.shouldBe(Condition.visible, Condition.text(error));
    }

    public void checkErrorMessageForDate(String error) {
        inputDateError.shouldBe(Condition.visible, Condition.text(error));
    }

    public void checkErrorMessageForName(String error) {
        inputNameError.shouldBe(Condition.visible, Condition.text(error));
    }

    public void checkErrorMessageForPhone(String error) {
        inputPhoneError.shouldBe(Condition.visible, Condition.text(error));
    }

    public boolean agreementIsValid() {
        return agreemetCheck.getAttribute("class").contains("input_invalid");
    }
}
