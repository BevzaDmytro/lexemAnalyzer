package com.company;

import com.company.analyzers.Parser;
import com.company.analyzers.SyntaxAnalyzer;
import com.company.analyzers.SyntaxAnalyzer2;
import com.company.extensions.Configuration;
import com.company.views.ConfigurationView;

public class Controller {
    private Parser parser;
    private SyntaxAnalyzer analyzer;
    SyntaxAnalyzer2 analyzer2;

    public Controller() {
        this.parser = new Parser();
//        this.analyzer = analyzer;
    }

    public Parser getParser() {
        return parser;
    }

    public ConfigurationView getCongigurations(){
        return this.analyzer2.getConfigurationView();
    }

    public SyntaxAnalyzer2 getAnalyzer2() {
        return analyzer2;
    }

    public void run(boolean isFile, String text) throws Exception {
        this.parser.parse(isFile, text);

//        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(parser.getLexemsTable());
//        analyzer.prog();

        analyzer2 = new SyntaxAnalyzer2(parser.getLexemsTable());
        analyzer2.analyze();

    }

}
