package com.company.graph;

import com.company.Controller;
import com.company.Parser;
import com.company.extensions.Lexem;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


public class MyFrame {

    private boolean isFile;
    private String path;
    private String text;
    private JFrame frame;
    private JTabbedPane panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;


    public MyFrame() {
        this.frame =  new JFrame("JTableExample");
        this.frame.getContentPane().setLayout(new FlowLayout());
        this.frame.setSize(800, 500);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.panel = new JTabbedPane();
        this.panel1= new JPanel();
        this.panel2 = new JPanel();
        this.panel3 = new JPanel();

    }

    private void analyze(boolean isFile, String text) throws Exception {
        Controller controller = new Controller();
        controller.run(isFile, text);
        MyFrame.this.show(controller.getParser().getLexemsTable().getLexems(), controller.getParser().getIdentificatorsTable().getLexems(), controller.getParser().getConstantsTable().getLexems());
    }

    public void inputData(){
        JPanel input = new JPanel();
        input.setLayout(new FlowLayout());
        ButtonGroup group = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("File", false);
        JRadioButton button2 = new JRadioButton("Input", false);
        button1.setBounds(5,5,10,10);
        button2.setBounds(5,20,10,10);
        group.add(button1);
        group.add(button2);

        JButton browse = new JButton("Browse!");
        browse.setEnabled(false);

        JTextArea in = new JTextArea("", 10, 40);
        in.setFont(new Font("Dialog", Font.PLAIN, 14));
        in.setTabSize(10);
        in.setLineWrap(true);
        in.setEnabled(false);
        JButton insertButton = new JButton("Parse it!");
        insertButton.setEnabled(false);


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch ( ((JRadioButton)ae.getSource()).getText() ) {
                    case "File" :
                        insertButton.setEnabled(false);
                        in.setEnabled(false);
                        browse.setEnabled(true);

                        browse.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                File selectedFile = new File("programm.txt");
                                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getParentDirectory(new File("programm.txt")));
                                int returnValue = fileChooser.showOpenDialog(null);
                                if (returnValue == JFileChooser.APPROVE_OPTION) {
                                    selectedFile = fileChooser.getSelectedFile();
                                }

                                try {
                                    MyFrame.this.analyze(true, selectedFile.getPath());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        break;
                    case "Input" :
                        insertButton.setEnabled(true);
                        in.setEnabled(true);
                        browse.setEnabled(false);

                        insertButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                String text = in.getText();
                                try {
                                    MyFrame.this.analyze(false, text);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                MyFrame.this.show(parser.getLexemsTable().getLexems(), parser.getIdentificatorsTable().getLexems(), parser.getConstantsTable().getLexems());
//                                MyFrame.this.show(lexemsTable, IDNTable, CONTable);
                            }
                        });
                        break;

                    default:
                        break;
                }
                panel.repaint();
            }
        };
        button1.addActionListener(listener);
        button2.addActionListener(listener);

        input.add(in);
        input.add(insertButton);
        input.add(browse);
        input.add(button1);
        input.add(button2);
//        String text = this.getSelectedButtonText(group);

//        if(text.equals("File")){
//            browse.setEnabled(true);
//            browse.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent event) {
//                    File selectedFile = new File("programm.txt");
//                    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//                    int returnValue = fileChooser.showOpenDialog(null);
//                    if (returnValue == JFileChooser.APPROVE_OPTION) {
//                         selectedFile = fileChooser.getSelectedFile();
//                    }
//                    MyFrame.this.path = selectedFile.getAbsolutePath();
//                    MyFrame.this.show(lexemsTable, IDNTable, CONTable);
//                }
//            });
//        }
//        else {
//            insertButton.setEnabled(true);
//            in.setEnabled(true);
//            insertButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent event) {
//                    MyFrame.this.text = in.getText();
//                    MyFrame.this.show(lexemsTable, IDNTable, CONTable);
//                }
//            });
//        }

        this.panel.addTab("Input", input);
        this.frame.add(this.panel);
        this.frame.setVisible(true);
    }

    public void show(ArrayList<Lexem> lexemsTable, ArrayList<Lexem> IDNTable, ArrayList<Lexem> CONTable){

//        this.panel.remove(panel1);
//        this.panel.remove(panel2);
//        this.panel.remove(panel3);
//        this.panel.removeAll();

//
//         JPanel panel1 = new JPanel();
//         JPanel panel2 = new JPanel();
//         JPanel panel3 = new JPanel();
//        this.panel.removeAll();
        //Создаем новую таблицу на основе двумерного массива данных и заголовков
        JTable jTab1 = new JTable(new ShowLexemsTable(lexemsTable));
        JTable jTab2 = new JTable(new ShowIDNTable(IDNTable));
        JTable jTab3 = new JTable(new ShowCONTable(CONTable));

        panel1.removeAll();
        panel2.removeAll();
        panel3.removeAll();

        panel1.add(jTab1);
        panel1.add(new JScrollPane(jTab1));
        panel2.add(jTab2);
        panel2.add(new JScrollPane(jTab2));
        panel3.add(jTab3);
        panel3.add(new JScrollPane(jTab3));


        this.panel.addTab("Lexems", this.panel1);
        this.panel.addTab("Identificators", this.panel2);
        this.panel.addTab("Constants", this.panel3);
        this.frame.add(this.panel);
        this.frame.setVisible(true);

    }
}
