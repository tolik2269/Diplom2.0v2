package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DataHelperDB;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.WebPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsWebCash {
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
    void clearDatabaseTables()throws  Exception {
        DataHelperDB.clearTables();
    }


    @Test
    @DisplayName("Оплата тура с валидной картой с статусом APPROVED")
    void testCashValidCardApproved() throws Exception {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getApprovedCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageSuccessfully();
        assertEquals("APPROVED", DataHelperDB.findPayStatus());
    }



    @Test
    @DisplayName("Оплата тура с валидной картой с статусом DECLINED")
    void testCashValidCardDeclined() throws Exception {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getDeclinedCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageError();
        assertEquals("DECLINED", DataHelperDB.findPayStatus());
    }



    @Test
    @DisplayName("Оплата тура по несуществующей карте")
    void testCashInvalidCard() throws Exception {
        webPage.buyWithCash();
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
    @DisplayName("Ввод данных с пустым полем Номер карты во вкладке Купить")
    public void testEmptyFieldNumberCard() throws  Exception {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getEmptyLine());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
        assertEquals(0, DataHelperDB.getOrderEntityCount());
    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты менее 16 символов во вкладке Купить")
    public void testCardNumberLessThan16() throws Exception {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumberLess());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
        assertEquals(0, DataHelperDB.getOrderEntityCount());
    }



    @Test
    @DisplayName("Оплата тура по не полностью заполненной карте")
    void testCashUnfilledCard() throws Exception {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumberLess());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
        assertEquals(0, DataHelperDB.getOrderEntityCount());

    }

    @Test
    @DisplayName("Ввод данных с пустым данными в поле Месяц во вкладке Купить")
    public void testEmptyMonth() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getEmptyLine());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 00 во вкладке Купить")
    public void testInvalidMonth() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getInvalidMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением больше 12 месяцев во вкладке Купить")
    public void testNonExistentMonth() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getRandomMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 1 цифры  во вкладке Купить")
    public void testInvalidNumber() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getMonthWithOneValue());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец с пустым значением во вкладке Купить")
    public void testEmptyOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getEmptyLine());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageRequiredField();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец на кириллице во вкладке Купить")
    public void testOwnerCyrillic() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomCyrillicName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец в цифровом значении во вкладке Купить")
    public void testNumbersOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerNumber());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец со спецсимволами во вкладке Купить")
    public void testSymbolsOwner() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getSpecialCharactersOwner());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле Год с пустым значением во вкладке Купить")
    public void testBlankValueYear() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getEmptyLine());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Год с 1 значением во вкладке Купить")
    public void testYearWithOneValue() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getYearWithOneValue());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с меньшим значением текущего года во вкладке Купить")
    public void testLastYear() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getPreviousYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageCardExpired();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 6+ лет во вкладке Купить")
    public void testYearPlus5() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYearPlus5());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 00 во вкладке Купить")
    public void testYear00() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getInvalidYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getRandomCvc());
        webPage.clickContinueButton();
        webPage.messageCardExpired();
    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с пустым значением во вкладке Купить")
    public void testEmptyCvcCvv() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getEmptyLine());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 1 значением во вкладке Купить")
    public void test1CVC() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getSingleDigitCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 2 значением во вкладке Купить")
    public void test2CVC() {
        webPage.buyWithCash();
        webPage.setCardNumber(DataHelper.getRandomCardNumber());
        webPage.setCardMonth(DataHelper.getNextMonth());
        webPage.setCardYear(DataHelper.getCurrentYear());
        webPage.setCardOwner(DataHelper.getRandomOwnerName());
        webPage.setCardCVV(DataHelper.getDoubleDigitCvc());
        webPage.clickContinueButton();
        webPage.messageIncorrectFormat();

    }

}
