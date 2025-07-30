package com.texar.inventory.shell.commands;

import com.texar.inventory.helpers.PCSearchHelper;
import com.texar.inventory.model.Command;
import com.texar.inventory.model.SearchCommand;
import com.texar.inventory.service.PCService;

import java.util.*;

public class SearchPCs implements Command, SearchCommand {
    PCService pcService = new PCService();
    Map<String, String> flags = new HashMap<>();

    public SearchPCs(PCService pcService) {
        this.pcService = pcService;
    }
    @Override
    public String getName() {
        return "search pcs";
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


                    String canonicalKey = PCSearchHelper.resolveCanonicalKey(rawKey);

                    if (canonicalKey != null && !value.isBlank()) {
                        flags.put(canonicalKey, value);
                    } else {
                        System.out.println("Unknown or invalid flag: --" + rawKey);
                        System.out.println("Returning all PCs");
                    }
                }
            }
        }
        var pcs = pcService.searchPCs(flags);

        if (pcs.isEmpty()) {
            System.out.println("No PCs matched your search.");
        } else {
            pcs.forEach(pc -> {
                System.out.println("-----------");
                System.out.println("ID: " + pc.getId());
                System.out.println("PC Name: " + pc.getPcName());
                System.out.println("Ease Number: " + pc.getEaseNumber());
                System.out.println("Model: " + pc.getModel());
                System.out.println("Asset Tag: " + pc.getAssetTag());
                System.out.println("Service Tag: " + pc.getServiceTag());
                System.out.println("Warranty Exp: " + pc.getWarrantyExpiration());
                System.out.println("Location: " + pc.getLocation());
                System.out.println("Branch: " + pc.getBranch());
                System.out.println("Status: " + pc.getStatus());
            });
        }

    }

    @Override
    public String getShortDescription() {
        return "Search PCs using flags";
    }

    @Override
    public String getFormattedHelpBlock() {
        StringBuilder sb = new StringBuilder();
        sb.append("      Example:\n");
        sb.append("        search pcs --pc_number=PC551 --ease_number=12345\n");
        sb.append("      Available flags:\n");

        Set<String> fields = PCSearchHelper.getSearchableFields();
        Map<String, List<String>> aliases = PCSearchHelper.getAliasesPerField();

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
