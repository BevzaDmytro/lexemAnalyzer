package com.company;

import com.company.extensions.InputLexemTable;
import com.company.extensions.Lexem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class LexemsTable extends AbstractTableModel {
    private ArrayList<Lexem> lexems;

    public int getRowCount() {
        return 10;
    }
    @Override
    public int getColumnCount() {
        return 10;
    }
    @Override
    public Object getValueAt(int r, int c) {
        return r * c;
    }


    public LexemsTable() {
        this.lexems = new ArrayList<>();
    }

    public void addLexem(Lexem lexem) throws Exception {
//        InputLexemTable inputLexemTable = new InputLexemTable();
//        if(inputLexemTable.isContain(lexem)) {
//            lexem.setCode(inputLexemTable.getCode(lexem));
            this.lexems.add(lexem);
//        }
//        else throw new Exception("Identificator is not defined in table");

    }

    public String lastLexemType(){
        return this.lexems.get(this.lexems.size() - 1).getType();
    }

    public boolean isContain(Lexem lexem){
        for ( Lexem currentLex : this.lexems ) {
            if(currentLex.getName().equals(lexem.getName())) return  true;
        }
        return false;
    }

    public String getFirstLineLexem(int line){
        for ( Lexem currentLex : this.lexems  ) {
            if (currentLex.getLine() == line) return currentLex.getName();
        }
        return "";
    }

    public boolean isDeclaration(Lexem lexem){
        int line = lexem.getLine();
//        for ( Lexem currentLex : this.lexems ) {
//            if(currentLex.getName().equals(lexem.getName())) {
//                line = currentLex.getLine();break;
//            }
//        }

        if(this.getFirstLineLexem(line).equals("int") || this.getFirstLineLexem(line).equals("float")) return true;

        return false;
    }

    public void showTable(){
        int i=1;
        System.out.format("\u2551%1s\u2551%7s\u2551%4s\u2551%4s\u2551%4s\u2551%4s\u2551\n",'№',"Name","Line","Code","IDN","CON");
        for(int j=0;j<32;j++) System.out.format("\u2550");
        System.out.format("\n");
        for ( Lexem lex : this.lexems ) {

            System.out.format("\u2551%2s\u2551%7s\u2551%4s\u2551%4s\u2551%4s\u2551%4s\u2551\n",i,lex.getName(),lex.getLine(),lex.getCode(),lex.getIDNCode(),lex.getCONCode());
            for(int j=0;j<32;j++) System.out.format("\u2550");
            System.out.format("\n");
//            System.out.format("\u2551%25s\u2551\n","-------------------------");
            i++;
        }
    }
}
