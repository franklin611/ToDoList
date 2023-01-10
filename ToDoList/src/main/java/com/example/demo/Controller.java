package com.example.demo;

import com.example.demo.datamodel.Contact;
import com.example.demo.datamodel.ContactData;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private BorderPane mainPanel;
    @FXML
    private TableView<Contact> contactsTable;
    private ContactData data;

    public Controller() {
    }

    public void initialize() {
        this.data = new ContactData();
        this.data.loadContacts();
        this.contactsTable.setItems(this.data.getContacts());
    }


    @FXML
    public void showQuote(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(this.mainPanel.getScene().getWindow());
        dialog.setTitle("Motivational Quote");

        Random rnd = new Random();
        int number = rnd.nextInt(0, 23);

        String[] quotes = {"Start each day with a positive thought and a grateful heart. – Roy T. Bennett\n" ,
                "Today is your day. Your mountain is waiting so get on your way.- Dr. Seuss\n" ,
                "Light tomorrow with today. –Elizabeth Barrett Browning\n" ,
                "Yesterday is not ours to recover, but tomorrow is ours to win or lose. – Lyndon B. Johnson\n" ,
                "When it rains, it pours? but soon, the sun shines again. Stay positive. Better days are on their way.\n" ,
                "Learn the rules like a pro, so you can break them like an artist. —Pablo Picasso\n" ,
                "Never give up on a dream just because of the time it will take to accomplish it. The time will pass anyway. – Earl Nightingale\n" ,
                "Just one small positive thought in the morning can change your whole day. – Dalai Lama\n" ,
                "Push yourself because no one else is going to do it for you.\n" ,
                "Yesterday is not ours to recover, but tomorrow is ours to win or lose. —Lyndon Johnson\n" ,
                "Every object, every being, is a jar of delight. Be a connoisseur.- Rumi\n" ,
                "Yesterday’s home runs don’t win today’s games. — Babe Ruth\n" ,
                "Someday is not a say of the week. – Janet Dailey\n" ,
                "You’re off to great places, today is your day. Your mountain is waiting, so get on your way. – Dr. Seuss\n" ,
                "Don’t count the days, make the days count. – Muhammad Ali\n" ,
                "Make each day your masterpiece. – John Wooden\n" ,
                "You’re braver than you believe and stronger than you seem, and smarter than you think. – A.A Mine\n" ,
                "Wonder is the beginning of wisdom. – Socrates\n" ,
                "How wonderful it is that nobody need wait a single moment before starting to improve the world. —Anne Frank\n" ,
                "Believe you can and you’re halfway there. – Theodore Roosevelt\n" ,
                "Don’t judge each day by the harvest you reap but by the seeds that you plant. – Robert Louis Stevenson\n" ,
                "Every day, there are 1,440 minutes. That means we have 1,440 daily opportunities to make a positive impact. – Les Brown\n" ,
                "You’ve got to get up every morning with determination if you’re going to go to bed with satisfaction. – George Lorimer\n" ,
                "When you want something, all the universe conspires in helping you to achieve it. – Paulo Coelho"};



        String motivationalQuote = quotes[number];
        dialog.setContentText(motivationalQuote);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();

        }



    @FXML
    public void showTaskPage() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Already on Task Page!");
        alert.setHeaderText((String)null);
        alert.setContentText("You are already displaying the task page.");
        alert.showAndWait();
    }

    @FXML
    public void showCalendarPage() throws IOException {
        Stage primaryStage  =new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 400);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();

        root.setCenter(popupContent);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    public void showAddContactDialog() {
        Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(this.mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Task");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("contactdialog.fxml"));

        try {
            dialog.getDialogPane().setContent((Node)fxmlLoader.load());
        } catch (IOException var6) {
            System.out.println("Couldn't load the dialog");
            var6.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactController contactController = (ContactController)fxmlLoader.getController();
            Contact newContact = contactController.getNewContact();
            this.data.addContact(newContact);
            this.data.saveContacts();
        }

    }

    @FXML
    public void showEditContactDialog() {
        Contact selectedContact = (Contact)this.contactsTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Task Selected");
            alert.setHeaderText((String)null);
            alert.setContentText("Please select the task you want to edit.");
            alert.showAndWait();
        } else {
            Dialog<ButtonType> dialog = new Dialog();
            dialog.initOwner(this.mainPanel.getScene().getWindow());
            dialog.setTitle("Edit Contact:");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("contactdialog.fxml"));

            try {
                dialog.getDialogPane().setContent((Node)fxmlLoader.load());
            } catch (IOException var6) {
                System.out.println("Couldn't load the dialog");
                var6.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            ContactController contactController = (ContactController)fxmlLoader.getController();
            contactController.editContact(selectedContact);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                contactController.updateContact(selectedContact);
                this.data.saveContacts();
            }

        }
    }

    public void deleteContact() {
        Contact selectedContact = (Contact)this.contactsTable.getSelectionModel().getSelectedItem();
        Alert alert;
        if (selectedContact == null) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText((String)null);
            alert.setContentText("Please select the task you want to delete.");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText((String)null);
            String var10001 = selectedContact.getFirstName();
            alert.setContentText("Are you sure you want to delete the selected task: " + var10001 + " " + selectedContact.getLastName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.data.deleteContact(selectedContact);
                this.data.saveContacts();
            }

        }
    }
}
