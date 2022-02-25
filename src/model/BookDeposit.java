package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


@SuppressWarnings("deprecation")
public class BookDeposit extends Observable {

    public ArrayList<Book> bookArray;
    File f = new File("books.xml");

    public BookDeposit() {
        bookArray = new ArrayList<Book>();
        readBookFile();
    }

    public void readBookFile(){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList bookList = doc.getElementsByTagName("book");

            for(int i =0; i<bookList.getLength();i++)
            {
                Node b = bookList.item(i);

                Element book = (Element) b;
                int id = Integer.parseInt(book.getAttribute("id"));
                NodeList details = book.getChildNodes();

                String title ="";
                String author ="";
                String genre ="";
                int year = 0;
                int price = 0;
                int stock = 0;

                for(int j=0; j<details.getLength();j++)
                {
                    Node n = details.item(j);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        Element name = (Element) n;

                        switch(j) {
                            case 1: {
                                title = name.getTextContent();
                                break;
                            }
                            case 3: {
                                author = name.getTextContent();
                                break;
                            }
                            case 5: {
                                genre = name.getTextContent();
                                break;
                            }
                            case 7: {
                                year = Integer.parseInt(name.getTextContent());
                                break;
                            }
                            case 9: {
                                price = Integer.parseInt(name.getTextContent());
                                break;
                            }
                            case 11: {
                                stock = Integer.parseInt(name.getTextContent());
                                break;
                            }
                        }
                    }
                }
                Book currentBook = new Book(id, title, author, genre, year, price, stock);
                bookArray.add(currentBook);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void readBooks() {
        for(int i =0; i<bookArray.size(); i++)
            System.out.println(bookArray.get(i).toString());
    }

    public void addBook(Book b) {
        bookArray.add(b);
        writeBookFile();
        setChanged();
        notifyObservers();
    }

    public void updateBook(int id, int sold) {
        for(int i =0;i<bookArray.size();i++) {
            if(bookArray.get(i).getId()==id)
            {
                bookArray.get(i).setStock(bookArray.get(i).getStock()-sold);
                break;
            }
        }
        writeBookFile();
        setChanged();
        notifyObservers();
    }

    public void writeBookFile(){

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.newDocument();

            Element rootElement = xmlDoc.createElement("catalog");

            for(int i =0; i<bookArray.size(); i++){
                Element mainElement = xmlDoc.createElement("book");
                mainElement.setAttribute("id", bookArray.get(i).getId()+"");

                Text bookTitleText = xmlDoc.createTextNode(bookArray.get(i).getTitle());
                Element bookTitle = xmlDoc.createElement("title");
                bookTitle.appendChild(bookTitleText);
                mainElement.appendChild(bookTitle);

                Text bookAuthorText = xmlDoc.createTextNode(bookArray.get(i).getAuthor());
                Element bookAuthor = xmlDoc.createElement("author");
                bookAuthor.appendChild(bookAuthorText);
                mainElement.appendChild(bookAuthor);

                //genre
                Text bookGenreText = xmlDoc.createTextNode(bookArray.get(i).getGenre());
                Element bookGenre = xmlDoc.createElement("genre");
                bookGenre.appendChild(bookGenreText);
                mainElement.appendChild(bookGenre);

                //year
                Text bookYearText = xmlDoc.createTextNode(bookArray.get(i).getYear()+"");
                Element bookYear = xmlDoc.createElement("year");
                bookYear.appendChild(bookYearText);
                mainElement.appendChild(bookYear);

                //price
                Text bookPriceText = xmlDoc.createTextNode(bookArray.get(i).getPrice()+"");
                Element bookPrice = xmlDoc.createElement("price");
                bookPrice.appendChild(bookPriceText);
                mainElement.appendChild(bookPrice);

                //stock
                Text bookStockText = xmlDoc.createTextNode(bookArray.get(i).getStock()+"");
                Element bookStock = xmlDoc.createElement("stock");
                bookStock.appendChild(bookStockText);
                mainElement.appendChild(bookStock);

                rootElement.appendChild(mainElement);
            }
            xmlDoc.appendChild(rootElement);

            DOMSource source = new DOMSource(xmlDoc);
            File f = new File("books.xml");
            Result result = new StreamResult(f);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);}
        catch(Exception e) {
            System.out.println("Error! Could not write XML file");
        }

    }
}
