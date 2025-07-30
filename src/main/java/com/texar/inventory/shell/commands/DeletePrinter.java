package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.Printer;
import com.texar.inventory.service.PrinterService;

import java.sql.SQLException;
import java.util.Scanner;

public class DeletePrinter implements Command {

    private final PrinterService printerService;

    public DeletePrinter() {
        this.printerService = new PrinterService();
    }

    @Override
    public String getName() {
        return "delete printer";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID of the Printer you want to delete: ");
        int id;
        String userChoice;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Cancelling.");
            return;
        }
        Printer printer = printerService.getPrinterById(id);
        try {
            System.out.print("Are you sure you want to delete: " + printer.getPrinterName() + "(" + "ID:" + printer.getId() + ")" + " " + "(Y/N):");
            userChoice = scanner.nextLine().trim();
            if (userChoice.equalsIgnoreCase("Y")) {
                printerService.deletePrinter(id);
            } else {
                System.out.println("Deletion Cancelled.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getShortDescription() {
        return "Delete Printer Entry. Requires ID of Printer";
    }
}
