package com.company;

public class ShiftOperation implements OperationMethod {
    @Override
    public String perform(String text, Mode mode, int cypher) {
        StringBuilder sb = new StringBuilder();
        char[] sentenceInChars = text.toCharArray();
        int shift = cypher % 26;
        for (char c : sentenceInChars) {
            int encodedCharNumber = c;
            if (Character.isUpperCase(c)) {
                encodedCharNumber = getUpdatedCharNumber(mode, shift, c, 90, 65);
            } else if (Character.isLowerCase(c)) {
                encodedCharNumber = getUpdatedCharNumber(mode, shift, c, 122, 97);
            }
            sb.append((char)(encodedCharNumber));
        }
        return sb.toString();
    }

    private int getUpdatedCharNumber(Mode mode, int shift, char c, int i, int i2) {
        int encodedCharNumber = c;
        if (mode == Mode.DECRYPT) {
            encodedCharNumber = i - (Math.abs(c - i - shift)) % 26;
        } else if (mode == Mode.ENCRYPT) { //ENCODE
            encodedCharNumber = (c - i2 + shift) % 26 + i2;
        }
        return encodedCharNumber;
    }
}
