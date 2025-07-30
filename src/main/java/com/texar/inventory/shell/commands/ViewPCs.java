package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.PC;
import com.texar.inventory.service.PCService;

import java.sql.SQLException;
import java.util.List;

public class ViewPCs implements Command {
    private final PCService pcService;

    public ViewPCs(PCService pcService) {
        this.pcService = pcService;
    }

    @Override
    public String getName() {
        return "view pcs";
    }

    @Override
    public void execute(String[] args){
        try {
            List<PC> pcs = pcService.getAllPCs();
            pcs.forEach(System.out::println);;
        } catch (SQLException e) {
            System.err.println("Error returning PCs: " + e.getMessage());
        }
    }

    @Override
    public String getShortDescription() {
        return "Displays all PCs in the system";
    }
}
