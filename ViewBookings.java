package com.hotel.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.sql.*;

public class ViewBookings extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton deleteBtn, checkoutBtn, billBtn, printBtn;

    public ViewBookings() {

        setTitle("View Bookings");
        setLayout(null);

        model = new DefaultTableModel();

        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Room");
        model.addColumn("Date");
        model.addColumn("Phone");
        model.addColumn("Email");

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 650, 250);
        add(sp);

        // 🔥 BUTTONS
        deleteBtn = new JButton("Delete");
        checkoutBtn = new JButton("Checkout");
        billBtn = new JButton("Generate Bill");
        printBtn = new JButton("Print Bill");

        deleteBtn.setBounds(20, 300, 120, 30);
        checkoutBtn.setBounds(160, 300, 120, 30);
        billBtn.setBounds(300, 300, 150, 30);
        printBtn.setBounds(470, 300, 120, 30);

        add(deleteBtn);
        add(checkoutBtn);
        add(billBtn);
        add(printBtn);

        loadData();

        // 🔴 DELETE
        deleteBtn.addActionListener(e -> {

            try {
                int row = table.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Select booking!");
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM bookings WHERE id=?"
                );

                ps.setInt(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Deleted!");

                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 🔵 CHECKOUT
        checkoutBtn.addActionListener(e -> {

            try {
                int row = table.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Select booking!");
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                String room = model.getValueAt(row, 2).toString();

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE bookings SET status='CHECKED_OUT' WHERE id=?"
                );
                ps.setInt(1, id);
                ps.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement(
                        "UPDATE rooms SET status='AVAILABLE' WHERE room_number=?"
                );
                ps2.setString(1, room);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null, "Checkout Done!");

                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 🟢 GENERATE BILL
        billBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select booking!");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            BillGenerator.generate(id);
        });

        // 🟣 PRINT BILL
        printBtn.addActionListener(e -> {

            try {
                File file = new File("bill.pdf");

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(null, "Generate bill first!");
                    return;
                }

                Desktop.getDesktop().print(file);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setSize(750, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔥 LOAD DATA
    void loadData() {

        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT * FROM bookings"
            );

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("room_number"),
                        rs.getString("date"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_email")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}