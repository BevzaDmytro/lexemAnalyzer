package com.company;

public class Controller {
    private Parser parser;
    private SyntaxAnalyzer analyzer;

    public Controller() {
        this.parser = new Parser();
//        this.analyzer = analyzer;
    }

    public Parser getParser() {
        return parser;
    }

    public void run(boolean isFile, String text) throws Exception {
        this.parser.parse(isFile, text);

        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(parser.getLexemsTable());
        analyzer.prog();
    }

}
