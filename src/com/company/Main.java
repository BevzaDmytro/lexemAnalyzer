package com.company;


import com.company.graph.MyFrame;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
//        Parser parser = new Parser();
//        parser.parse(true, "programm.txt");

        MyFrame frame = new MyFrame();
//        frame.show(parser.getLexemsTable().getLexems(),parser.getIdentificatorsTable().getLexems(), parser.getConstantsTable().getLexems());
        frame.inputData();

//        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(parser.getLexemsTable());
//        analyzer.prog();
    }
}
