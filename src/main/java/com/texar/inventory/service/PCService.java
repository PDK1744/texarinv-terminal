package com.texar.inventory.service;

import com.texar.inventory.db.DatabaseManager;
import com.texar.inventory.helpers.PCSearchHelper;
import com.texar.inventory.model.PC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PCService {
    private final DatabaseManager db;
//    private String currentUser;

    public PCService() {
        this.db = DatabaseManager.getInstance();
//        this.currentUser = currentUser;
    }

    public void addPC(PC pc) {
        String sql = "INSERT INTO pc_inv (pc_number, ease_number, model, asset_tag, service_tag, warranty_expiration, location, branch, notes, status) " +
                "VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pc.getPcName());
            stmt.setInt(2, pc.getEaseNumber());
            stmt.setString(3, pc.getModel());
            stmt.setInt(4, pc.getAssetTag());
            stmt.setString(5, pc.getServiceTag());
            stmt.setObject(6, pc.getWarrantyExpiration());
            stmt.setString(7, pc.getLocation());
            stmt.setString(8, pc.getBranch());
            stmt.setString(9, pc.getNotes());
            stmt.setString(10, pc.getStatus());

            stmt.executeUpdate();
            System.out.println(pc.getPcName() + " added successfully");

        } catch (SQLException e) {
            System.err.println("Error adding PC: " + e.getMessage());
        }
    }

    public List<PC> getAllPCs() throws SQLException {
        String sql = "SELECT * FROM pc_inv ORDER BY pc_number ASC";
        List<PC> pcList = new ArrayList<>();

        try (Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String pcName = rs.getString("pc_number");
                int easeNumber = rs.getInt("ease_number");
                String model = rs.getString("model");
                int assetTag = rs.getInt("asset_tag");
                String serviceTag = rs.getString("service_tag");
                LocalDate warrantyExpiration = rs.getObject("warranty_expiration", LocalDate.class);
                String location = rs.getString("location");
                String branch = rs.getString("branch");
                String notes = rs.getString("notes");
                String status = rs.getString("status");

                PC pc = new PC(id, pcName, easeNumber, model, assetTag, serviceTag, warrantyExpiration, location, branch, notes, status);

                pcList.add(pc);

            }
        } catch (SQLException e) {
            System.err.println("Error returning PCs: " + e.getMessage());
        }

        return pcList;
    }

    public List<PC> searchPCs(Map<String, String> flags) {
        List<PC> pcList = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        String sql = "SELECT * FROM pc_inv WHERE ";
        StringBuilder sb = new StringBuilder(sql);
        if (flags.isEmpty()) {
            try {
                return getAllPCs();
            } catch (SQLException e) {
                System.err.println("Error searching PCs: " + e.getMessage());
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
                String type = PCSearchHelper.getColumnType(key);
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
                    String pcName = rs.getString("pc_number");
                    int easeNumber = rs.getInt("ease_number");
                    String model = rs.getString("model");
                    int assetTag = rs.getInt("asset_tag");
                    String serviceTag = rs.getString("service_tag");
                    LocalDate warrantyExpiration = rs.getObject("warranty_expiration", LocalDate.class);
                    String location = rs.getString("location");
                    String branch = rs.getString("branch");
                    String notes = rs.getString("notes");
                    String status = rs.getString("status");

                    PC pc = new PC(id, pcName, easeNumber, model, assetTag, serviceTag, warrantyExpiration, location, branch, notes, status);
                    pcList.add(pc);
                }
            }

        } catch(SQLException e) {
            System.err.println("Error searching PCs: " + e.getMessage());
        }
        return pcList;
    }

    public PC getPCById(int id) {
        String sql = "SELECT * FROM pc_inv WHERE id = ?";

        try (Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String pcName = rs.getString("pc_number");
                    int easeNumber = rs.getInt("ease_number");
                    String model = rs.getString("model");
                    int assetTag = rs.getInt("asset_tag");
                    String serviceTag = rs.getString("service_tag");
                    LocalDate warrantyExpiration = rs.getObject("warranty_expiration", LocalDate.class);
                    String location = rs.getString("location");
                    String branch = rs.getString("branch");
                    String notes = rs.getString("notes");
                    String status = rs.getString("status");

                    return new PC(id, pcName, easeNumber, model, assetTag, serviceTag, warrantyExpiration, location, branch, notes, status);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching PC by ID: " + e.getMessage());
        }
        return null;
    }

    public void updatePC(int id, PC pc) {
        String sql = "UPDATE pc_inv SET pc_number = ?, ease_number = ?, model = ?, asset_tag = ?, service_tag = ?, warranty_expiration = ?, location = ?, branch = ?, notes = ?, status = ? WHERE id = ?";

        try (Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pc.getPcName());
            stmt.setInt(2, pc.getEaseNumber());
            stmt.setString(3, pc.getModel());
            stmt.setInt(4, pc.getAssetTag());
            stmt.setString(5, pc.getServiceTag());
            stmt.setObject(6, pc.getWarrantyExpiration());
            stmt.setString(7, pc.getLocation());
            stmt.setString(8, pc.getBranch());
            stmt.setString(9, pc.getNotes());
            stmt.setString(10, pc.getStatus());
            stmt.setInt(11, pc.getId());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error updating PC: " + e.getMessage());
        }
    }

    public void deletePC(int id) {
        String sql = "DELETE FROM pc_inv WHERE id = ?";

        try (Connection conn = db.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("PC deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting PC: " + e.getMessage());
        }
    }

}
