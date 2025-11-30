package com.dth;

import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;

import java.io.IOException;

public class CliMinimal {

    private static final String NAME_OPTION = "name";

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        // Setting the options
        Options options = new Options();
        Option option = Option.builder("n").required()
                .longOpt(NAME_OPTION)
                .desc("Your name is required")
                .type(String.class)
                .hasArg()
                .get();

        options.addOption(option);

        // Main logic
        try {
            CommandLine commandLine = parser.parse(options, args);
            String name = commandLine.getParsedOptionValue(NAME_OPTION);
            System.out.println("Hello " + name);
        } catch (ParseException ex) {
            printHelp(ex, options);
        }
    }

    private static void printHelp(ParseException ex, Options options) {
        System.out.println(ex.getMessage());
        try {
            HelpFormatter formatter = HelpFormatter.builder().setShowSince(false).get();
            formatter.printOptions(options);
        } catch (IOException e) {
            throw new RuntimeException("Can't create help message", ex);
        }
    }
}
