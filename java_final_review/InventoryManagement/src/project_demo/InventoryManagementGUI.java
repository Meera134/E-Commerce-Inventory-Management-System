package project_demo;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

public class InventoryManagementGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField nameField, priceField, quantityField, categoryField, categoryNameField, minPriceField, maxPriceField;
    private ProductBST bst;

    public InventoryManagementGUI() {
        bst = new ProductBST();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Inventory Management System");
        frame.setBounds(100, 100, 1393, 801);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel categoryLabel = new JLabel("Category ID:");
        categoryLabel.setBounds(20, 65, 100, 25);
        frame.getContentPane().add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBackground(new Color(245, 245, 220));
        categoryField.setForeground(new Color(0, 0, 0));
        categoryField.setBounds(91, 65, 100, 25);
        frame.getContentPane().add(categoryField);

        JLabel categoryNameLabel = new JLabel("Category Name:");
        categoryNameLabel.setBounds(217, 65, 100, 25);
        frame.getContentPane().add(categoryNameLabel);

        categoryNameField = new JTextField();
        categoryNameField.setBackground(new Color(245, 245, 220));
        categoryNameField.setBounds(300, 65, 150, 25);
        frame.getContentPane().add(categoryNameField);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(490, 65, 100, 25);
        frame.getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBackground(new Color(245, 245, 220));
        nameField.setBounds(577, 65, 150, 25);
        frame.getContentPane().add(nameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(772, 65, 39, 25);
        frame.getContentPane().add(priceLabel);

        priceField = new JTextField();
        priceField.setBackground(new Color(245, 245, 220));
        priceField.setBounds(807, 65, 100, 25);
        frame.getContentPane().add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(928, 65, 80, 25);
        frame.getContentPane().add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBackground(new Color(245, 245, 220));
        quantityField.setBounds(1014, 65, 100, 25);
        frame.getContentPane().add(quantityField);

        JButton addButton = new JButton("Add Product");
        addButton.setBackground(new Color(255, 250, 250));
        addButton.setForeground(new Color(220, 20, 60));
        addButton.setBounds(300, 121, 150, 30);
        frame.getContentPane().add(addButton);

        JButton removeButton = new JButton("Remove Quantity");
        removeButton.setForeground(new Color(220, 20, 60));
        removeButton.setBackground(new Color(255, 250, 250));
        removeButton.setBounds(505, 121, 150, 30);
        frame.getContentPane().add(removeButton);

        JButton displayButton = new JButton("Display Inventory");
        displayButton.setForeground(new Color(138, 43, 226));
        displayButton.setBackground(new Color(240, 248, 255));
        displayButton.setBounds(429, 240, 150, 30);
        frame.getContentPane().add(displayButton);

        JButton sortButton = new JButton("Sort by Price");
        sortButton.setForeground(new Color(220, 20, 60));
        sortButton.setBackground(new Color(255, 250, 250));
        sortButton.setBounds(732, 121, 150, 30);
        frame.getContentPane().add(sortButton);

        JLabel minLabel = new JLabel("Min Price:");
        minLabel.setBounds(202, 180, 70, 25);
        frame.getContentPane().add(minLabel);

        minPriceField = new JTextField();
        minPriceField.setBackground(new Color(245, 245, 220));
        minPriceField.setBounds(263, 180, 80, 25);
        frame.getContentPane().add(minPriceField);

        JLabel maxLabel = new JLabel("Max Price:");
        maxLabel.setBounds(380, 180, 70, 25);
        frame.getContentPane().add(maxLabel);

        maxPriceField = new JTextField();
        maxPriceField.setBackground(new Color(245, 245, 220));
        maxPriceField.setBounds(464, 180, 80, 25);
        frame.getContentPane().add(maxPriceField);

        JButton filterPriceRangeBtn = new JButton("Filter Price Range");
        filterPriceRangeBtn.setForeground(new Color(220, 20, 60));
        filterPriceRangeBtn.setBackground(new Color(255, 250, 250));
        filterPriceRangeBtn.setBounds(641, 177, 180, 30);
        frame.getContentPane().add(filterPriceRangeBtn);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 280, 1294, 427);
        frame.getContentPane().add(scrollPane);

        // --- Action Listeners ---
        addButton.addActionListener(e -> {
            try {
                int categoryId = Integer.parseInt(categoryField.getText());
                String categoryName = categoryNameField.getText();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                bst.addProduct(categoryId, categoryName, name, price, quantity);
                displayInventory();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please check your values.");
            }
        });

        removeButton.addActionListener(e -> {
            try {
                int categoryId = Integer.parseInt(categoryField.getText());
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                bst.deleteProduct(categoryId, name, quantity);
                displayInventory();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please check your values.");
            }
        });

        displayButton.addActionListener(e -> displayInventory());

        sortButton.addActionListener(e -> {
            try {
                int categoryId = Integer.parseInt(categoryField.getText());
                bst.sortProducts(categoryId, "price");
                displayInventory();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid Category ID.");
            }
        });

        filterPriceRangeBtn.addActionListener(e -> {
            try {
                int categoryId = Integer.parseInt(categoryField.getText());
                double min = Double.parseDouble(minPriceField.getText());
                double max = Double.parseDouble(maxPriceField.getText());

                textArea.setText("");
                
                JLabel lblNewLabel = new JLabel("INVENTORY MANAGEMENT SYSTEM");
                lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 20));
                lblNewLabel.setBounds(426, 10, 354, 13);
                frame.getContentPane().add(lblNewLabel);
                BSTNode node = bst.findCategory(categoryId);
                if (node != null) {
                    ProductNode temp = node.products.head;
                    textArea.append("Products in category '" + node.categoryName + "' within $" + min + " - $" + max + ":\n");
                    boolean found = false;
                    while (temp != null) {
                        if (temp.product.price >= min && temp.product.price <= max) {
                            textArea.append("  - " + temp.product + "\n");
                            found = true;
                        }
                        temp = temp.next;
                    }
                    if (!found) {
                        textArea.append("No products found in this price range.\n");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Category ID not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers for Category ID and Price Range.");
            }
        });

        frame.setVisible(true);
    }

    private void displayInventory() {
        textArea.setText("");
        java.util.List<BSTNode> categories = new java.util.ArrayList<>();
        collectCategories(bst.root, categories);
        for (BSTNode node : categories) {
            textArea.append("Category: " + node.categoryName + " (ID: " + node.categoryId + ")\n");
            ProductNode temp = node.products.head;
            while (temp != null) {
                textArea.append("  - " + temp.product + "\n");
                temp = temp.next;
            }
        }
    }

    private void collectCategories(BSTNode node, java.util.List<BSTNode> list) {
        if (node != null) {
            collectCategories(node.left, list);
            list.add(node);
            collectCategories(node.right, list);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementGUI::new);
    }
}
