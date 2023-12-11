package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DataHelperDB;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.WebPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsWebCredit {
    WebPage webPage = new WebPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void clearDatabaseTables() throws  Exception {
        DataHelperDB.clearTables();
    }
    @Test
    @DisplayName("Кредит за тур с валидной картой с статусом APPROVED")
    void testCreditValidCardApproved() throws Exception {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getApprovedCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageSuccessfully();
        assertEquals("APPROVED", DataHelperDB.findCreditStatus());
    }
    @Test
    @DisplayName("Кредит за тур с валидной картой с статусом DECLINED")
    void testCreditValidCardDeclined() throws Exception {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getDeclinedCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageError();
        assertEquals("DECLINED", DataHelperDB.findCreditStatus());
    }

    @Test
    @DisplayName("Кредит за тур по несуществующей карте")
    void testCreditInvalidCard() throws Exception {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageError();
        assertEquals(0, DataHelperDB.getOrderEntityCount());

    }
    @Test
    @DisplayName("Ввод данных с пустым полем Номер карты во вкладке Купить в кредит")
    public void testCreditEmptyFieldNumberCard() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getEmptyLine());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }
    @Test
    @DisplayName("Ввод данных в поле Номер карты менее 16 символов во вкладке Купить в кредит")
    public void testCreditCardNumberLessThan16() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumberLess());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты  с несуществующей картой во вкладке Купить в кредит")
    public void testCreditCashInvalidCard() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageError();
    }

    @Test
    @DisplayName("Ввод данных с пустым данными в поле Месяц во вкладке Купить в кредит")
    public void testCreditEmptyMonth() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getEmptyLine());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 00 во вкладке Купить в кредит")
    public void tesCredittInvalidMonth() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getInvalidMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением больше 12 месяцев во вкладке Купить в кредит")
    public void testCreditNonExistentMonth() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getRandomMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 1 цифры  во вкладке Купить в кредит")
    public void testCreditInvalidNumber() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getMonthWithOneValue());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец с пустым значением во вкладке Купить в кредит")
    public void testCreditEmptyOwner() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getEmptyLine());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageRequiredField();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец на кириллице во вкладке Купить в кредит")
    public void testCreditOwnerCyrillic() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomCyrillicName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец в цифровом значении во вкладке Купить в кредит")
    public void testCreditNumbersOwner() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerNumber());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец со спецсимволами во вкладке Купить в кредит")
    public void testCreditSymbolsOwner() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getSpecialCharactersOwner());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле Год с пустым значением во вкладке Купить в кредит")
    public void testCreditBlankValueYear() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getEmptyLine());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с 1 значением во вкладке Купить в кредит")
    public void testCreditYearWithOneValue() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getYearWithOneValue());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с меньшим значением текущего года во вкладке Купить в кредит")
    public void testCreditLastYear() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getPreviousYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageCardExpired();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 5+ лет во вкладке Купить в кредит")
    public void testCreditYearPlus5() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYearPlus5());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 00 во вкладке Купить в кредит")
    public void testCreditYear00() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getInvalidYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageCardExpired();
    }


    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с пустым значением во вкладке Купить в кредит")
    public void testCreditEmptyCvcCvv() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getEmptyLine());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 1 значением во вкладке Купить в кредит")
    public void testCredit1CVC() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getSingleDigitCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 2 значением во вкладке Купить в кредит")
    public void testCredit2CVC() {
        webPage.buyInCredit();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getDoubleDigitCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

}
