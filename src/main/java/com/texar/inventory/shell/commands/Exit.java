package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;

public class Exit implements Command {

    @Override
    public void execute(String[] args) {
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getShortDescription() {
        return "Exit the program";
    }
}
