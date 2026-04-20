package com.hotel.management;

import javax.swing.*;
import java.sql.*;

public class BookingForm extends JFrame {

    JTextField customerName, phone, email, roomNumber, date;
    JButton bookBtn;

    public BookingForm() {

        setTitle("Room Booking Form");

        JLabel n = new JLabel("Name:");
        JLabel p = new JLabel("Phone:");
        JLabel e = new JLabel("Email:");
        JLabel r = new JLabel("Room No:");
        JLabel d = new JLabel("Date:");

        customerName = new JTextField();
        phone = new JTextField();
        email = new JTextField();
        roomNumber = new JTextField();
        date = new JTextField();

        bookBtn = new JButton("Book Room");

        n.setBounds(50, 50, 100, 25);
        p.setBounds(50, 80, 100, 25);
        e.setBounds(50, 110, 100, 25);
        r.setBounds(50, 140, 100, 25);
        d.setBounds(50, 170, 100, 25);

        customerName.setBounds(150, 50, 150, 25);
        phone.setBounds(150, 80, 150, 25);
        email.setBounds(150, 110, 150, 25);
        roomNumber.setBounds(150, 140, 150, 25);
        date.setBounds(150, 170, 150, 25);

        bookBtn.setBounds(120, 220, 120, 30);

        add(n);
        add(p);
        add(e);
        add(r);
        add(d);
        add(customerName);
        add(phone);
        add(email);
        add(roomNumber);
        add(date);
        add(bookBtn);

        // ✅ BUTTON ACTION
        bookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                try {
                    Connection con = DBConnection.getConnection();

                    String name = customerName.getText();
                    String ph = phone.getText();
                    String em = email.getText();
                    String room = roomNumber.getText();
                    String dt = date.getText();

                    String check = "SELECT status FROM rooms WHERE room_number=?";
                    PreparedStatement ps = con.prepareStatement(check);
                    ps.setString(1, room);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next() && rs.getString("status").equalsIgnoreCase("Booked")) {
                        JOptionPane.showMessageDialog(null, "Room Already Booked!");
                        return;
                    }

                    String query = "INSERT INTO bookings (customer_name, room_number, date, customer_phone, customer_email) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);

                    pst.setString(1, name);
                    pst.setString(2, room);
                    pst.setString(3, dt);
                    pst.setString(4, ph);
                    pst.setString(5, em);

                    pst.executeUpdate();

                    String update = "UPDATE rooms SET status='Booked' WHERE room_number=?";
                    PreparedStatement pst2 = con.prepareStatement(update);
                    pst2.setString(1, room);
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Booking Successful!");

                    customerName.setText("");
                    phone.setText("");
                    email.setText("");
                    roomNumber.setText("");
                    date.setText("");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // ✅ IMPORTANT: THIS MUST BE OUTSIDE EVERYTHING
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}