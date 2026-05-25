package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.UUID;

public class GuiApp extends Application {
    private Store store;
    private ObservableList<String> itemsList;
    private ListView<String> listView;
    private TextArea detailsArea;

    @Override
    public void start(Stage primaryStage) {
        store = new Store("Clothes Paradise", "вул. Хрещатик, 15");
        itemsList = FXCollections.observableArrayList();
        listView = new ListView<>(itemsList);
        listView.setPrefHeight(200);

        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setPrefHeight(150);

        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Clothes", "Shirt", "Pants", "Jacket", "Sweater");
        typeCombo.setValue("Clothes");

        TextField typeField = new TextField();
        typeField.setPromptText("Тип одягу");

        ComboBox<Size> sizeCombo = new ComboBox<>();
        sizeCombo.getItems().addAll(Size.values());
        sizeCombo.setValue(Size.M);

        TextField priceField = new TextField();
        priceField.setPromptText("Ціна");

        TextField brandField = new TextField();
        brandField.setPromptText("Бренд");

        ComboBox<Material> materialCombo = new ComboBox<>();
        materialCombo.getItems().addAll(Material.values());
        materialCombo.setValue(Material.COTTON);

        TextField extraField = new TextField();
        extraField.setPromptText("Додатково (кишеня/довжина/капюшон/блискавка)");

        Button addButton = new Button("Додати");

        inputGrid.add(new Label("Тип об'єкта:"), 0, 0);
        inputGrid.add(typeCombo, 1, 0);
        inputGrid.add(new Label("Тип одягу:"), 0, 1);
        inputGrid.add(typeField, 1, 1);
        inputGrid.add(new Label("Розмір:"), 0, 2);
        inputGrid.add(sizeCombo, 1, 2);
        inputGrid.add(new Label("Ціна:"), 0, 3);
        inputGrid.add(priceField, 1, 3);
        inputGrid.add(new Label("Бренд:"), 0, 4);
        inputGrid.add(brandField, 1, 4);
        inputGrid.add(new Label("Матеріал:"), 0, 5);
        inputGrid.add(materialCombo, 1, 5);
        inputGrid.add(new Label("Додатково:"), 0, 6);
        inputGrid.add(extraField, 1, 6);
        inputGrid.add(addButton, 1, 7);

        TextField searchUuidField = new TextField();
        searchUuidField.setPromptText("Введіть UUID");
        Button searchButton = new Button("Знайти");

        HBox searchBox = new HBox(10, searchUuidField, searchButton);
        searchBox.setPadding(new Insets(10));

        addButton.setOnAction(e -> {
            try {
                String typeStr = typeField.getText().trim();
                if (typeStr.isEmpty()) {
                    showAlert("Помилка", "Тип одягу не може бути порожнім");
                    return;
                }
                Size size = sizeCombo.getValue();
                double price = Double.parseDouble(priceField.getText().trim());
                String brand = brandField.getText().trim();
                if (brand.isEmpty()) {
                    showAlert("Помилка", "Бренд не може бути порожнім");
                    return;
                }
                Material material = materialCombo.getValue();
                String extra = extraField.getText().trim();
                String selectedType = typeCombo.getValue();

                Clothes clothes = null;
                switch (selectedType) {
                    case "Shirt":
                        boolean hasPocket = extra.equalsIgnoreCase("так") || extra.equalsIgnoreCase("true");
                        clothes = new Shirt(typeStr, size, price, brand, material, hasPocket, "стандартний");
                        break;
                    case "Pants":
                        int length = 80;
                        try { length = Integer.parseInt(extra); } catch (NumberFormatException ex) {}
                        clothes = new Pants(typeStr, size, price, brand, material, length, false);
                        break;
                    case "Jacket":
                        boolean hasHood = extra.equalsIgnoreCase("так") || extra.equalsIgnoreCase("true");
                        clothes = new Jacket(typeStr, size, price, brand, material, hasHood, false);
                        break;
                    case "Sweater":
                        boolean hasZip = extra.equalsIgnoreCase("так") || extra.equalsIgnoreCase("true");
                        clothes = new Sweater(typeStr, size, price, brand, material, hasZip, "круглий");
                        break;
                    default:
                        clothes = new Clothes(typeStr, size, price, brand, material) {};
                        break;
                }
                store.addNewClothes(clothes, 1);
                updateListView();
                clearFields(typeField, priceField, brandField, extraField);
                typeCombo.setValue("Clothes");
                sizeCombo.setValue(Size.M);
                materialCombo.setValue(Material.COTTON);
            } catch (NumberFormatException ex) {
                showAlert("Помилка", "Ціна має бути числом");
            } catch (IllegalArgumentException ex) {
                showAlert("Помилка", ex.getMessage());
            }
        });

        searchButton.setOnAction(e -> {
            String uuidStr = searchUuidField.getText().trim();
            if (uuidStr.isEmpty()) {
                showAlert("Помилка", "Введіть UUID");
                return;
            }
            try {
                UUID uuid = UUID.fromString(uuidStr);
                StoreItem found = store.findByUuid(uuid);
                if (found != null) {
                    detailsArea.setText("Знайдено:\n" + found.getClothes().toString() + "\nКількість: " + found.getQuantity());
                } else {
                    detailsArea.setText("❌ Товар з UUID " + uuidStr + " не знайдено.");
                }
            } catch (IllegalArgumentException ex) {
                detailsArea.setText("❌ Некоректний формат UUID: " + uuidStr);
            }
        });

        VBox root = new VBox(10, inputGrid, new Label("Список товарів:"), listView, new Label("Деталі пошуку:"), searchBox, detailsArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("Clothes Store Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateListView();
    }

    private void updateListView() {
        itemsList.clear();
        for (StoreItem item : store.getAllItems()) {
            itemsList.add(item.getClothes().getShortInfo() + " [x" + item.getQuantity() + "]");
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}