package com.texar.inventory.service.commands;

import com.texar.inventory.model.Command;

import java.util.Map;

public class Help implements Command {
    private final Map<String, Command> commands;

    public Help(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Avaliable Commands:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue().getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Shows this help message";
    }

}
