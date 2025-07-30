package com.texar.inventory.model;

import java.time.LocalDate;

public class PC {
    private int id;
    private String pcName;
    private int easeNumber;
    private String model;
    private int assetTag;
    private String serviceTag;
    private LocalDate warranty_expiration;
    private String location;
    private String branch;
    private String notes;
    private String status;

    public PC(String pcName,
              int easeNumber,
              String model,
              int assetTag,
              String serviceTag,
              LocalDate warranty_expiration,
              String location,
              String branch,
              String notes,
              String status) {
        this.pcName = pcName;
        this.easeNumber = easeNumber;
        this.model = model;
        this.assetTag = assetTag;
        this.serviceTag = serviceTag;
        this.warranty_expiration = warranty_expiration;
        this.location = location;
        this.branch = branch;
        this.notes = notes;
        this.status = status;
    }

    public PC(int id,
              String pcName,
              int easeNumber,
              String model,
              int assetTag,
              String serviceTag,
              LocalDate warranty_expiration,
              String location,
              String branch,
              String notes,
              String status) {
        this.id = id;
        this.pcName = pcName;
        this.easeNumber = easeNumber;
        this.model = model;
        this.assetTag = assetTag;
        this.serviceTag = serviceTag;
        this.warranty_expiration = warranty_expiration;
        this.location = location;
        this.branch = branch;
        this.notes = notes;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public int getEaseNumber() {
        return easeNumber;
    }

    public void setEaseNumber(int easeNumber) {
        this.easeNumber = easeNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(int assetTag) {
        this.assetTag = assetTag;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public LocalDate getWarrantyExpiration() {
        return warranty_expiration;
    }

    public void setWarrantyExpiration(LocalDate warranty_expiration) {
        this.warranty_expiration = warranty_expiration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
                | PC Number      : %-30s
                | EASE #         : %-30s
                | Model          : %-30s
                | Asset Tag      : %-30s
                | Service Tag    : %-30s
                | Warranty Exp   : %-30s
                | Location       : %-30s
                | Branch         : %-30s
                | Status         : %-30s
                | Notes          : %-30s
                +------------------------------------------------+
                """,
                pcName,
                id,
                pcName,
                easeNumber,
                model,
                assetTag,
                serviceTag,
                warranty_expiration,
                location,
                branch,
                status,
                notes == null ? "" : notes
        );
    }


}
