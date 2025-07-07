//New project Car Showroom
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.*;

public class CARSHOWROOZW {

    
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/carshowroom";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "5202";

    public static void main(String[] args) {
        
       
        
        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();
        String deb, debu, query, address, selectedCar = "k";
        int k, debn, carPrice = 0;
        JOptionPane.showMessageDialog(null, "Welcome to Coding Duniya");
        JOptionPane.showMessageDialog(null, "Welcome to Car Showroom");
        String name = JOptionPane.showInputDialog("Enter your name");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!");
            System.exit(0);
        }

      
        if (name.equalsIgnoreCase("devlopHarsh")) {
            k = Integer.parseInt(JOptionPane.showInputDialog("Enter Password"));
            if (k == 5202) {
                deb = JOptionPane.showInputDialog("Enter your action (Insert/Update)");
                
                if (deb.equalsIgnoreCase("Insert")) {
                
                    debu = JOptionPane.showInputDialog("Enter the name of the car");
                    debn = Integer.parseInt(JOptionPane.showInputDialog("Enter its price"));
                    
                    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO cars_name(name, price) VALUES(?, ?)")) {
                        
                        preparedStatement.setString(1, debu);
                        preparedStatement.setInt(2, debn);
                        int rowsAffected = preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Row Inserted Successfully: " + rowsAffected);
                        
                    } catch (SQLException e) {
                        showDatabaseError(e);
                    }
                    System.exit(0);
                }
                else if (deb.equalsIgnoreCase("Update")) {
                   
                    debu = JOptionPane.showInputDialog("Enter the name of the car to update");
                    debn = Integer.parseInt(JOptionPane.showInputDialog("Enter its new price"));
                    
                    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         PreparedStatement pstmt = connection.prepareStatement(
                             "UPDATE cars_name SET price = ? WHERE name = ?")) {
                        
                        pstmt.setInt(1, debn);
                        pstmt.setString(2, debu);
                        int rowsAffected = pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Rows updated: " + rowsAffected);
                        
                    } catch (SQLException e) {
                        showDatabaseError(e);
                    }
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Password");
                System.exit(0);
            }
        }

        
        JOptionPane.showMessageDialog(null, "Hello " + name + "!!!");
        address = JOptionPane.showInputDialog("Enter your address");
        
       
        int budget = Integer.parseInt(JOptionPane.showInputDialog("Enter your budget"));
        
        if (budget > 100000 && budget <= 2000000) {
            query = "SELECT name, price FROM cars_name WHERE price > 100000 AND price <= 2000000";
            connectionmaker(query);
            selectedCar = JOptionPane.showInputDialog("Enter the car you want to purchase");
            carPrice = getCarPrice(selectedCar);
        } 
        else if (budget > 2000000 && budget <= 3000000) {
            query = "SELECT name, price FROM cars_name WHERE price > 2000000 AND price <= 3000000";
            connectionmaker(query);
            selectedCar = JOptionPane.showInputDialog("Enter the car you want to purchase");
            carPrice = getCarPrice(selectedCar);
        } 
        else if (budget > 3000000 && budget <= 4000000) {
            query = "SELECT name, price FROM cars_name WHERE price > 3000000 AND price <= 4000000";
            connectionmaker(query);
            selectedCar = JOptionPane.showInputDialog("Enter the car you want to purchase");
            carPrice = getCarPrice(selectedCar);
        } 
        else if (budget > 4000000 && budget <= 6000000) {
            query = "SELECT name, price FROM cars_name WHERE price > 4000000 AND price <= 6000000";
            connectionmaker(query);
            selectedCar = JOptionPane.showInputDialog("Enter the car you want to purchase");
            carPrice = getCarPrice(selectedCar);
        } 
        else if (budget > 6000000) {
            query = "SELECT name, price FROM cars_name WHERE price > 6000000";
            connectionmaker(query);
            selectedCar = JOptionPane.showInputDialog("Enter the car you want to purchase");
            carPrice = getCarPrice(selectedCar);
        } 
        else {
            JOptionPane.showMessageDialog(null, "Sorry, we don't have cars in your budget range");
            System.exit(0);
        }

        
        if (selectedCar != null && !selectedCar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, selectedCar + " selected");
            k = Integer.parseInt(JOptionPane.showInputDialog("Press 1 to confirm purchase"));
            
            if (k == 1) {
                JOptionPane.showMessageDialog(null, "Your order is confirmed successfully");
                JOptionPane.showMessageDialog(null, "Bill generated successfully\nTotal: " + carPrice + " (incl. taxes)");
                
               
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO constumer(name, address, date_, ammount, car_name) VALUES(?, ?, ?, ?, ?)")) {
                    
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, address);
                    preparedStatement.setString(3, dateString);
                    preparedStatement.setInt(4, carPrice);
                    preparedStatement.setString(5, selectedCar);
                    
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Thank you for your purchase!");
                    }
                    
                } catch (SQLException e) {
                    showDatabaseError(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Purchase cancelled. Visit again!");
            }
        }
        
        System.exit(0);
    }

    
    public static int getCarPrice(String carName) {
        int price = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT price FROM cars_name WHERE name = ?")) {
            
            preparedStatement.setString(1, carName);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                price = rs.getInt("price");
            }
            
        } catch (SQLException e) {
            showDatabaseError(e);
        }
        return price;
    }

    
    public static void connectionmaker(String query) {
        JFrame frame = new JFrame("Available Cars");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Car Name");
        tableModel.addColumn("Price (₹)");
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                String carName = resultSet.getString("name");
                int carPrice = resultSet.getInt("price");
                tableModel.addRow(new Object[]{carName, String.format("%,d", carPrice)});
            }
            
        } catch (SQLException e) {
            showDatabaseError(e);
            return;
        }
        
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    
    private static void showDatabaseError(SQLException e) {
        JOptionPane.showMessageDialog(null, 
            "Database Error: " + e.getMessage() + 
            "\nError Code: " + e.getErrorCode() + 
            "\nSQL State: " + e.getSQLState(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
