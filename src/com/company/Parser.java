package com.company;

import com.company.extensions.InputLexemTable;
import com.company.extensions.Lexem;
import com.company.extensions.Checker;
import com.company.graph.MyFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
    private int state;
    private Checker checker;
    private String lexem;
    private boolean hasToRead;
    private boolean EOF;

    public Parser() {
        this.state = 1;
        this.checker = new Checker();
        this.hasToRead = true;
        this.EOF = false;
        this.lexem = "";
    }

//    private int readChar(){
////        BufferedReader reader = null;
//        int i = -1;
//        try {
//           i =  this.reader.read();
//        } catch (IOException e) {
//            e.getCause();
//        }
//
//        return i;
//    }

    public void parse() throws Exception {
        BufferedReader reader = null;
        LexemsTable lexemsTable = new LexemsTable();
        IdentificatorsTable identificatorsTable = new IdentificatorsTable();
        ConstantsTable constantsTable = new ConstantsTable();
        InputLexemTable inputLexemTable = new InputLexemTable();

        try {
            reader = new BufferedReader(new FileReader(new File("programm.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("NICE");
        char symbol = ' ';
        this.state = 1;
        int line = 1;
//            (c = reader.read()) != -1
        while (!this.EOF) {
            if(this.hasToRead) {
//                if(this.readChar() ==  -1) {this.EOF = false; break;}
//                 symbol = (char) this.readChar();
                int c = 0;
                try {
                    c = reader.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(c == -1) {this.EOF = false;break;}
                 symbol = (char) c;
            }

            switch(this.state){
                case 1:
                    if(!this.hasToRead){
                        this.lexem = "";
                        this.hasToRead = true;
                    }
                    if(checker.isWhiteDelimiter(symbol)){
                        continue;
                    }

                    if(checker.isLetter(symbol)) {
                        this.lexem += Character.toString(symbol);
                        this.state = 2;
                        continue;
                    }
                    else if(checker.isNumber(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 3;
                        continue;
                    }
                    else if(checker.isDot(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 6;
                    }
                    else if(checker.isOneCharDelimiter(symbol)){
                        if(checker.isPlusOrMinus(symbol) && !(lexemsTable.lastLexemType().equals("IDN") || lexemsTable.lastLexemType().equals("CON"))){
                           this.lexem += Character.toString(symbol);
                           this.state = 4;
                           continue;
                        }
                        if(checker.isNewLine(symbol)){
                            if(symbol == '\n') {
                                this.lexem += Character.toString('¶');
                                Lexem newLex = new Lexem(this.lexem, "OneCharDelimiter", line);
                                newLex.setCode(inputLexemTable.getCode(newLex));
                                lexemsTable.addLexem(newLex);
                                line++;
                                this.lexem = "";
                            }
                            continue;
                        }
                        this.lexem += Character.toString(symbol);
                        Lexem newLex = new Lexem(this.lexem, "OneCharDelimiter", line);
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                            this.lexem = "";
                        }
                        else throw new Exception("Not defined in lexems table");
                    }
                    else if(checker.isLessMark(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 7;
                        continue;
                    }
                    else if(checker.isGreatMark(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 8;
                        continue;
                    }
                    else if(checker.isEqualMark(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 9;
                        continue;
                    }
                    else if(checker.isNoMark(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 10;
                        continue;
                    }
                    else {
                        this.EOF = true;
                        throw new Exception("Undefined symbol " + symbol + " Line: " + line);
                    }
                    break;

                case 2:
                    if(checker.isNumber(symbol) || checker.isLetter(symbol)){
                        this.lexem += Character.toString(symbol);
//                        if(checker.isKeyword(this.lexem)){
                        Lexem newLex = new Lexem(this.lexem, "keyword", line);
                        if(inputLexemTable.isContain(newLex) ){
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
//                            System.out.println("Lexem added: " + this.lexem);
                            this.lexem = "";
                            this.state = 1;
                        }
                    }
                    else {
                        Lexem newLex = new Lexem(this.lexem, "IDN", line);
                        if( lexemsTable.isDeclaration( newLex ) ) {
                            String type = lexemsTable.getFirstLineLexem(line);
                            newLex.setIdType(type);
                            // перевірка на те чи не визначається повторно
                            if (!identificatorsTable.isDefined(newLex)) {
                                identificatorsTable.addIdentificator(newLex);
                            }
                            else {
                                throw new Exception("Variable " + this.lexem + "is already defined(line " + line + " )");
                            }
                        }
                        else{
                            if (!identificatorsTable.isDefined(newLex)) {
                                throw new Exception("Variable " + this.lexem + "is not defined");
                            }
                        }
                        newLex.setCode(100);
                        newLex.setIDNCode(identificatorsTable.getNumberOfLexem(newLex));
                        lexemsTable.addLexem(newLex);
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 3:
                    if(checker.isNumber(symbol)){
                        this.lexem += Character.toString(symbol);
                    }
                    else if(checker.isDot(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 5;
                    }
                    else{
                        Lexem newLex = new Lexem(this.lexem, "CON", line);
                        constantsTable.constantType(newLex);
                        constantsTable.addConstant(newLex);
                        newLex.setCONCode(constantsTable.getNumberOfLexem(newLex));
                        newLex.setCode(101);
                        lexemsTable.addLexem(newLex);
//                        System.out.println("Lexem added: " + this.lexem);
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 4:
                    if(checker.isNumber(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 3;
                    }
                    else if(checker.isDot(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state = 6;
                    }
                    else{
                        throw new Exception("Unexpected symbol after " + this.lexem + " on line " + line);
                    }
                    break;

                case 5:
                    if(checker.isNumber(symbol)){
                        this.lexem += Character.toString(symbol);
                    }
                    else{
                        Lexem newLex = new Lexem(this.lexem, "CON", line);
                        constantsTable.constantType(newLex);
                        constantsTable.addConstant(newLex);
                        newLex.setCONCode(constantsTable.getNumberOfLexem(newLex));
                        newLex.setCode(101);
                        lexemsTable.addLexem(newLex);
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 6:
                    if(checker.isNumber(symbol)){
                        this.lexem += Character.toString(symbol);
                        this.state =5;
                    }
                    else{
                        Lexem newLex = new Lexem(this.lexem, "CON", line);
                        constantsTable.constantType(newLex);
                        constantsTable.addConstant(newLex);
                        newLex.setCONCode(constantsTable.getNumberOfLexem(newLex));
                        newLex.setCode(101);
                        lexemsTable.addLexem(newLex);
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 7:
                    Lexem newLex = new Lexem(this.lexem, "Delimiter", line);
                    if(checker.isLessMark(symbol) || checker.isEqualMark(symbol)){
                        this.lexem += Character.toString(symbol);
                        newLex = new Lexem(this.lexem, "Delimiter", line);

                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");
//                        System.out.println("Lexem added: " + this.lexem);

                        this.lexem = "";
                        this.state = 1;
                    }
                    else{
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 8:
                     newLex = new Lexem(this.lexem, "Delimiter", line);
                    if(checker.isGreatMark(symbol) || checker.isEqualMark(symbol)){
                        this.lexem += Character.toString(symbol);


                        newLex = new Lexem(this.lexem, "Delimiter", line);
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");

                        this.lexem = "";
                        this.state = 1;
                    }
                    else{
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");
                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 9:
                    newLex = new Lexem(this.lexem, "Delimiter", line);
                    if(checker.isEqualMark(symbol)){
                        this.lexem += Character.toString(symbol);

                        newLex = new Lexem(this.lexem, "Delimiter", line);
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");


                        this.lexem = "";
                        this.state = 1;
                    }
                    else{

                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table");

                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                case 10:
                    newLex = new Lexem(this.lexem, "Delimiter", line);
                    if(checker.isEqualMark(symbol)){
                        this.lexem += Character.toString(symbol);

                        newLex = new Lexem(this.lexem, "Delimiter", line);
                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Not declarated in lexems table: " + this.lexem);


                        this.lexem = "";
                        this.state = 1;
                    }
                    else{

                        if(inputLexemTable.isContain(newLex)) {
                            newLex.setCode(inputLexemTable.getCode(newLex));
                            lexemsTable.addLexem(newLex);
                        }
                        else throw new Exception("Excepted '=' on line "+ line);

                        this.hasToRead = false;
                        this.state = 1;
                    }
                    break;

                  default:
                      break;
            }

        }

        lexemsTable.showTable();
        System.out.println("IDN");
//        identificatorsTable.showTable();
        System.out.println("CON");
//        constantsTable.showTable();

        MyFrame frame = new MyFrame();
        frame.show(lexemsTable.getLexems(),identificatorsTable.getLexems(), constantsTable.getLexems());

        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(lexemsTable);
        analyzer.prog();
    }
}
