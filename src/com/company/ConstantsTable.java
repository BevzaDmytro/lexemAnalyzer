package com.company;

import com.company.extensions.Lexem;

import java.util.ArrayList;

public class ConstantsTable {
    private ArrayList<Lexem> lexems;


    public ConstantsTable() {
        this.lexems = new ArrayList<>();
    }

    public void addConstant(Lexem lexem){
        this.lexems.add(lexem);
    }

    public int getNumberOfLexem(Lexem lexem){
        int i = 0;
        for (Lexem lex : this.lexems ) {
            if(lex.getName().equals(lexem.getName())) return i+1;
            i++;
        }
        return i;
    }
    public void constantType(Lexem lexem){
        if(lexem.getName().contains(".")) lexem.setIdType("float");
        else lexem.setIdType("int");
    }

    public void showTable(){
        int i=1;
        System.out.format("\u2551%1s\u2551%7s\u2551%4s\u2551\n",'№',"Name","Type");
        for(int j=0;j<32;j++) System.out.format("\u2550");
        System.out.format("\n");
        for ( Lexem lex : this.lexems ) {

            System.out.format("\u2551%2s\u2551%7s\u2551%4s\u2551\n",i,lex.getName(),lex.getIdType());
            for(int j=0;j<32;j++) System.out.format("\u2550");
            System.out.format("\n");
//            System.out.format("\u2551%25s\u2551\n","-------------------------");
            i++;
        }
    }
}
