package com.hotel.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewRoom extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewRoom() {
        setTitle("View Rooms");

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Room No");
        model.addColumn("Type");
        model.addColumn("Price");
        model.addColumn("Status");

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 350, 200);

        add(sp);

        loadRooms();

        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔥 Load data from database
    public void loadRooms() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM rooms";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}