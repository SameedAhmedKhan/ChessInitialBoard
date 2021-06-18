package com.company;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

public class Main {

    public static int Empty = 0;
    public static int King = 1;
    public static int Queen = 2;
    public static int Bishop = 3;
    public static int Knight = 4;
    public static int Rook = 5;

    static public int[][] board = new int[8][8];


    public static void main(String[] args) throws IOException {
        // write your code here
        LinkedList<Piece> ps=new LinkedList<>();
        BufferedImage all= ImageIO.read(new File("D:\\chess.png"));
        Image []imgs=new Image[12];
        int ind=0;
        int []BackRow=new int[8];

        //Parse the images from the pic and store in the List
        for(int y=0;y<400;y+=200){
            for(int x=0;x<1200;x+=200){
                imgs[ind]=all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

       //Generate Indices of the chess according to mentioned rules
        BackRow=Random_Chess_Board(board, 8);

        //store the piece(Kind,queen etc) in form of linked list
        Chess_Placement_Block(ps,BackRow);

        //create black and white boxes in the frame
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true);
        JPanel pn = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boolean white = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (white) {
                            g.setColor(new Color(235, 235, 208));
                        } else {
                            g.setColor(new Color(119, 148, 85));

                        }
                        g.fillRect(x * 64, y * 64, 64, 64);
                        white = !white;
                    }
                    white = !white;
                }
                for(Piece p: ps){
                    int ind=0;
                    if(p.name.equalsIgnoreCase("king")){
                        ind=0;
                    }
                    if(p.name.equalsIgnoreCase("queen")){
                        ind=1;
                    }
                    if(p.name.equalsIgnoreCase("bishop")){
                        ind=2;
                    }
                    if(p.name.equalsIgnoreCase("knight")){
                        ind=3;
                    }
                    if(p.name.equalsIgnoreCase("rook")){
                        ind=4;
                    }
                    if(p.name.equalsIgnoreCase("pawn")){
                        ind=5;
                    }
                    if(!p.isWhite){
                        ind+=6;
                    }
                    g.drawImage(imgs[ind], p.xp*64, p.yp*64, this);
                }
            }
        };

        frame.add(pn);
        frame.setVisible(true);

    }

    //print the chess board
    static void Print_Board(int[][] board, int size) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j % 2 == 0) {
                    System.out.print("'" + board[i][j] + "'" + " | ");
                } else {
                    System.out.print(board[i][j] + " | ");
                }
            }
            System.out.println();
        }

    }

    //generate indices of the chess
    static int[] Random_Chess_Board(int[][] board, int size) {

        int[] BackRow = new int[8];

        List<Integer> EmptyOdd = new ArrayList<Integer>();
        List<Integer> EmptyEven = new ArrayList<Integer>();

        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                EmptyEven.add(i);
            } else {
                EmptyOdd.add(i);
            }
        }
        System.out.println("Even List");
        for(int i=0;i<EmptyEven.size();i++){
            System.out.println(EmptyEven.get(i));
        }
        System.out.println("Odd List");
        for(int i=0;i<EmptyOdd.size();i++){
            System.out.println(EmptyOdd.get(i));
        }


        Random rand = new Random();

        //Place King in array at index 1 and 6
        int min = 1, max = 6;
        int KingIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        BackRow[KingIndex] = King;
        System.out.println("King Index = " + KingIndex);

        //place rooks at random spaces higher and lower than kings place
        min = 0;
        max = KingIndex - 1;
        int rookOneIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        min = KingIndex + 1;
        max = 7;
        int rookTwoIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        BackRow[rookOneIndex] = Rook;
        BackRow[rookTwoIndex] = Rook;
        System.out.println("rookOneIndex = " + rookOneIndex);
        System.out.println("rookTwoIndex = " + rookTwoIndex);


        //Update the odd and even lists so no pieces will be placed on top of the already placed pieces
        EmptyEven.remove(new Integer(KingIndex));
        EmptyOdd.remove(new Integer(KingIndex));
        EmptyEven.remove(new Integer(rookOneIndex));
        EmptyOdd.remove(new Integer(rookOneIndex));
        EmptyEven.remove(new Integer(rookTwoIndex));
        EmptyOdd.remove(new Integer(rookTwoIndex));

        //place bishops,1 on odd index,1 on even
        min = 0;
        max = EmptyEven.size() - 1;
        int bishopOneIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        min = 0;
        max = EmptyOdd.size() - 1;
        int bishopTwoIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        BackRow[EmptyEven.get(bishopOneIndex)] = Bishop;
        BackRow[EmptyOdd.get(bishopTwoIndex)] = Bishop;

        System.out.println("biShopOneIndex = " + EmptyEven.get(bishopOneIndex));
        System.out.println("biShopTwoIndex = " + EmptyOdd.get(bishopTwoIndex));

        //Update the odd and even lists so no pieces will be placed on top of the already placed pieces
        EmptyEven.remove(new Integer(EmptyEven.get(bishopOneIndex)));
        EmptyOdd.remove(new Integer(EmptyOdd.get(bishopTwoIndex)));

        //Since the Bishops are placed, only 1 list of empty spaces is needed
        List<Integer> emptySpaces = new ArrayList<Integer>();
        emptySpaces.addAll(EmptyEven);
        emptySpaces.addAll(EmptyOdd);

        System.out.println("Even List");
        for(int i=0;i<EmptyEven.size();i++){
            System.out.println(EmptyEven.get(i));
        }
        System.out.println("Odd List");
        for(int i=0;i<EmptyOdd.size();i++){
            System.out.println(EmptyOdd.get(i));
        }
        System.out.println("Empty Spaces List");
        for(int i=0;i<emptySpaces.size();i++){
            System.out.println(emptySpaces.get(i));
        }

        //Place queen
        min = 0;
        max = emptySpaces.size()-1;
        int queenIndex = (int) Math.floor(Math.random() * (max - min + 1) + min);
        BackRow[emptySpaces.get(queenIndex)] = Queen;

        System.out.println("QueenIndex = " + emptySpaces.get(queenIndex));

        //Update the empty spaces list so no pieces will be placed on top of the already placed pieces
        emptySpaces.remove(new Integer(emptySpaces.get(queenIndex)));

        //Place knights
        int knightOneIndex = emptySpaces.get(0);
        int knightTwoIndex = emptySpaces.get(1);
        BackRow[knightOneIndex] = Knight;
        BackRow[knightTwoIndex] = Knight;

        System.out.println("KnightOneIndex = " + knightOneIndex);
        System.out.println("KnightTwoIndex = " + knightTwoIndex);

//        int j = 0;
//        int k = 7;
////        for (int i = 0; i < 8; i++) {
//            board[j][i] = BackRow[i];
//            board[k][i] = BackRow[i];
//        }

        //Return Back row contains all random indices of the chess


       return BackRow;
    }

    //place the pieces of chess in the linked list for later use
    static void Chess_Placement_Block(LinkedList<Piece> ps,int []BackRow){
        //Row 1
        for(int i=0;i<8;i++){
            System.out.println(BackRow[i]);
            if(BackRow[i]==1){
                Piece bking=new Piece(i, 0, false, "king", ps);
            }
            else if(BackRow[i]==2){
                Piece bqueen=new Piece(i, 0, false, "queen", ps);
            }
            else if(BackRow[i]==3){
                Piece bbishop=new Piece(i, 0, false, "bishop", ps);
            }
            else if(BackRow[i]==4){
                Piece bkinght=new Piece(i, 0, false, "knight", ps);
            }
            else if(BackRow[i]==5){
                Piece brook=new Piece(i, 0, false, "rook", ps);
            }

        }

        //Row 7
        for(int i=0;i<8;i++){
            System.out.println(BackRow[i]);
            if(BackRow[i]==1){
                Piece wking=new Piece(i, 7, false, "king", ps);
            }
            else if(BackRow[i]==2){
                Piece wqueen=new Piece(i, 7, false, "queen", ps);
            }
            else if(BackRow[i]==3){
                Piece wbishop=new Piece(i, 7, false, "bishop", ps);
            }
            else if(BackRow[i]==4){
                Piece wkinght=new Piece(i, 7, false, "knight", ps);
            }
            else if(BackRow[i]==5){
                Piece wrook=new Piece(i, 7, false, "rook", ps);
            }

        }


        Piece bpawn1=new Piece(1, 1, false, "pawn", ps);
        Piece bpawn2=new Piece(2, 1, false, "pawn", ps);
        Piece bpawn3=new Piece(3, 1, false, "pawn", ps);
        Piece bpawn4=new Piece(4, 1, false, "pawn", ps);
        Piece bpawn5=new Piece(5, 1, false, "pawn", ps);
        Piece bpawn6=new Piece(6, 1, false, "pawn", ps);
        Piece bpawn7=new Piece(7, 1, false, "pawn", ps);
        Piece bpawn8=new Piece(0, 1, false, "pawn", ps);

        Piece wpawn1=new Piece(1, 6, true, "pawn", ps);
        Piece wpawn2=new Piece(2, 6, true, "pawn", ps);
        Piece wpawn3=new Piece(3, 6, true, "pawn", ps);
        Piece wpawn4=new Piece(4, 6, true, "pawn", ps);
        Piece wpawn5=new Piece(5, 6, true, "pawn", ps);
        Piece wpawn6=new Piece(6, 6, true, "pawn", ps);
        Piece wpawn7=new Piece(7, 6, true, "pawn", ps);
        Piece wpawn8=new Piece(0, 6, true, "pawn", ps);

    }



}