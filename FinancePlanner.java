package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinancePlanner {
    private double totalIncome;
    private List<Expense> expenses;
    private Connection connection;

    // Constructor
    public FinancePlanner(double totalIncome) {
        this.totalIncome = totalIncome;
        this.expenses = new ArrayList<>();

        // Establish database connection
        try {
            // Make sure to replace with your actual DB credentials
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_db", "root", "070604@MAk");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1); // Exit if connection fails
        }
    }

    public void addExpense(String name, double amount) {
        expenses.add(new Expense(name, amount));

        // Store expense in the database
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO expenses (name, amount) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double calculateSavings() {
        return totalIncome - calculateTotalExpenses();
    }

    public void displaySummary() {
        System.out.println("Income: " + totalIncome);
        System.out.println("Total Expenses: " + calculateTotalExpenses());
        System.out.println("Total Savings: " + calculateSavings());
        System.out.println("Expenses Breakdown:");
        for (Expense expense : expenses) {
            System.out.println("- " + expense.getName() + ": " + expense.getAmount());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your total income: ");
        double income = scanner.nextDouble();
        scanner.nextLine(); // consume the leftover newline

        FinancePlanner planner = new FinancePlanner(income);

        while (true) {
            System.out.print("Enter expense name (or 'done' to finish): ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter expense amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume the leftover newline

            planner.addExpense(name, amount);
        }

        planner.displaySummary();
        scanner.close();
    }
}