package com.texar.inventory.shell;


import com.texar.inventory.model.Command;
import com.texar.inventory.service.PCService;
import com.texar.inventory.service.PrinterService;
import com.texar.inventory.shell.commands.*;
import org.jline.reader.*;
import org.jline.terminal.*;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.*;

public class CommandProcessor {
//    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, List<Command>> commandGroups = new LinkedHashMap<>();

    static String asciiArt = """
            
            
             ________  ________  __    __   ______   _______ \s
            /        |/        |/  |  /  | /      \\ /       \\\s
            $$$$$$$$/ $$$$$$$$/ $$ |  $$ |/$$$$$$  |$$$$$$$  |
               $$ |   $$ |__    $$  \\/$$/ $$ |__$$ |$$ |__$$ |
               $$ |   $$    |    $$  $$<  $$    $$ |$$    $$<\s
               $$ |   $$$$$/      $$$$  \\ $$$$$$$$ |$$$$$$$  |
               $$ |   $$ |_____  $$ /$$  |$$ |  $$ |$$ |  $$ |
               $$ |   $$       |$$ |  $$ |$$ |  $$ |$$ |  $$ |
               $$/    $$$$$$$$/ $$/   $$/ $$/   $$/ $$/   $$/\s
            
         
            """;
    static int[] ansiColors = {
            AttributedStyle.BLACK,
            AttributedStyle.RED,
            AttributedStyle.GREEN,
            AttributedStyle.YELLOW,
            AttributedStyle.BLUE,
            AttributedStyle.MAGENTA,
            AttributedStyle.CYAN,
            AttributedStyle.WHITE
    };

    static int randomColor = ansiColors[new Random().nextInt(ansiColors.length)];

    static AttributedStyle style = AttributedStyle.DEFAULT.foreground(randomColor).bold();

    public static void run() {
        PCService pcService = new PCService();
        PrinterService printerService = new PrinterService();

//        commands.put("search pcs", new SearchPCs(pcService));
//        commands.put("search printers", new SearchPrinters(printerService));
//        commands.put("view pcs", new ViewPCs(pcService));
//        commands.put("add pc", new AddPC(pcService));
//        commands.put("edit pc", new UpdatePC(pcService));
//        commands.put("delete pc", new DeletePC());
//        commands.put("view printers", new ViewPrinters(printerService));
//        commands.put("add printer",new AddPrinter(printerService));
//        commands.put("edit printer", new UpdatePrinter(printerService));
//        commands.put("delete printer", new DeletePrinter());

        commandGroups.put("General", List.of(new Help(commandGroups), new Exit()));
        commandGroups.put("PC Commands", List.of(new ViewPCs(pcService), new AddPC(pcService), new UpdatePC(pcService),
                new DeletePC(), new SearchPCs(pcService)));
        commandGroups.put("Printer Commands", List.of(
                new ViewPrinters(printerService), new AddPrinter(printerService),
                new UpdatePrinter(printerService), new DeletePrinter(), new SearchPrinters(printerService)
        ));

        Command helpCommand = new Help(commandGroups);



        try {
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .highlighter(new Highlighter() {
                        @Override
                        public AttributedString highlight(LineReader lineReader, String s) {
                            return new AttributedString(s, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold());
                        }
                    })
                    .build();

            System.out.println(new AttributedString(asciiArt, style).toAnsi());
            System.out.println(new AttributedString(
                    "\nWelcome to TEXAR IT Inventory",
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold()
            ).toAnsi());

            System.out.println("Type 'help' to see available commands.\n");





            String prompt = new AttributedString("TEXARInvOS> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN).bold()).toAnsi();
            String line = "";
            while (true) {
                /*
                TODO: Add Welcome message

*/

                try {
                    line = reader.readLine(prompt).trim();
                    String[] tokens = line.split("\\s+");

                    if (tokens.length == 0) continue;

                    String commandKey = (tokens.length >= 2)
                            ? tokens[0].toLowerCase() + " " + tokens[1].toLowerCase()
                            : tokens[0].toLowerCase();

                    String[] args = (tokens.length > 2)
                            ? Arrays.copyOfRange(tokens, 2, tokens.length)
                            : new String[] {};

                    Command cmd = null;
                    for (List<Command> group : commandGroups.values()) {
                        for (Command c : group) {
                            if (c.getName().equalsIgnoreCase(commandKey)) {
                                cmd = c;
                                break;
                            }
                        }
                    }
                    if (cmd != null) {
                        cmd.execute(args);
                    } else {
                        System.out.println("Unknown command: " + commandKey);

                    }

                } catch (UserInterruptException e) {
                    System.out.println("\nExiting...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
