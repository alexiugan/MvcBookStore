package view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.Book;
import model.BookDeposit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.awt.event.ActionEvent;

@SuppressWarnings("deprecation")
public class EmpGUI extends JFrame implements Observer {

    BookDeposit model;
    private JPanel contentPane;
    DefaultTableModel tableModel;
    JTable table;
    JComboBox<String> searchOption;
    JTextField searchTxt;
    public JTextField sellTxt;
    JLabel searchLbl, searchByLbl, sellLbl;
    public JComboBox<String> bookSale;
    JButton sellBtn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new EmpGUI("----", new BookDeposit());
    }
    /**
     * Create the frame.
     */
    public EmpGUI(String username, BookDeposit model) {
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

        searchByLbl = new JLabel("Search by:");
        searchByLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        searchByLbl.setBounds(50, 75, 100, 34);
        contentPane.add(searchByLbl);

        String[] op = {"Title", "Author", "Genre"};
        searchOption = new JComboBox<String>(op);
        searchOption.setBounds(160, 80, 80, 30);
        contentPane.add(searchOption);

        searchLbl = new JLabel("Search:");
        searchLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        searchLbl.setBounds(300, 75, 100, 34);
        contentPane.add(searchLbl);

        searchTxt = new JTextField();
        searchTxt.setBounds(390, 80, 150, 30);
        searchTxt.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                type();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                type();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                type();
            }

            private void type()
            {
                fillTable();
            }

        });
        contentPane.add(searchTxt);

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
        scroll.setBounds(50, 150, 680, 290);
        contentPane.add(scroll);


        sellLbl = new JLabel("Sell:");
        sellLbl.setBounds(50,470,50,20);
        sellLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(sellLbl);

        sellTxt = new JTextField();
        sellTxt.setBounds(100, 470, 80, 30);
        contentPane.add(sellTxt);

        bookSale = new JComboBox<String>();
        for(int i =0; i<model.bookArray.size();i++)
            bookSale.addItem(model.bookArray.get(i).getId() + " - " + model.bookArray.get(i).getTitle());
        bookSale.setBounds(200,470, 200, 30);
        contentPane.add(bookSale);

        sellBtn = new JButton("Sell");
        sellBtn.setBounds(320, 510, 80, 30);
        contentPane.add(sellBtn);


        JButton btnExit = new JButton("Logout");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.setBounds(520, 470, 182, 49);
        contentPane.add(btnExit);

        JSeparator separator = new JSeparator();
        separator.setBounds(209, 56, 381, 2);
        contentPane.add(separator);

    }

    public void fillTable() {

        tableModel.setRowCount(0);
        table.setModel(tableModel);





        //if search field is empty, show all
        if(searchTxt.getText().equals(""))
        {
            for(int i =0; i<model.bookArray.size();i++)
            {
                Book b = model.bookArray.get(i);
                String [] row = {b.getId() + "", b.getTitle(), b.getAuthor(), b.getGenre(), b.getYear()+"", b.getPrice()+"", b.getStock()+""};
                tableModel.addRow(row);
            }
        }

        else {

            for(int i =0; i<model.bookArray.size();i++)
            {
                boolean show = false;
                Book b = model.bookArray.get(i);

                if(searchOption.getSelectedItem().toString().equals("Title"))
                {
                    if(search(b.getTitle()))
                        show=true;
                    else
                        show=false;
                }

                else if(searchOption.getSelectedItem().toString().equals("Author"))
                {
                    if(search(b.getAuthor()))
                        show=true;
                    else
                        show=false;
                }

                else if(searchOption.getSelectedItem().toString().equals("Genre"))
                {
                    if(search(b.getGenre()))
                        show=true;
                    else
                        show=false;
                }

                if(show) {
                    String [] row = {b.getId() + "", b.getTitle(), b.getAuthor(), b.getGenre(), b.getYear()+"", b.getPrice()+"", b.getStock()+""};
                    tableModel.addRow(row);
                }
            }
        }
    }

    public boolean search(String word)
    {
        String regex = searchTxt.getText().toUpperCase() + "(.*)";
        if(word.toUpperCase().matches(regex))
            return true;
        return false;
    }
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        fillTable();
        if(bookSale.getItemCount()<model.bookArray.size())
            bookSale.addItem(model.bookArray.get(model.bookArray.size()-1).getId() + " - " + model.bookArray.get(model.bookArray.size()-1).getTitle());
    }

    public void addSellListener(ActionListener a) {
        sellBtn.addActionListener(a);
    }

    public Book getSoldBook() {
        String book = bookSale.getSelectedItem().toString();
        Book b = new Book(-1,"","","",-1,-1,-1);

        Scanner sc = new Scanner(book);

        int id = sc.nextInt();

        for(int i =0; i<model.bookArray.size();i++){
            if(model.bookArray.get(i).getId()==id)
                b=model.bookArray.get(i);
        }
        sc.close();

        return b;
    }

    public int getNrSold()
    {
        int i = 0;
        try {
            i= Integer.parseInt(sellTxt.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Enter a valid number", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
        return i;
    }

}
