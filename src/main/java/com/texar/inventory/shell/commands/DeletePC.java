package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.PC;
import com.texar.inventory.service.PCService;

import java.sql.SQLException;
import java.util.Scanner;

public class DeletePC implements Command {

    private final PCService pcService;

    public DeletePC() {
        this.pcService = new PCService();
    }

    @Override
    public String getName() {
        return "delete pc";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID of the PC you want to delete: ");
        int id;
        String userChoice;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Cancelling.");
            return;
        }
        PC pc = pcService.getPCById(id);
        try {
            System.out.print("Are you sure you want to delete: " + pc.getPcName() + "(" + "ID:" + pc.getId() + ")" + " " + "(Y/N):");
            userChoice = scanner.nextLine().trim();
            if (userChoice.equalsIgnoreCase("Y")) {
                pcService.deletePC(id);
            } else {
                System.out.println("Deletion Cancelled.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getShortDescription() {
        return "Delete PC Entry. Requires ID of PC";
    }
}
