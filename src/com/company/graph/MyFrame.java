package com.company.graph;

import com.company.LexemsTable;
import com.company.extensions.Lexem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyFrame {

    public JRadioButton radiobuttons(String text, boolean selected){
        JRadioButton button = new JRadioButton(text, selected);
        return button;
    }

    public void show(ArrayList<Lexem> lexemsTable, ArrayList<Lexem> IDNTable, ArrayList<Lexem> CONTable){
        JFrame jfrm = new JFrame("JTableExample");

        JPanel input = new JPanel();
        JPanel panel1= new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        //Устанавливаем диспетчер компоновки
        jfrm.getContentPane().setLayout(new FlowLayout());
        //Устанавливаем размер окна
        jfrm.setSize(1000, 600);
        //Устанавливаем завершение программы при закрытии окна
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JTextArea in = new JTextArea("", 30, 10);
        // Шрифт и табуляция
        in.setFont(new Font("Dialog", Font.PLAIN, 14));
        in.setTabSize(10);
        in.setLineWrap(true);

        JButton insertButton = new JButton("Insert");
        input.add(in);
        input.add(insertButton);
        input.add(this.radiobuttons("From file", true));
        input.add(this.radiobuttons("From input area", false));
//        input.add(this.radiobuttons());
        insertButton.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent event) {
                  String text =  in.getText();
            }
        });















        //Создаем новую таблицу на основе двумерного массива данных и заголовков
        JTable jTab1 = new JTable(new ShowLexemsTable(lexemsTable));
        JTable jTab2 = new JTable(new ShowIDNTable(IDNTable));
        JTable jTab3 = new JTable(new ShowCONTable(CONTable));

        panel1.add(jTab1);
        panel1.add(new JScrollPane(jTab1));
        panel2.add(jTab2);
        panel2.add(new JScrollPane(jTab2));

        panel3.add(jTab3);
        panel3.add(new JScrollPane(jTab3));
        //Отображаем контейнер
        JTabbedPane tabby = new JTabbedPane();
        tabby.addTab("Input", input);
        tabby.addTab("Lexems", panel1);
        tabby.addTab("Identificators", panel2);
        tabby.addTab("Constants", panel3);

//
//        // додаємо вкладки у фрейм
        jfrm.add(tabby);
        jfrm.setVisible(true);

    }
}
