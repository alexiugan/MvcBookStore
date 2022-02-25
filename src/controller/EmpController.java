package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.Book;
import model.BookDeposit;
import view.EmpGUI;

public class EmpController {
    private EmpGUI view;
    private BookDeposit model;


    public EmpController(EmpGUI view, BookDeposit model) {
        this.view = view;
        this.model = model;


        this.view.addSellListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book b = view.getSoldBook();
                int sold = view.getNrSold();

                if(b.getStock()>=sold)
                {
                    model.updateBook(b.getId(), sold);
                    view.sellTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(view, "Not enough in stock", "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}