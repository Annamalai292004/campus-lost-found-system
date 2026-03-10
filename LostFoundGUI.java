import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LostFoundGUI extends JFrame {
    private ArrayList<Item> lostItems = new ArrayList<>();
    private ArrayList<Item> foundItems = new ArrayList<>();
    private JTextArea displayArea;
    private JTextField nameField, descField, locationField, contactField, idField;
    private JComboBox<String> typeBox;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    public LostFoundGUI() {
        setTitle("Campus Lost & Found System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createMainMenuPanel(), "MENU");
        mainPanel.add(createLostPanel(), "LOST");
        mainPanel.add(createFoundPanel(), "FOUND");
        mainPanel.add(createClaimPanel(), "CLAIM");
        mainPanel.add(createViewPanel(), "VIEW");
        
        add(mainPanel);
        
        cardLayout.show(mainPanel, "MENU");
    }
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("CAMPUS LOST & FOUND", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(new EmptyBorder(30, 100, 30, 100));
        
        JButton lostBtn = createStyledButton("I LOST Something", new Color(255, 99, 71));
        JButton foundBtn = createStyledButton("I FOUND Something", new Color(60, 179, 113));
        JButton claimBtn = createStyledButton("CLAIM an Item", new Color(30, 144, 255));
        JButton viewBtn = createStyledButton("View All Items", new Color(255, 165, 0));
        JButton exitBtn = createStyledButton("Exit", new Color(220, 20, 60));
        
        lostBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOST"));
        foundBtn.addActionListener(e -> cardLayout.show(mainPanel, "FOUND"));
        claimBtn.addActionListener(e -> {
            refreshClaimPanel();
            cardLayout.show(mainPanel, "CLAIM");
        });
        viewBtn.addActionListener(e -> {
            refreshViewPanel();
            cardLayout.show(mainPanel, "VIEW");
        });
        exitBtn.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(lostBtn);
        buttonPanel.add(foundBtn);
        buttonPanel.add(claimBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(exitBtn);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        JLabel footer = new JLabel("Find What You Lost!", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(footer, BorderLayout.SOUTH);
        
        return panel;
    }
    private JPanel createLostPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("REPORT LOST ITEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(255, 99, 71));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        
        formPanel.add(new JLabel("Your Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Category:"));
        typeBox = new JComboBox<>(new String[]{"Electronics", "Books", "ID Card", "Accessories", "Other"});
        formPanel.add(typeBox);
        
        formPanel.add(new JLabel("Description:"));
        descField = new JTextField();
        formPanel.add(descField);
        
        formPanel.add(new JLabel("Location Lost:"));
        locationField = new JTextField();
        formPanel.add(locationField);
        
        formPanel.add(new JLabel("Phone Number:"));
        contactField = new JTextField();
        formPanel.add(contactField);
        
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBackground(new Color(60, 179, 113));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton backBtn = new JButton("⬅ BACK");
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        formPanel.add(submitBtn);
        formPanel.add(backBtn);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        submitBtn.addActionListener(e -> submitLost());
        backBtn.addActionListener(e -> {
            clearFields();
            cardLayout.show(mainPanel, "MENU");
        });
        
        return panel;
    }
    private JPanel createFoundPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel(" REPORT FOUND ITEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(60, 179, 113));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        
        formPanel.add(new JLabel("Your Name:"));
        JTextField fNameField = new JTextField();
        formPanel.add(fNameField);
        
        formPanel.add(new JLabel("Category:"));
        JComboBox<String> fTypeBox = new JComboBox<>(new String[]{"Electronics", "Books", "ID Card", "Accessories", "Other"});
        formPanel.add(fTypeBox);
        
        formPanel.add(new JLabel("Description:"));
        JTextField fDescField = new JTextField();
        formPanel.add(fDescField);
        
        formPanel.add(new JLabel("Location Found:"));
        JTextField fLocationField = new JTextField();
        formPanel.add(fLocationField);
        
        formPanel.add(new JLabel("Kept At:"));
        JTextField fKeptField = new JTextField("Security Office");
        formPanel.add(fKeptField);
        
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBackground(new Color(60, 179, 113));
        submitBtn.setForeground(Color.WHITE);
        
        JButton backBtn = new JButton("⬅ BACK");
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        
        formPanel.add(submitBtn);
        formPanel.add(backBtn);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        submitBtn.addActionListener(e -> {
            Item item = new Item(
                foundItems.size() + 1,
                fNameField.getText(),
                (String)fTypeBox.getSelectedItem(),
                fDescField.getText(),
                fLocationField.getText(),
                fKeptField.getText(),
                "FOUND"
            );
            foundItems.add(item);
            
            JOptionPane.showMessageDialog(this, 
                "Found item registered!\nID: " + item.id + "\nKept at: " + item.contact,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            fNameField.setText("");
            fDescField.setText("");
            fLocationField.setText("");
            cardLayout.show(mainPanel, "MENU");
        });
        
        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "MENU");
        });
        
        return panel;
    }
    private JPanel createClaimPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("CLAIM AN ITEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(30, 144, 255));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        
        displayArea = new JTextArea(8, 40);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setBorder(new TitledBorder("Items in Security"));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.WHITE);
        
        inputPanel.add(new JLabel("Item ID:"));
        idField = new JTextField(5);
        inputPanel.add(idField);
        
        JButton claimBtn = new JButton("CLAIM");
        claimBtn.setBackground(new Color(30, 144, 255));
        claimBtn.setForeground(Color.WHITE);
        inputPanel.add(claimBtn);
        
        JButton backBtn = new JButton("BACK");
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        inputPanel.add(backBtn);
        
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        claimBtn.addActionListener(e -> processClaim());
        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "MENU");
        });
        
        return panel;
    }
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("ALL ITEMS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(255, 165, 0));
        panel.add(title, BorderLayout.NORTH);
        
        displayArea = new JTextArea(15, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.setBackground(new Color(30, 144, 255));
        refreshBtn.setForeground(Color.WHITE);
        
        JButton backBtn = new JButton("⬅ BACK");
        backBtn.setBackground(new Color(220, 20, 60));
        backBtn.setForeground(Color.WHITE);
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshBtn.addActionListener(e -> refreshViewPanel());
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        return panel;
    }
    private void submitLost() {
        if (nameField.getText().isEmpty() || descField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        Item item = new Item(
            lostItems.size() + 1,
            nameField.getText(),
            (String)typeBox.getSelectedItem(),
            descField.getText(),
            locationField.getText(),
            contactField.getText(),
            "LOST"
        );
        lostItems.add(item);
        
        JOptionPane.showMessageDialog(this, 
            "Lost item registered!\nYour ID: " + item.id + "\nWe'll notify you!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        clearFields();
        cardLayout.show(mainPanel, "MENU");
    }
    
    private void processClaim() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            // Find item
            Item item = null;
            for (Item f : foundItems) {
                if (f.id == id) {
                    item = f;
                    break;
                }
            }
            
            if (item == null) {
                JOptionPane.showMessageDialog(this, "Item not found!");
                return;
            }
            
            // Verification dialog
            String desc = JOptionPane.showInputDialog(this, 
                "Describe the item (color/features):");
            
            if (desc != null && item.description.toLowerCase().contains(desc.toLowerCase())) {
                JOptionPane.showMessageDialog(this,
                    " MATCH FOUND!\nYour item is at: " + item.contact + 
                    "\nGo collect it with your ID!");
                foundItems.remove(item);
                refreshClaimPanel();
            } else {
                String phone = JOptionPane.showInputDialog(this,
                    " No match!\nEnter phone for notification:");
                JOptionPane.showMessageDialog(this,
                    "We'll call you at: " + phone + " if found!");
            }
            
            idField.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID!");
        }
    }
    
    private void refreshClaimPanel() {
        StringBuilder sb = new StringBuilder();
        if (foundItems.isEmpty()) {
            sb.append("No items in security.");
        } else {
            for (Item f : foundItems) {
                sb.append("ID: ").append(f.id)
                  .append(" | ").append(f.category)
                  .append(" | ").append(f.description)
                  .append(" | At: ").append(f.contact)
                  .append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }
    
    private void refreshViewPanel() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LOST ITEMS ===\n");
        if (lostItems.isEmpty()) {
            sb.append("No lost items.\n");
        } else {
            for (Item i : lostItems) {
                sb.append("ID: ").append(i.id)
                  .append(" | ").append(i.personName)
                  .append(" | ").append(i.category)
                  .append(" | ").append(i.description)
                  .append(" | Lost: ").append(i.location)
                  .append("\n");
            }
        }
        
        sb.append("\n=== FOUND ITEMS ===\n");
        if (foundItems.isEmpty()) {
            sb.append("No found items.\n");
        } else {
            for (Item i : foundItems) {
                sb.append("ID: ").append(i.id)
                  .append(" | ").append(i.personName)
                  .append(" | ").append(i.category)
                  .append(" | ").append(i.description)
                  .append(" | Found: ").append(i.location)
                  .append(" | At: ").append(i.contact)
                  .append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        return btn;
    }
    
    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        locationField.setText("");
        contactField.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LostFoundGUI().setVisible(true);
        });
    }
}
class Item {
    int id;
    String personName;
    String category;
    String description;
    String location;
    String contact;
    String type;
    
    public Item(int id, String name, String cat, String desc, 
                String loc, String contact, String type) {
        this.id = id;
        this.personName = name;
        this.category = cat;
        this.description = desc;
        this.location = loc;
        this.contact = contact;
        this.type = type;
    }
}