package com.texar.inventory.model;

public class Printer {
    private int id;
    private String printerName;
    private String branch;
    private String location;
    private String model;
    private String features;
    private String ipAddress;
    private int portrait;
    private int landscape;
    private String notes;
    private String status;

    public Printer(String printerName, String branch, String location, String model, String features, String ipAddress, int portrait, int landscape, String notes, String status) {
        this.printerName = printerName;
        this.branch = branch;
        this.location = location;
        this.model = model;
        this.features = features;
        this.ipAddress = ipAddress;
        this.portrait = portrait;
        this.landscape = landscape;
        this.notes = notes;
        this.status = status;
    }

    public Printer(int id, String printerName, String branch, String location, String model, String features, String ipAddress, int portrait, int landscape, String notes, String status) {
        this.id = id;
        this.printerName = printerName;
        this.branch = branch;
        this.location = location;
        this.model = model;
        this.features = features;
        this.ipAddress = ipAddress;
        this.portrait = portrait;
        this.landscape = landscape;
        this.notes = notes;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public int getLandscape() {
        return landscape;
    }

    public void setLandscape(int landscape) {
        this.landscape = landscape;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                """
                +------------------ %s DETAILS ---------------+
                | ID             : %-30s
                | Printer Number : %-30s
                | Branch         : %-30s
                | Location       : %-30s
                | Model          : %-30s
                | Features       : %-30s
                | IP Address     : %-30s
                | Portrait       : %-30s
                | Landscape      : %-30s
                | Status         : %-30s
                | Notes          : %-30s
                +------------------------------------------------+
                """,
                printerName,
                id,
                printerName,
                branch,
                location,
                model,
                features,
                ipAddress,
                portrait,
                landscape,
                status,
                notes == null ? "" : notes
        );
    }
}
