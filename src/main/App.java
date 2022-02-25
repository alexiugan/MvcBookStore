package main;
import model.BookDeposit;
import view.LoginWindow;

public class App {

    public static void main(String[] args) {

        BookDeposit b = new BookDeposit();
        new LoginWindow(b);

    }

}
