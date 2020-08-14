package com.company;

public class UnicodeOperation implements OperationMethod {
    @Override
    public String perform(String text, Mode mode, int cypher) {
        StringBuilder sb = new StringBuilder();
        char[] sentenceInChars = text.toCharArray();
        for (char c : sentenceInChars) {
            int newChar = 0;
            if (mode == Mode.DECRYPT) {
                newChar = c - cypher % 26;
            } else if (mode == Mode.ENCRYPT) {
                newChar = c + cypher % 26;
            }
            sb.append((char)(newChar));
        }
        return sb.toString();
    }
}
