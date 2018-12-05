package com.company.extensions;

import com.company.extensions.Lexem;

import java.util.ArrayList;

public class IdentificatorsTable {
    private ArrayList<Lexem> lexems;


    public ArrayList<Lexem> getLexems() {
        return lexems;
    }

    public IdentificatorsTable() {
        this.lexems = new ArrayList<>(1);
    }

    public void addIdentificator(Lexem lexem){
        this.lexems.add(lexem);
    }

    public boolean isDefined(Lexem lexem){
        for ( Lexem currentLex : this.lexems ) {
            if(currentLex.getName().equals(lexem.getName())) return  true;
        }
        return false;
    }

    public int getNumberOfLexem(Lexem lexem){
        int i = 0;
        for (Lexem lex : this.lexems ) {
            if(lex.getName().equals(lexem.getName())) return i+1;
            i++;
        }
        return i;
    }

//    public void showTable(){
//        int i=1;
//        System.out.format("\u2551%1s\u2551%7s\u2551%4s\u2551\n",'№',"Name","Type");
//        for(int j=0;j<32;j++) System.out.format("\u2550");
//        System.out.format("\n");
//        for ( Lexem lex : this.lexems ) {
//
//            System.out.format("\u2551%2s\u2551%7s\u2551%4s\u2551\n",i,lex.getName(),lex.getIdType());
//            for(int j=0;j<32;j++) System.out.format("\u2550");
//            System.out.format("\n");
////            System.out.format("\u2551%25s\u2551\n","-------------------------");
//            i++;
//        }
//    }

}
