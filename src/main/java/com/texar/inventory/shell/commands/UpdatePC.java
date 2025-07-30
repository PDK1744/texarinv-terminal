package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.PC;
import com.texar.inventory.service.PCService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdatePC implements Command {

    private final PCService pcService;

    public UpdatePC(PCService pcService) {
        this.pcService = new PCService();
    }

    @Override
    public String getName() {
        return "update pc";
    }

    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of the PC you want to edit: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Cancelling.");
            return;
        }

        PC pc = pcService.getPCById(id);
        if (pc == null) {
            System.out.println("No PC found with that ID.");
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        System.out.println("Editing PC with ID " + id);

        prompt(scanner, "PC Number", pc.getPcName(), pc::setPcName);
        promptInt(scanner, "Ease Number", pc.getEaseNumber(), pc::setEaseNumber);
        prompt(scanner, "Model", pc.getModel(), pc::setModel);
        promptInt(scanner, "Asset Tag", pc.getAssetTag(), pc::setAssetTag);
        prompt(scanner, "Service Tag", pc.getServiceTag(), pc::setServiceTag);
        promptDate(scanner, "Warranty Expiration (YYYY-MM-DD)", pc.getWarrantyExpiration(), pc::setWarrantyExpiration);
        prompt(scanner, "Location", pc.getLocation(), pc::setLocation);
        prompt(scanner, "Branch", pc.getBranch(), pc::setBranch);
        prompt(scanner, "Notes", pc.getNotes(), pc::setNotes);
        prompt(scanner, "Status", pc.getStatus(), pc::setStatus);

        pcService.updatePC(id, pc);
        System.out.println("PC updated successfully");
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

    private void promptDate(Scanner scanner, String fieldName, LocalDate current, java.util.function.Consumer<LocalDate> callback) {
        System.out.print(fieldName + " [Current: " + current + "] (Press Enter to keep current): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                callback.accept(LocalDate.parse(input));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, skipping " + fieldName);
            }
        }
    }

    @Override
    public String getShortDescription() {
        return "Edit PC details. Requires ID of PC";
    }
}
