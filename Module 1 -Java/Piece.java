package com.company;

import java.util.LinkedList;

public class Piece {
    int xp;
    int yp;
    boolean isWhite;
    LinkedList<Piece> ps;
    String name;

    public Piece(int xp, int yp, boolean isWhite,String n, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        this.isWhite = isWhite;
        this.ps=ps;
        name=n;
        ps.add(this);
    }


}
