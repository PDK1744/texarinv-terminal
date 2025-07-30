package com.texar.inventory.shell.commands;


import com.texar.inventory.helpers.PrinterSearchHelper;
import com.texar.inventory.model.Command;
import com.texar.inventory.model.SearchCommand;
import com.texar.inventory.service.PrinterService;

import java.util.*;

public class SearchPrinters implements Command, SearchCommand {
    PrinterService printerService;
    Map<String, String> flags = new HashMap<>();

    public SearchPrinters(PrinterService printerService) {
        this.printerService = printerService;
    }

    @Override
    public String getName() {
        return "search printers";
    }

    @Override
    public void execute(String[] args) {
        flags.clear();

        for (String arg : args) {
            if (arg.startsWith("--") && arg.contains("=")) {
                String newArg = arg.substring(2);
                String[] parts = newArg.split("=", 2);
                System.out.println(Arrays.toString(parts));

                if (parts.length > 1) {
                    String rawKey = parts[0].trim().toLowerCase();
                    String value = parts[1].trim();


                    String canonicalKey = PrinterSearchHelper.resolveCanonicalKey(rawKey);

                    if (canonicalKey != null && !value.isBlank()) {
                        flags.put(canonicalKey, value);
                    } else {
                        System.out.println("Unknown or invalid flag: --" + rawKey);
                        System.out.println("Returning all printers");
                    }
                }
            }
        }
        var printers = printerService.searchPrinters(flags);

        if (printers.isEmpty()) {
            System.out.println("No Printers matched your search.");
        } else {
            printers.forEach(printer -> {
                System.out.println("-----------");
                System.out.println("ID: " + printer.getId());
                System.out.println("Printer Name: " + printer.getPrinterName());
                System.out.println("Branch: " + printer.getBranch());
                System.out.println("Location: " + printer.getModel());
                System.out.println("Model: " + printer.getModel());
                System.out.println("Features: " + printer.getFeatures());
                System.out.println("IP Address: " + printer.getIpAddress());
                System.out.println("Portrait: " + printer.getLocation());
                System.out.println("Landscape: " + printer.getBranch());
                System.out.println("Notes: " + printer.getNotes());
                System.out.println("Status: " + printer.getStatus());
            });
        }

    }

    @Override
    public String getShortDescription() {
        return "Search Printers using flags";
    }

    @Override
    public String getFormattedHelpBlock() {
        StringBuilder sb = new StringBuilder();
        sb.append("      Example:\n");
        sb.append("        search printers --printer_name=PR551 --model=HP LaserJet\n");
        sb.append("      Available flags:\n");

        Set<String> fields = PrinterSearchHelper.getSearchableFields();
        Map<String, List<String>> aliases = PrinterSearchHelper.getAliasesPerField();

        for (String field : fields) {
            sb.append("        --").append(String.format("%-18s", field + "=?"))
                    .append(" (searches by ").append(field);

            if (aliases.containsKey(field)) {
                sb.append(" | aliases: ");
                sb.append(String.join(", ", aliases.get(field)));
            }

            sb.append(")\n");
        }

        return sb.toString();
    }

}
