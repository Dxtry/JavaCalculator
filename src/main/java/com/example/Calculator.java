package com.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

    public double add (double a, double b) {
        return a + b;
    }

    public double substraction (double a, double b) {
        return a - b;
    }

    public double multiply (double a, double b) {
        return a * b;
    }

    public double divide (double a, double b) {
        if(b == 0) {
            throw new ArithmeticException("Divide by zero is not allowed!");
        }
        return a / b;
    }

    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);
        Calculator calc = new Calculator();
        DatabaseManager db = new DatabaseManager();


        while (true) {
            System.out.println("Enter the first number (or 'q' to quit): ");
            if (sc.hasNext("q")) {
                break;
            }

            double num1 = 0;
            double num2 = 0;
            try {
                num1 = sc.nextDouble();
                System.out.println("Enter the second number");
                num2 = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number");
                sc.next();
                continue;
            }

            System.out.println("Choose operation: (+, -, *, /)");
            char operation = ' ';

            try {
                operation = sc.next().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invalid operation! Please use : +, -, *, /.");
                sc.next();
                continue;
            }

            double result;
            String operationStr = "";
            try {
                switch (operation) {
                    case '+':
                        result = calc.add(num1, num2);
                        operationStr = "add";
                        break;
                    case '-':
                        result = calc.substraction(num1, num2);
                        operationStr = "substraction";
                        break;
                    case '*':
                        result = calc.multiply(num1, num2);
                        operationStr = "multiply";
                        break;
                    case '/':
                        result = calc.divide(num1, num2);
                        operationStr = "divide";
                        break;
                    default:
                        System.out.println("Invalid operation!");
                        continue;
                }

                if (db.isConnected()) {
                    db.saveOperation(operationStr, num1, num2, result);
                } else {
                    System.out.println("Cannot save operation: No database connected!");
                }

                System.out.println("Result: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }
            sc.nextLine();
        }

        sc.close();
        if (db.isConnected()) db.closeConnection();
    }
}
