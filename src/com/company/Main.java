package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        OutputProcessor os = new OutputProcessor();
        processCommandLineArguments(args, os);
        System.out.println(os.buildOutputString());

    }

    private static void processCommandLineArguments(String[] args, OutputProcessor os) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                os.setMode(args[i+1]);
                continue;
            }
            if (args[i].equals("-key")) {
                os.setKey(args[i+1]);
                continue;
            }
            if (args[i].equals("-in")) {
                if (os.getText().isEmpty()) {
                    os.setText(readDataFromFile(args, i));
                }
                continue;
            }
            if (args[i].equals("-data")) {
                os.setText(readDataFromConsole(args, i));
                continue;
            }
            if (args[i].equals("-alg")) {
                os.setAlgorithm(args[i+1]);
                continue;
            }
            if (args[i].equals("-out")) {
                try {
                    PrintStream fileOut = new PrintStream(args[i+1]);
                    System.setOut(fileOut);
                } catch (FileNotFoundException e) {
                    System.out.print("Error");
                }
            }
        }
    }

    private static String readDataFromConsole(String[] args, int i) {
        try {
            return args[i+1];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            System.out.print("Error");
            return "";
        }
    }

    private static String readDataFromFile(String[] args, int i) {
        try {
            return new String(Files.readAllBytes(Paths.get(args[i+1])));
        } catch (ArrayIndexOutOfBoundsException | IOException ignore) {
            System.out.print("Error");
            return "";
        }
    }
}
