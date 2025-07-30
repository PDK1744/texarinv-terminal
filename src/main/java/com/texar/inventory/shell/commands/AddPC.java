package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.PC;
import com.texar.inventory.service.PCService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddPC implements Command {
    private final PCService pcService;

    public AddPC(PCService pcService) {
        this.pcService = pcService;
    }

    @Override
    public String getName() {
        return "add pc";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String pcNumber = prompt(scanner, "PC Number", true);
        int easeNumber = promptInt(scanner, "Ease Number", false);
        String model = prompt(scanner, "Model", true);
        int assetTag = promptInt(scanner, "Asset Tag", true);
        String serviceTag = prompt(scanner, "Service Tag", true);
        LocalDate warrantyExpiration = promptDate(scanner, "Warranty Expiration(YYYY-MM-DD)");
        String location = prompt(scanner, "Location", true);
        String branch = prompt(scanner, "Branch", true);
        String notes = prompt(scanner, "Notes", false);
        String status = prompt(scanner, "Status", true);

        PC pc = new PC(pcNumber, easeNumber, model, assetTag, serviceTag, warrantyExpiration, location, branch, notes, status);
        pcService.addPC(pc);

        System.out.println("PC Added successfully");

    }

    @Override
    public String getShortDescription() {
        return "Add a new PC";
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

    private LocalDate promptDate(Scanner scanner, String label) {
        String inputString;
        do {
            System.out.print(label + ": ");
            inputString = scanner.nextLine().trim();
            try {
                return LocalDate.parse(inputString);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD");
            }
        } while(true);
    }
}
