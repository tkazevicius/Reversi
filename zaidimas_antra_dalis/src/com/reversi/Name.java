package com.reversi;

public class Name {
    private static Name name = new Name();
    public String nameReversi;
    private Name() {
        nameReversi = "Reversi";
    }
    public static Name getInstance() {
        return name;
    }
}
