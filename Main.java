package com.employee;

import java.sql.*;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n--- Employee Database Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                addEmployee();
            } else if (choice == 2) {
                viewEmployees();
            } else if (choice == 3) {
                updateEmployee();
            } else if (choice == 4) {
                deleteEmployee();
            } else if (choice == 5) {
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addEmployee() {
        try (Connection con = DBUtil.getConnection()) {
            String sql = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            scanner.nextLine();  // clear buffer

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter position: ");
            String position = scanner.nextLine();

            System.out.print("Enter salary: ");
            double salary = scanner.nextDouble();

            ps.setString(1, name);
            ps.setString(2, position);
            ps.setDouble(3, salary);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    static void viewEmployees() {
        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT * FROM employee";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s %-20s %-20s %-10s\n", "ID", "Name", "Position", "Salary");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");

                System.out.printf("%-5d %-20s %-20s %-10.2f\n", id, name, position, salary);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing employees: " + e.getMessage());
        }
    }

    static void updateEmployee() {
        try (Connection con = DBUtil.getConnection()) {
            System.out.print("Enter employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new position: ");
            String position = scanner.nextLine();

            System.out.print("Enter new salary: ");
            double salary = scanner.nextDouble();

            String sql = "UPDATE employee SET name = ?, position = ?, salary = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, position);
            ps.setDouble(3, salary);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee(s) updated.");
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    static void deleteEmployee() {
        try (Connection con = DBUtil.getConnection()) {
            System.out.print("Enter employee ID to delete: ");
            int id = scanner.nextInt();

            String sql = "DELETE FROM employee WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee(s) deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}