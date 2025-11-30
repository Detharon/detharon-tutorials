package com.dth;

import org.apache.commons.cli.*;

import java.util.Random;

public class CliRandom {

    private static final String MIN = "min";
    private static final String MAX = "max";
    private static final String RANDOM_CLASS = "c";
    private static final String SEED = "s";
    private static final String RESULTS = "n";

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = prepareOptions();

        try {
            CommandLine commandLine = parser.parse(prepareOptions(), args);

            // Getting required arguments
            int min = ((Number) commandLine.getParsedOptionValue(MIN)).intValue();
            int max = ((Number) commandLine.getParsedOptionValue(MAX)).intValue();

            // Getting optional arguments
            Random random = ((Random) commandLine.getParsedOptionValue(RANDOM_CLASS));
            if (random == null) {
                random = new Random();
            }

            if (commandLine.hasOption(SEED)) {
                long seed = ((Number) commandLine.getParsedOptionValue(SEED)).longValue();
                random.setSeed(seed);
            }

            long numbers = 1;
            if (commandLine.hasOption(RESULTS)) {
                numbers = ((Number) commandLine.getParsedOptionValue(RESULTS)).longValue();
            }

            for (int i = 0; i < numbers; i++) {
                System.out.print(Math.round(random.nextInt(max)) + min);
                if (i != numbers - 1) {
                    System.out.println();
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            new HelpFormatter().printHelp("cli-random", options);
        }
    }

    private static Options prepareOptions() {
        Options options = new Options();

        options.addOption(getMinOption())
                .addOption(getMaxOption())
                .addOption(getRandomClassOption())
                .addOption(getSeedOption())
                .addOption(getNumberOfResultsOption());

        return options;
    }

    private static Option getMinOption() {
        return Option.builder().required().desc("minimum number (inclusive)")
                .longOpt(MIN)
                .type(Number.class)
                .hasArg()
                .get();
    }

    private static Option getMaxOption() {
        return Option.builder().required().desc("maximum number (inclusive)")
                .longOpt(MAX)
                .type(Number.class)
                .hasArg()
                .get();
    }

    private static Option getRandomClassOption() {
        return Option.builder(RANDOM_CLASS).desc("class extending Random, that will provide" +
                " random numbers, for example: java.security.SecureRandom")
                .longOpt("class")
                .type(Object.class)
                .hasArg()
                .get();
    }

    private static Option getNumberOfResultsOption() {
        return Option.builder(RESULTS).desc("number of results, 1 by default")
                .longOpt("numbers")
                .type(Number.class)
                .hasArg()
                .get();
    }

    private static Option getSeedOption() {
        return Option.builder(SEED).desc("seed, by default determined by" +
                " Random implementation")
                .longOpt("seed")
                .type(Number.class)
                .hasArg()
                .get();
    }
}