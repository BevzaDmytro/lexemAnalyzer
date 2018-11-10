package com.company;

import com.company.graph.MyFrame;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        Parser parser = new Parser();
        parser.parse();

        MyFrame frame = new MyFrame();
        frame.show();

  //TODO доробити таблиці, перевірка на тип
    }
}
