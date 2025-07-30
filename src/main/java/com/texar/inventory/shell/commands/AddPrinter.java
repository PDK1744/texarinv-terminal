package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.PC;
import com.texar.inventory.model.Printer;
import com.texar.inventory.service.PCService;
import com.texar.inventory.service.PrinterService;


import java.time.LocalDate;
import java.util.Scanner;

public class AddPrinter implements Command {
    private final PrinterService printerService;

    public AddPrinter(PrinterService printerService) {
        this.printerService = printerService;
    }

    @Override
    public String getName() {
        return "add printer";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String printerNumber = prompt(scanner, "Printer Number", true);
        String branch = prompt(scanner, "Branch", false);
        String location = prompt(scanner, "Location", true);
        String model = prompt(scanner, "Model", true);
        String features = prompt(scanner, "Features", false);
        String ipAddress = prompt(scanner, "IP Address", false);
        int portrait = promptInt(scanner, "Portrait", true);
        int landscape = promptInt(scanner, "Landscape", true);
        String notes = prompt(scanner, "Notes", false);
        String status = prompt(scanner, "Status", true);

        Printer printer = new Printer(printerNumber, branch, location, model, features, ipAddress, portrait, landscape, notes, status);
        printerService.addPrinter(printer);

        System.out.println("Printer Added successfully");

    }

    @Override
    public String getShortDescription() {
        return "Add a new Printer";
    }

    private String prompt(Scanner scanner, String label, boolean required) {
        String input;
        do {
            System.out.print(label + ": ");;
            input = scanner.nextLine().trim();
            if (!required || !input.isEmpty()) break;
            System.out.println(label + " is required");
        } while (true);
        return input;
    }

    private int promptInt(Scanner scanner, String label, boolean required) {
        String inputString;
        do {
            System.out.print(label + ": ");;
            inputString = scanner.nextLine().trim();
            if (!required || !inputString.isEmpty()) break;
            System.out.println(label + " is required");
        } while (true);
        return Integer.parseInt(inputString);
    }
}
