package com.texar.inventory.helpers;

import java.util.*;

public class PrinterSearchHelper {

    private static final Map<String, String> COLUMN_TYPES = new HashMap<>();
    private static final Map<String, String> FLAG_ALIASES = Map.ofEntries(
            Map.entry("printer_name", "printer_name"),
            Map.entry("printername", "printer_name"),
            Map.entry("printer_number", "printer_name"),
            Map.entry("printernumber", "printer_name"),
            Map.entry("printer", "printer_name"),
            Map.entry("pr", "printer_name"),
            Map.entry("branch", "branch"),
            Map.entry("location", "location"),
            Map.entry("model", "model"),
            Map.entry("features", "features"),
            Map.entry("desc", "features"),
            Map.entry("ip_address", "ip_address"),
            Map.entry("ipaddress", "ip_address"),
            Map.entry("ip", "ip_address"),
            Map.entry("portrait", "portrait"),
            Map.entry("landscape", "landscape"),
            Map.entry("notes", "notes"),
            Map.entry("status", "status")
    );

    static {
        COLUMN_TYPES.put("id", "int");
        COLUMN_TYPES.put("printer_name", "string");
        COLUMN_TYPES.put("branch", "string");
        COLUMN_TYPES.put("location", "string");
        COLUMN_TYPES.put("model", "string");
        COLUMN_TYPES.put("features", "string");
        COLUMN_TYPES.put("ip_address", "string");
        COLUMN_TYPES.put("portrait", "int");
        COLUMN_TYPES.put("landscape", "int");
        COLUMN_TYPES.put("notes", "string");
        COLUMN_TYPES.put("status", "string");


    }

    public static Set<String> getSearchableFields() {
        return COLUMN_TYPES.keySet();
    }

    public static String getColumnType(String column) {
        return COLUMN_TYPES.getOrDefault(column, "string"); // Default to string if not found
    }

    public static String resolveCanonicalKey(String rawFlag) {
        rawFlag = rawFlag.toLowerCase().trim();
        return FLAG_ALIASES.get(rawFlag.toLowerCase());
    }

    public static Map<String, List<String>> getAliasesPerField() {
        Map<String, List<String>> reverseMap = new HashMap<>();
        for (Map.Entry<String, String> entry : FLAG_ALIASES.entrySet()) {
            reverseMap.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }
        return reverseMap;
    }
}
