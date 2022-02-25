package view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.Book;
import model.BookDeposit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

@SuppressWarnings("deprecation")
public class AdminGUI extends JFrame implements Observer {

    private BookDeposit model;
    private JPanel contentPane;
    JButton addBtn;
    JLabel titleLbl, authorLbl, genreLbl, yearLbl, priceLbl, stockLbl;
    JTextField titleTxt, authorTxt, genreTxt, yearTxt, priceTxt, stockTxt;
    DefaultTableModel tableModel;
    JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new AdminGUI("----", new BookDeposit());
    }
    /**
     * Create the frame.
     */
    public AdminGUI(String username, BookDeposit model) {
        this.model=model;
        model.addObserver(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblWelcome.setBounds(300, 11, 315, 34);
        contentPane.add(lblWelcome);


        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Year");
        tableModel.addColumn("Price");
        tableModel.addColumn("Stock");

        table = new JTable(tableModel);
        fillTable();

        //setting column dimensions
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(5);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(5).setPreferredWidth(5);

        //plancing field text in the center
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0 ; i<=6 ; i++)
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );



        table.setRowHeight(40);
        table.setDefaultEditor(Object.class, null);
        table.setBackground(new Color(255, 255, 150));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 90, 680, 350);
        contentPane.add(scroll);



        JButton btnExit = new JButton("Logout");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(620, 11, 130, 35);
        contentPane.add(btnExit);

        titleLbl = new JLabel("Title:");
        titleLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titleLbl.setBounds(60, 470, 80, 20);
        titleTxt = new JTextField();
        titleTxt.setBounds(130, 470, 80, 20);
        contentPane.add(titleLbl);
        contentPane.add(titleTxt);

        authorLbl = new JLabel("Author:");
        authorLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        authorLbl.setBounds(60, 500, 80, 20);
        authorTxt = new JTextField();
        authorTxt.setBounds(130, 500, 80, 20);
        contentPane.add(authorLbl);
        contentPane.add(authorTxt);

        genreLbl = new JLabel("Genre:");
        genreLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        genreLbl.setBounds(230, 470, 80, 20);
        genreTxt = new JTextField();
        genreTxt.setBounds(300, 470, 80, 20);
        contentPane.add(genreLbl);
        contentPane.add(genreTxt);

        yearLbl = new JLabel("Year:");
        yearLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        yearLbl.setBounds(230, 500, 80, 20);
        yearTxt = new JTextField();
        yearTxt.setBounds(300, 500, 80, 20);
        contentPane.add(yearLbl);
        contentPane.add(yearTxt);

        priceLbl = new JLabel("Price:");
        priceLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        priceLbl.setBounds(400, 470, 80, 20);
        priceTxt = new JTextField();
        priceTxt.setBounds(470, 470, 80, 20);
        contentPane.add(priceLbl);
        contentPane.add(priceTxt);

        stockLbl = new JLabel("Stock:");
        stockLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        stockLbl.setBounds(400, 500, 80, 20);
        stockTxt = new JTextField();
        stockTxt.setBounds(470, 500, 80, 20);
        contentPane.add(stockLbl);
        contentPane.add(stockTxt);

        addBtn = new JButton("Add Book");
        addBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        addBtn.setBounds(570, 480, 160, 40);
        contentPane.add(addBtn);

        JSeparator separator = new JSeparator();
        separator.setBounds(209, 56, 381, 2);
        contentPane.add(separator);

    }

    public void fillTable() {

        tableModel.setRowCount(0);
        table.setModel(tableModel);

        BookDeposit bookDep = new BookDeposit();


        for(int i =0; i<bookDep.bookArray.size();i++)
        {
            Book b = bookDep.bookArray.get(i);
            String [] row = {b.getId() + "", b.getTitle(), b.getAuthor(), b.getGenre(), b.getYear()+"", b.getPrice()+"", b.getStock()+""};
            tableModel.addRow(row);
        }
    }

    public void addBookListener(ActionListener a) {
        addBtn.addActionListener(a);
    }

    public Book getBook() {
        if(titleTxt.getText().equals("") || authorTxt.getText().equals("") || genreTxt.getText().equals("") || yearTxt.getText().equals("") || priceTxt.getText().equals("") || stockTxt.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Empty fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return new Book(-1, "", "", "", -1,-1,-1);
        }
        String title="",author="",genre="";
        int year=-1, price =-1, stock=-1;

        try {
            title = titleTxt.getText();
            author = authorTxt.getText();
            genre =genreTxt.getText();
            year = Integer.parseInt(yearTxt.getText());
            price = Integer.parseInt(priceTxt.getText());
            stock = Integer.parseInt(stockTxt.getText());

        }
        catch(Exception e1) {
            JOptionPane.showMessageDialog(this, "Invalid data!", "Input Error", JOptionPane.ERROR_MESSAGE);}

        Book b = new Book(model.bookArray.size()+1, title, author, genre, year, price, stock);
        return b;
    }

    @Override
    public void update(Observable o, Object arg) {
        fillTable();

    }

    public void resetFields() {
        titleTxt.setText("");
        authorTxt.setText("");
        genreTxt.setText("");
        yearTxt.setText("");
        priceTxt.setText("");
        stockTxt.setText("");
    }
}
