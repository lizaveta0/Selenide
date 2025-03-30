import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.CardPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    private CardPage cardPage;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        cardPage = new CardPage();
    }

    @Test
    public void testOrderFlowPositive() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String successMessage = cardPage.getOrderSuccessMessage();

        assertEquals("Встреча успешно забронирована на " + date, successMessage);
    }

    @Test
    public void testOrderFlowNegative() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String successMessage = cardPage.getOrderSuccessMessage();

        assertEquals("Встреча успешно забронирована на " + date, successMessage);
    }

    @Test
    public void testOrderFlowNegativeCity() {
        String city = "";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForCity();

        assertEquals("Поле обязательно для заполнения", error);
    }

    @Test
    public void testOrderFlowNegativeCityIncorrect() {
        String city = "Лондон";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForCity();

        assertEquals("Доставка в выбранный город недоступна", error);
    }

    @Test
    public void testOrderFlowNegativeDate() {
        String city = "Москва";
        String date = "1";
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForDate();

        assertEquals("Неверно введена дата", error);
    }

    @Test
    public void testOrderFlowNegativeDateEmpty() {
        String city = "Москва";
        String date = " ";
        String name = "Тестовый Пользователь";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForDate();

        assertEquals("Неверно введена дата", error);
    }

    @Test
    public void testOrderFlowNegativeNameEmpty() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForName();

        assertEquals("Поле обязательно для заполнения", error);
    }

    @Test
    public void testOrderFlowNegativeNameIncorrect() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Test";
        String phone = "+79998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForName();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error);
    }

    @Test
    public void testOrderFlowNegativePhoneEmpty() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый";
        String phone = "";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForPhone();

        assertEquals("Поле обязательно для заполнения", error);
    }

    @Test
    public void testOrderFlowNegativePhoneIncorrect() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый";
        String phone = "89998887766";
        Boolean accept = true;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        String error = cardPage.getErrorMessageForPhone();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error);
    }

    @Test
    public void testOrderFlowNegativeAgreement() {
        String city = "Москва";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name = "Тестовый";
        String phone = "+79998887766";
        Boolean accept = false;

        cardPage.fillOrderForm(city, date, name, phone, accept);
        Boolean agreemented = cardPage.agreementIsValid();

        assertEquals(accept, agreemented);
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}
