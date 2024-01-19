import java.util.*;

class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        try {
            String[] parts = expression.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат выражения");
            }

            String firstOperand = parts[0];
            String operator = parts[1];
            String secondOperand = parts[2];

            boolean isRoman = isRomanNumeral(firstOperand) && isRomanNumeral(secondOperand);
            int operand1 = parseOperand(firstOperand, isRoman);
            int operand2 = parseOperand(secondOperand, isRoman);

            int result;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }

            if (isRoman) {
                System.out.println("Результат: " + toRomanNumeral(result));
            } else {
                System.out.println("Результат: " + result);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static boolean isRomanNumeral(String input) {
        String romanNumeralRegex = "^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return input.matches(romanNumeralRegex);
    }

    private static int parseOperand(String operand, boolean isRoman) {
        if (isRoman) {
            return fromRomanNumeral(operand);
        } else {
            try {
                int value = Integer.parseInt(operand);
                if (value < 1 || value > 10) {
                    throw new IllegalArgumentException("Число должно быть от 1 до 10");
                }
                return value;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат числа");
            }
        }
    }

    private static String toRomanNumeral(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть от 1 до 3999");
        }

        StringBuilder romanNumeral = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                romanNumeral.append(symbols[i]);
            }
        }

        return romanNumeral.toString();
    }

    private static int fromRomanNumeral(String romanNumeral) {
        int number = 0;
        int i = 0;

        while (i < romanNumeral.length()) {
            char currentSymbol = romanNumeral.charAt(i);
            int currentValue = getRomanNumeralValue(currentSymbol);

            if (i + 1 < romanNumeral.length()) {
                char nextSymbol = romanNumeral.charAt(i + 1);
                int nextValue = getRomanNumeralValue(nextSymbol);

                if (currentValue >= nextValue) {
                    number += currentValue;
                    i++;
                } else {
                    number += nextValue - currentValue;
                    i += 2;
                }
            } else {
                number += currentValue;
                i++;
            }
        }

        return number;
    }

    private static int getRomanNumeralValue(char symbol) {
        switch (symbol) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Неверный символ римской цифры");
        }
    }
}
