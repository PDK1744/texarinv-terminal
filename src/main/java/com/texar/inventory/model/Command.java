package com.texar.inventory.model;

public interface Command {
    void execute(String[] args);
//    String getDescription();
    String getShortDescription();
    String getName();
}
