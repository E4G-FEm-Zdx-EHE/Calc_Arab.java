
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        String result;
        try {
            result = calc(input);
        } catch (Exception e) {
            result = "throws exception";
        }         // "выдает исключения"
                  // "генерирует исключение"

        System.out.println(result);
    }

    public static String calc(String input) {
        // Проверка на корректность введенных данных
        if (!input.matches("[IVX]{1,4}(\\s?)(\\+|-|\\*|\\/)(\\s?)[IVX]{1,4}")) {
            throw new IllegalArgumentException("Invalid input");
        }                                   // "Неверный ввод"

        // Разделение введенной строки на операнды и оператор
        String[] parts = input.split("\\s");
        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        // Проверка на одновременное использование арабских и римских чисел
        boolean arabic1 = isArabic(operand1);
        boolean arabic2 = isArabic(operand2);
        if (arabic1 != arabic2) {
            throw new IllegalArgumentException("Arabic and Roman numerals cannot be mixed");
        }                                   // "Арабские и римские цифры нельзя смешивать"

        int num1 = arabic1 ? Integer.parseInt(operand1) : romanToArabic(operand1);
        int num2 = arabic2 ? Integer.parseInt(operand2) : romanToArabic(operand2);

        // Проверка на допустимый диапазон чисел
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers out of range");
        }                                   // "Номера вне диапазона"

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }                                       // "Недопустимый оператор"

        return arabic1 ? String.valueOf(result) : arabicToRoman(result);
    }

    public static boolean isArabic(String input) {
        return input.matches("\\d+");
    }

    public static int romanToArabic(String input) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);

        int result = 0;
        int prevValue = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            int value = romanNumerals.get(input.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }

        return result;
    }

    public static String arabicToRoman(int input) {
        if (input < 1) {
            throw new IllegalArgumentException("Roman numerals cannot represent zero or negative numbers");
        }                                   // "Римские цифры не могут представлять нулевые или отрицательные числа"

        StringBuilder roman = new StringBuilder();
        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        int i = 0;
        while (input > 0) {
            if (input >= values[i]) {
                roman.append(symbols[i]);
                input -= values[i];
            } else {
                i++;
            }
        }

        return roman.toString();
    }
}