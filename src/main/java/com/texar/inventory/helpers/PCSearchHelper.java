package com.texar.inventory.helpers;

import java.util.*;

public class PCSearchHelper {
    private static final Map<String, String> COLUMN_TYPES = new HashMap<>();

    public static final Map<String, String> FLAG_ALIASES = Map.ofEntries(
            Map.entry("pc_number", "pc_number"),
            Map.entry("pcname", "pc_number"),
            Map.entry("pcnumber", "pc_number"),
            Map.entry("pc", "pc_number"),
            Map.entry("ease_number", "ease_number"),
            Map.entry("easenumber", "ease_number"),
            Map.entry("asset_tag", "asset_tag"),
            Map.entry("assettag", "asset_tag"),
            Map.entry("asset", "asset_tag"),
            Map.entry("notes", "notes"),
            Map.entry("service_tag", "service_tag"),
            Map.entry("servicetag", "service_tag"),
            Map.entry("model", "model"),
            Map.entry("location", "location"),
            Map.entry("id", "id"),
            Map.entry("branch", "branch"),
            Map.entry("status", "status")
    );

    static {
        COLUMN_TYPES.put("id", "int");
        COLUMN_TYPES.put("pc_number", "string");
        COLUMN_TYPES.put("ease_number", "int");
        COLUMN_TYPES.put("asset_tag", "int");
        COLUMN_TYPES.put("model", "string");
        COLUMN_TYPES.put("service_tag", "string");
        COLUMN_TYPES.put("location", "string");
        COLUMN_TYPES.put("branch", "string");
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
