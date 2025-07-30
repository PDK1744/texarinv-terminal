package com.texar.inventory.service;

import com.texar.inventory.db.DatabaseManager;
import com.texar.inventory.helpers.PrinterSearchHelper;
import com.texar.inventory.model.Printer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrinterService {

    private final DatabaseManager db;

    public PrinterService() {
        this.db = DatabaseManager.getInstance();
    }

    public void addPrinter(Printer printer) {
        String sql = "INSERT INTO printer_inv (printer_name, branch, location, model, features, ip_address, portrait, landscape, notes, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, printer.getPrinterName());
            stmt.setString(2, printer.getBranch());
            stmt.setString(3, printer.getLocation());
            stmt.setString(4, printer.getModel());
            stmt.setString(5, printer.getFeatures());
            stmt.setString(6, printer.getIpAddress());
            stmt.setInt(7, printer.getPortrait());
            stmt.setInt(8, printer.getLandscape());
            stmt.setString(9, printer.getNotes());
            stmt.setString(10, printer.getStatus());

            stmt.executeUpdate();
            System.out.println(printer.getPrinterName() + " added successfully");

        } catch (SQLException e) {
            System.err.println("Error adding Printer: " + e.getMessage());
        }
    }

    public List<Printer> getAllPrinters() throws SQLException {
        String sql = "SELECT * FROM printer_inv ORDER BY printer_name ASC";
        List<Printer> printerList = new ArrayList<>();

        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String printerNumber = rs.getString("printer_name");
                String branch = rs.getString("branch");
                String location = rs.getString("location");
                String model = rs.getString("model");
                String features = rs.getString("features");
                String ipAddress = rs.getString("ip_address");
                int portrait = rs.getInt("portrait");
                int landscape = rs.getInt("landscape");
                String notes = rs.getString("notes");
                String status = rs.getString("status");

                Printer printer = new Printer(id, printerNumber, branch, location, model, features, ipAddress, portrait, landscape, notes, status);


                printerList.add(printer);

            }
        } catch (SQLException e) {
            System.err.println("Error returning Printers: " + e.getMessage());
        }

        return printerList;
    }

    public List<Printer> searchPrinters(Map<String, String> flags) {
        List<Printer> printerList = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        String sql = "SELECT * FROM printer_inv WHERE ";
        StringBuilder sb = new StringBuilder(sql);
        if (flags.isEmpty()) {
            try {
                return getAllPrinters();
            } catch (SQLException e) {
                System.err.println("Error searching Printers: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            int count = 0;
            for (String flag : flags.keySet()) {
                String value = flags.get(flag);
                keys.add(flag);
                values.add(value);
                if (count == flags.size() - 1) {
                    sb.append(flag);
                    sb.append(" ");
                    sb.append("=");
                    sb.append(" ");
                    sb.append("?");
                } else {
                    sb.append(flag);
                    sb.append(" ");
                    sb.append("=");
                    sb.append(" ");
                    sb.append("?");
                    sb.append(" ");
                    sb.append("AND");
                    sb.append(" ");
                }
                count++;
            }

        }
        String finalSql = sb.toString();
        try(Connection conn = db.connect();
            PreparedStatement stmt = conn.prepareStatement(finalSql);) {
            for (int i = 0; i < values.size(); i++) {
                String key = keys.get(i);
                String value = values.get(i);
                String type = PrinterSearchHelper.getColumnType(key);
                if (type == null) {
                    System.err.println("Unknown column: " + key);
                    continue;
                }

                if (type.equals("int")) {
                    stmt.setInt(i + 1, Integer.parseInt(value));
                } else {
                    stmt.setString(i + 1, value);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String printerNumber = rs.getString("printer_name");
                    String branch = rs.getString("branch");
                    String location = rs.getString("location");
                    String model = rs.getString("model");
                    String features = rs.getString("features");
                    String ipAddress = rs.getString("ip_address");
                    int portrait = rs.getInt("portrait");
                    int landscape = rs.getInt("landscape");
                    String notes = rs.getString("notes");
                    String status = rs.getString("status");

                    Printer printer = new Printer(id, printerNumber, branch, location, model, features, ipAddress, portrait, landscape, notes, status);


                    printerList.add(printer);
                }
            }

        } catch(SQLException e) {
            System.err.println("Error searching Printers: " + e.getMessage());
        }
        return printerList;
    }

    public Printer getPrinterById(int id) {
        String sql = "SELECT * FROM printer_inv WHERE id = ?";

        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String printerNumber = rs.getString("printer_name");
                    String branch = rs.getString("branch");
                    String location = rs.getString("location");
                    String model = rs.getString("model");
                    String features = rs.getString("features");
                    String ipAddress = rs.getString("ip_address");
                    int portrait = rs.getInt("portrait");
                    int landscape = rs.getInt("landscape");
                    String notes = rs.getString("notes");
                    String status = rs.getString("status");

                    return new Printer(id, printerNumber, branch, location, model, features, ipAddress, portrait, landscape, notes, status);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Printer by ID: " + e.getMessage());
        }
        return null;
    }

    public void updatePrinter(int id, Printer printer) {
        String sql = "UPDATE printer_inv SET printer_name = ?, branch = ?, location = ?, model = ?, features = ?, ip_address = ?, portrait = ?, landscape = ?, notes = ?, status = ? WHERE id = ?";

        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, printer.getPrinterName());
            stmt.setString(2, printer.getBranch());
            stmt.setString(3, printer.getLocation());
            stmt.setString(4, printer.getModel());
            stmt.setString(5, printer.getFeatures());
            stmt.setString(6, printer.getIpAddress());
            stmt.setInt(7, printer.getPortrait());
            stmt.setInt(8, printer.getLandscape());
            stmt.setString(9, printer.getNotes());
            stmt.setString(10, printer.getStatus());
            stmt.setInt(11, printer.getId());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error updating Printer: " + e.getMessage());
        }
    }

    public void deletePrinter(int id) {
        String sql = "DELETE FROM printer_inv WHERE id = ?";

        try (Connection conn = db.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Printer deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting Printer: " + e.getMessage());
        }
    }
}
