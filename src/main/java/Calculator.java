

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Calculator {

    /**
     * Performs calculations on Roman numerals and prints the result.
     * <p>
     * If either of the numbers are not Roman numerals, or if the operation is
     * unrecognized, prints an error message.  Otherwise, performs the
     * operation and prints the result in Roman numerals.  If the result is
     * less than 1 or larger than 3999, prints a message indicating this
     * instead.
     *
     * @param leftNumber  The left operand, in Roman numerals.
     * @param operation   The operator, which may be,
     * - "+" for addition,
     * - "-" for subtraction,
     * - "*" for multiplication,
     * - "/" for (integer) division,
     * - "%" for remainder,
     * - "#" to average the two numbers.
     * @param rightNumber The right operand.
     */

    private static final Logger logger = Logger.getLogger(Calculator.class);

    public static void calculate(String leftNumber, String operation, String rightNumber) throws IOException {

        boolean opValid;
        int leftInt, rightInt;
        int resultInt = 0;

        if (NumberUtils.isCreatable(leftNumber))
            leftInt = Integer.parseInt(leftNumber);
        else if (RomanNumerals.parse(rightNumber.toUpperCase()) != -1)
            leftInt = RomanNumerals.parse(leftNumber.toUpperCase());
        else leftInt = -1;

        if (NumberUtils.isCreatable(rightNumber))
            rightInt = Integer.parseInt(rightNumber);
        else if (RomanNumerals.parse(leftNumber.toUpperCase()) != -1)
            rightInt = RomanNumerals.parse(rightNumber.toUpperCase());
        else rightInt = -1;

        valueOfNumbers(leftInt);

        valueOfNumbers(rightInt);

        if (operation.equals("+") || operation.equals("-") || operation.equals("*") ||
                operation.equals("/") || operation.equals("%") || operation.equals("#")) {
            opValid = true;
        } else {
            logger.debug("invalid operation");
            //  System.exit(0);
            throw new NoSuchElementException();
        }

        switch (operation) {
            case "+":
                resultInt = leftInt + rightInt;
                break;

            //Subtraction
            case "-":
                resultInt = leftInt - rightInt;
                break;

            //Multiplication
            case "*":
                resultInt = leftInt * rightInt;
                break;

            //Division
            case "/":
                resultInt = leftInt / rightInt;
                break;

            //Modulo
            case "%":
                resultInt = leftInt % rightInt;
                break;

            //Average
            case "#":
                resultInt = (leftInt + rightInt) / 2;
                break;
        }

        // If result is in range, formats to roman numeral and displays to console
//                if (resultInt <= 0 || resultInt >= 100) {
//                    System.out.println("result out of range");
//                }
        if (NumberUtils.isCreatable(leftNumber) && NumberUtils.isCreatable(rightNumber))
            System.out.println(resultInt);
        else {
            System.out.println(RomanNumerals.format(resultInt));
        }

    }

    private static void valueOfNumbers(int num) {
        if (num == -1) {
            logger.error("Числа должны быть либо арабскими, либо римскими");
            throw new NoSuchElementException("Система записи чисел должны быть либо арабской, " +
                    "либо римской");
        }
        if (num >= 10 || num == 0) {
            logger.error("Введено число меньше 0 или больше 10");
            throw new NoSuchElementException("Введите число больше 0 и меньше 10");
        }
    }


//    public static int parseDecimalNumber(String number) {
//        try {
//            return Integer.parseInt(number);
//        } catch (NumberFormatException e) {
//            return -1;
//        }
//    }

    /**
     * @note You do not need to understand how this function works.
     */
    public static void main(String[] args) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Show the prompt.
            System.out.println("Введите выражение одной строкой через пробел: ");
            // Read a line of input.
            final String line = reader.readLine();

            if (line.length() == 0) {
                throw new IOException("Пустая строка");
            }

            // Scan the line into three parts: two numbers with an operator between them.
            final Scanner scanner = new Scanner(line);
            final String leftNumber, operation, rightNumber;

            try {
                leftNumber = scanner.next();
                operation = scanner.next();
                rightNumber = scanner.next();
                if (scanner.hasNext()){
                    throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
            } catch (NoSuchElementException e) {
               throw new NoSuchElementException("Выражение должно быть записано одной строкой через пробел");
            }
            calculate(leftNumber, operation, rightNumber);
        }
    }
}

