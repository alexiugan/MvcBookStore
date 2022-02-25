package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Book;
import model.BookDeposit;
import view.AdminGUI;


public class AdminController{
    private AdminGUI view;
    private BookDeposit model;


    public AdminController(AdminGUI view, BookDeposit model) {
        this.view = view;
        this.model = model;


        this.view.addBookListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book b = view.getBook();
                if(!b.isDefault())
                {
                    model.addBook(b);
                    view.resetFields();
                }
            }
        });


    }

}
