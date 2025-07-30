package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.SearchCommand;

import java.util.List;
import java.util.Map;

public class Help implements Command {
    private final Map<String, List<Command>> commandGroups;

    public Help(Map<String, List<Command>> commandGroups) {
        this.commandGroups = commandGroups;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available Commands:\n");
        sb.append("--------------------------------------------------\n");

        for (Map.Entry<String, List<Command>> entry : commandGroups.entrySet()) {
            sb.append(entry.getKey()).append(":\n");

            for (Command command : entry.getValue()) {
                String name = String.format("  - %-18s", command.getName());
                sb.append(name).append(command.getShortDescription()).append("\n");

                if (command instanceof SearchCommand) {
                    sb.append(((SearchCommand) command).getFormattedHelpBlock());
                }
            }

            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    @Override
    public String getShortDescription() {
        return "Shows this help message";
    }

}
