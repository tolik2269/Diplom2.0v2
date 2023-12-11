package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    public static String getApprovedCardNumber() {
        return "4444444444444441";
    }

    public static String getDeclinedCardNumber() {
        return "4444444444444442";
    }

    public static String getRandomCardNumber() {
        return generateRandomDigits(16);
    }

    public static String getRandomCardNumberLess() {
        int randomNumberLength = random.nextInt(16);
        return generateRandomDigits(randomNumberLength);
    }



    public static String getYearWithOneValue() {
        return generateRandomDigits(1);
    }
    public static String getMonthWithOneValue() {
        return generateRandomDigits(1);
    }


    public static String getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        return String.format("%02d", currentYear % 100);
    }

    public static String getCurrentYearPlus5() {
        int currentYear = Integer.parseInt(getCurrentYear());
        int yearPlus5 = currentYear + 5;
        return String.format("%02d", yearPlus5 % 100);
    }

    public static String getPreviousYear() {
        int currentYear = Integer.parseInt(getCurrentYear());
        int previousYear = currentYear - 1;
        return String.format("%02d", previousYear % 100);
    }

    public static String getRandomMonth() {
        int randomValue = random.nextInt(88) + 12;
        return String.valueOf(randomValue);
    }

    public static String getInvalidMonth() {
        return "00";
    }

    public static String getEmptyLine() {
        return "";
    }

    public static String getInvalidYear() {
        return "00";
    }



    public static String getNextMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextMonth = currentDate.plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String formattedMonth = nextMonth.format(formatter);

        return formattedMonth;
    }


    public static String getRandomOwnerName() {
        String[] names = {"Vlad", "Igor", "Anaton", "Elena", "Ira", "Kristina", "Viktor", "Katy"};
        int randomIndex = random.nextInt(names.length);
        return names[randomIndex];
    }

    public static String getRandomCyrillicName() {
        String[] names = {"Влад", "Игорь", "Антон", "Елена", "Ира", "Кристина", "Виктор", "Катя"};
        int randomIndex = random.nextInt(names.length);
        return names[randomIndex];
    }


    public static String getRandomOwnerNumber() {
        return String.valueOf(random.nextInt(10));
    }

    public static String getSpecialCharactersOwner() {
        return "!@#$%^&*()_+-";
    }


    public static String getRandomCvc() {
        return generateRandomDigits(3);
    }

    public static String getSingleDigitCvc() {
        return generateRandomDigits(1);
    }

    public static String getDoubleDigitCvc() {
        return generateRandomDigits(2);
    }

    private static String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}

