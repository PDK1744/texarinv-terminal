package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.Printer;
import com.texar.inventory.service.PrinterService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdatePrinter implements Command {

    private final PrinterService printerService;

    public UpdatePrinter(PrinterService PrinterService) {
        this.printerService = new PrinterService();
    }

    @Override
    public String getName() {
        return "update printer";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of the Printer you want to edit: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Cancelling.");
            return;
        }

        Printer printer = printerService.getPrinterById(id);
        if (printer == null) {
            System.out.println("No Printer found with that ID.");
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        System.out.println("Editing Printer with ID " + id);

        prompt(scanner, "Printer Number", printer.getPrinterName(), printer::setPrinterName);
        prompt(scanner, "Branch", printer.getBranch(), printer::setBranch);
        prompt(scanner, "Location", printer.getLocation(), printer::setLocation);
        prompt(scanner, "Model", printer.getModel(), printer::setModel);
        prompt(scanner, "Features", printer.getFeatures(), printer::setFeatures);
        prompt(scanner, "IP Address", printer.getIpAddress(), printer::setIpAddress);
        promptInt(scanner, "Portrait", printer.getPortrait(), printer::setPortrait);
        promptInt(scanner, "Landscape", printer.getLandscape(), printer::setLandscape);
        prompt(scanner, "Notes", printer.getNotes(), printer::setNotes);
        prompt(scanner, "Status", printer.getStatus(), printer::setStatus);

        printerService.updatePrinter(id, printer);
        System.out.println("Printer updated successfully");
    }

    private void prompt(Scanner scanner, String fieldName, String current, java.util.function.Consumer<String> callback) {
        System.out.print(fieldName + " [Current: " + current + "] (Press Enter to keep current): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            callback.accept(input);
        }
    }

    private void promptInt(Scanner scanner, String fieldName, int current, java.util.function.Consumer<Integer> callback) {
        System.out.print(fieldName + " [Current: " + current + "] (Press Enter to keep current): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                callback.accept(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, skipping " + fieldName);
            }
        }
    }



    @Override
    public String getShortDescription() {
        return "Edit Printer details. Requires ID of Printer";
    }
}
