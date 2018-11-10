package com.company.graph;

import javax.swing.*;

public class MyFrame {

    public void show(){
//        JFrame frame = new JFrame("My First GUI");
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        frame.setSize(300,300);
//
//        JButton button = new JButton("Press");
//
//        frame.getContentPane().add(button); // Adds Button to content pane of frame
//
//        frame.setVisible(true);


        //створюємо вкладку
        JFrame frame = new JFrame();
        frame.setTitle ("Test Tabbed Pane");
        frame.setSize(100, 300);
        JTabbedPane tabby = new JTabbedPane();

        //створюємо панелі для вкладок
        JPanel panel1= new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3= new JPanel();
//        JPanel panel2 = new JPanel();

        //додаємо на першу вкладку групу радіокнопок
        ButtonGroup radioGroup=new ButtonGroup();
        panel1.add(new JLabel("Ваш вік?"));
        JRadioButton radioButton;
        panel1.add(radioButton=new JRadioButton("12..18"));
        radioGroup.add(radioButton);
        panel1.add(radioButton=new JRadioButton("19..30"));
        radioGroup.add(radioButton);
        panel1.add(radioButton=new JRadioButton("30..60"));
        radioGroup.add(radioButton);

        //на другу панель додаємо просто напис
        panel2.add(new JLabel("Друга вкладка"));

        //додаємо панелі у JTabbedPane
        tabby.addTab("Перша", panel1);
        tabby.addTab("Друга", panel2);

        // додаємо вкладки у фрейм
        frame.add(tabby);
        frame.setVisible(true);
    }
}
