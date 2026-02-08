/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author njeyagan
 */
public class Project5 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
       // Creating a sample customer
    Customer customer = new Customer("user123", "pass123",0);

    Text wlcmeText = new Text("Welcome to the BookStore App");

    Label userLabel = new Label("Username:");
    TextField userField = new TextField();
    
    Label passLabel = new Label("Password:");
    PasswordField passField = new PasswordField();

    Button loginbtn = new Button("Login");
    loginbtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String enteredUsername = userField.getText();
            String enteredPassword = passField.getText();
            
            if (enteredUsername.equals(customer.getUsername()) && enteredPassword.equals(customer.getPassword())) {
                System.out.println("Login Successful! Welcome " + customer.getUsername());
                
            } 
            
            else if(enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
                System.out.println("login Successfull! welcome admin");
                OwnerStartScreen(primaryStage);
            }
           
            
            else {
                System.out.println("Login Failed! Incorrect username or password.");
            }
        }
    });
        
        VBox vbox = new VBox(10); // Creates a VBox with 10 pixels of spacing between elements
    vbox.setAlignment(Pos.CENTER); // Center align the VBox content
    vbox.getChildren().addAll(wlcmeText, userLabel, userField, passLabel, passField, loginbtn);

    Scene scene = new Scene(vbox, 600, 600);

        
        
        
        primaryStage.setTitle("BookStore App");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
  public void OwnerStartScreen(Stage primaryStage) {
    Text ownerText = new Text("Owner Start Screen");

    Button booksBtn = new Button("Books");
    Button customersBtn = new Button("Customers");
    Button logoutBtn = new Button("Logout");

    // Logout button event to return to the login screen
    logoutBtn.setOnAction(event -> start(primaryStage)); 
    
    // Book button event to call the OwnerBookScreen method
    booksBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            OwnerBookScreen(primaryStage);
        }
    });

    customersBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            OwnerCustomerScreen(primaryStage);
        }
    });
    
    VBox vbox = new VBox(10); // Creates a VBox with spacing
    vbox.setAlignment(Pos.CENTER); 
    vbox.getChildren().addAll(ownerText, booksBtn, customersBtn, logoutBtn);

    Scene ownerScene = new Scene(vbox, 600, 600);
    primaryStage.setScene(ownerScene);
    primaryStage.show();
}
    
  
    public void OwnerBookScreen(Stage primaryStage) {
    
    Text ownerText = new Text("Owner Book Screen");

    // Creating a sample book
    Book book = new Book("Harry Potter", 13.99);

    Label bookTitleLabel = new Label("Title:");
    TextField bookTitleField = new TextField();

    Label bookPriceLabel = new Label("Price:");
    TextField priceField = new TextField();

    // Create an observable list for books
    ObservableList<Book> books = FXCollections.observableArrayList();

    // Create TableView and columns
    TableView<Book> table = new TableView<>();

    // got stuck here fix this
    TableColumn<Book, String> bookNameCol = new TableColumn<>("Book Name");
    bookNameCol.setCellValueFactory(new PropertyValueFactory<>("title"));

    TableColumn<Book, Double> bookPriceCol = new TableColumn<>("Book Price");
    bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    // Set column properties
    bookNameCol.setMinWidth(200);
    bookPriceCol.setMinWidth(100);

    // Add columns to the table
    table.getColumns().addAll(bookNameCol, bookPriceCol);
    table.setItems(books); // Bind the table to the observable list

    // Label for the table
    Label label = new Label("Book List");

    Button addBookBtn = new Button("Add Book");
    Button deleteBookBtn = new Button("Delete Book");
    Button returnBack = new Button("Return to Owner Start Screen");

    // Adding a book to the list and table
    addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String title = bookTitleField.getText();
                double price = Double.parseDouble(priceField.getText());

                // Create a new book and add it to the list
                Book newBook = new Book(title, price);
                books.add(newBook);

                // Clear the text fields after adding
                bookTitleField.clear();
                priceField.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }
    });
    
    
    // delete a book to the list and table
    deleteBookBtn.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        // Get the selected item from the table
        Book selectedBook = table.getSelectionModel().getSelectedItem();
        
        if (selectedBook != null) {
            // Remove the selected item from the list
            books.remove(selectedBook);
        } else {
            System.out.println("No book selected to delete.");
        }
    }
});
    
    // Event handler for returning to the owner start screen
    returnBack.setOnAction(event -> OwnerStartScreen(primaryStage));

    // Create a VBox to hold the components
    VBox vbox = new VBox(10);
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(ownerText,  label, table,bookTitleLabel,bookTitleField, bookPriceLabel, priceField, addBookBtn, deleteBookBtn, returnBack);

    // Create a scene and add the VBox
    Scene scene = new Scene(new Group(), 600, 800);
    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Owner Book Screen");
    primaryStage.show();
}

public void OwnerCustomerScreen(Stage primaryStage) {

    Text ownerText = new Text("Owner Customer Screen");

    Label usernameLabel = new Label("Username:");
    TextField usernameField = new TextField();

    Label passwordLabel = new Label("Password:");
    TextField passwordField = new TextField();
    
    Label pointsLabel = new Label("Points:");
    TextField pointsField = new TextField();

    // Create an observable list for customers
    ObservableList<Customer> Customers = FXCollections.observableArrayList();

    // Create TableView and columns
    TableView<Customer> table = new TableView<>();

    TableColumn<Customer, String> usernameCol = new TableColumn<>("Customer Username");
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Customer, String> passwordCol = new TableColumn<>("Password");
    passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

    TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");
    pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    // Set column properties
    usernameCol.setMinWidth(200);
    passwordCol.setMinWidth(100);
    pointsCol.setMinWidth(100);

    // Add columns to the table
    table.getColumns().addAll(usernameCol, passwordCol, pointsCol);
    table.setItems(Customers);

    // Label for the table
    Label label = new Label("Customer List");

    Button addCustomerBtn = new Button("Add Customer");
    Button deleteCustomerBtn = new Button("Delete Customer");
    Button returnBack = new Button("Return to Owner Start Screen");

    // Adding a customer to the list and table
    addCustomerBtn.setOnAction(event -> {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            int points = Integer.parseInt(pointsField.getText());

            // Create a new customer and add it to the list
            Customer newCustomer = new Customer(username, password, points);
            Customers.add(newCustomer);

            // Clear the text fields after adding
            usernameField.clear();
            passwordField.clear();
            pointsField.clear();
        } catch (NumberFormatException e) {
            System.out.println("Invalid points. Please enter a valid number.");
        }
    });

    // Deleting a selected customer from the list and table
    deleteCustomerBtn.setOnAction(event -> {
        Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Customers.remove(selectedCustomer);
        } else {
            System.out.println("No customer selected to delete.");
        }
    });

    // Return to the owner start screen
    returnBack.setOnAction(event -> OwnerStartScreen(primaryStage));

    // Create a VBox to hold the components
    VBox vbox = new VBox(10);
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(ownerText, label, table, usernameLabel, usernameField, passwordLabel, passwordField, pointsLabel, pointsField, addCustomerBtn, deleteCustomerBtn, returnBack);

    // Create a scene and add the VBox
    Scene scene = new Scene(new Group(), 600, 800);
    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Owner Customer Screen");
    primaryStage.show();
}

        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
