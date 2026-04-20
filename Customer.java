package com.hotel.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Customer extends JFrame {

    JTable table;
    DefaultTableModel model;

    public Customer() {

        setTitle("View Customers (Bookings Data)");

        model = new DefaultTableModel();
        table = new JTable(model);

        // Columns
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Room No");
        model.addColumn("Date");
        model.addColumn("Phone");
        model.addColumn("Email");

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 550, 250);
        add(sp);

        loadData();

        setSize(600, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void loadData() {
        try {
            model.setRowCount(0); // clear old data

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM bookings";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                // Using column index (MOST SAFE METHOD)
                model.addRow(new Object[]{
                        rs.getObject(1),
                        rs.getObject(2),
                        rs.getObject(3),
                        rs.getObject(4),
                        rs.getObject(5),
                        rs.getObject(6)
                });
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}