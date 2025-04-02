import org.apache.commons.csv.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SalesDataAnalysis {

    // Method to load sales data from CSV
    public static List<SaleRecord> loadSalesData(String filePath) {
        List<SaleRecord> sales = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : parser) {
                String date = record.get("Date");
                double amount = Double.parseDouble(record.get("Amount"));
                sales.add(new SaleRecord(date, amount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sales;
    }

    // Method to perform basic statistics (total sales, average sales)
    public static void performBasicAnalytics(List<SaleRecord> sales) {
        double totalSales = 0;
        double averageSales = 0;
        int numSales = sales.size();

        for (SaleRecord sale : sales) {
            totalSales += sale.getAmount();
        }

        if (numSales > 0) {
            averageSales = totalSales / numSales;
        }

        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Average Sales: $" + averageSales);
    }

    // Method to perform simple linear regression for future prediction
    public static void performRegression(List<SaleRecord> sales) {
        SimpleRegression regression = new SimpleRegression();
        
        // Adding data points (time in days vs sales amount)
        int dayCounter = 1;
        for (SaleRecord sale : sales) {
            regression.addData(dayCounter++, sale.getAmount());
        }
        
        // Predicting future sales (e.g., predicting sales for day 100)
        double predictedSaleAtDay100 = regression.predict(100);
        System.out.println("Predicted sales for Day 100: $" + predictedSaleAtDay100);
    }

    public static void main(String[] args) {
        // Load the data
        String filePath = "sales.csv";  // Path to the CSV file
        List<SaleRecord> sales = loadSalesData(filePath);

        // Perform basic analytics
        performBasicAnalytics(sales);

        // Perform linear regression to predict future sales
        performRegression(sales);
    }
}

// SaleRecord class to store individual sales data
class SaleRecord {
    private String date;
    private double amount;

    public SaleRecord(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
