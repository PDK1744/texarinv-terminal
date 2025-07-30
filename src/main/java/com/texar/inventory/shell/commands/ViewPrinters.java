package com.texar.inventory.shell.commands;

import com.texar.inventory.model.Command;
import com.texar.inventory.model.Printer;
import com.texar.inventory.service.PrinterService;

import java.sql.SQLException;
import java.util.List;

public class ViewPrinters implements Command {
    private final PrinterService printerService;

    public ViewPrinters(PrinterService printerService) {
        this.printerService = printerService;
    }

    @Override
    public String getName() {
        return "view printers";
    }

    @Override
    public void execute(String[] args){
        try {
            List<Printer> printers = printerService.getAllPrinters();
            printers.forEach(System.out::println);;
        } catch (SQLException e) {
            System.err.println("Error returning Printers: " + e.getMessage());
        }
    }

    @Override
    public String getShortDescription() {
        return "Displays all Printers";
    }
}
