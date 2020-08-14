package com.company;

enum Mode {
    ENCRYPT,
    DECRYPT
}

enum Algorithm {
    SHIFT,
    UNICODE
}

public class OutputProcessor {

    private Mode mode;
    private int key = 0;
    private String text = "";
    private Algorithm algorithm = Algorithm.SHIFT;

    public void setMode(String arg) {
        if (arg.equalsIgnoreCase("enc")) {
            this.mode = Mode.ENCRYPT;
        } else if (arg.equalsIgnoreCase("dec")) {
            this.mode = Mode.DECRYPT;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setKey(String arg) {
        this.key = Integer.parseInt(arg);
    }

    public void setText(String arg) {
        this.text = arg;
    }

    public String getText() {
        return text;
    }

    public void setAlgorithm(String arg) {
        if (arg.equalsIgnoreCase("shift")) {
            this.algorithm = Algorithm.SHIFT;
        } else if (arg.equalsIgnoreCase("unicode")) {
            this.algorithm = Algorithm.UNICODE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String buildOutputString() {
        OperationStrategy operationStrategy = new OperationStrategy();
        if (algorithm == Algorithm.SHIFT) {
            operationStrategy.setOperationMethod(new ShiftOperation());
        } else if (algorithm == Algorithm.UNICODE) {
            operationStrategy.setOperationMethod(new UnicodeOperation());
        } else {
            return null;
        }
        return operationStrategy.execute(text, mode, key);
    }
}
