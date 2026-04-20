 package com.hotel.management;

import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Hotel Dashboard");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🏨 LOGO (TOP LEFT)
        ImageIcon logoIcon = new ImageIcon("images/logo.png");
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(20, 10, 100, 100);
        add(logo);

        // TEXT
        JLabel label = new JLabel("Welcome to Hotel Management System");
        label.setBounds(140, 40, 300, 30);
        add(label);

        // 🛎️ BUTTON ICONS
        ImageIcon bellIcon = new ImageIcon("images/bell.png");
        ImageIcon roomIcon = new ImageIcon("images/room.png");

        // 🔥 Buttons
        JButton roomBtn = new JButton("Add Room");
        roomBtn.setBounds(140, 100, 200, 40);

        JButton viewBtn = new JButton("View Rooms", roomIcon);
        viewBtn.setBounds(140, 150, 200, 40);

        JButton bookBtn = new JButton("Book Room", bellIcon);
        bookBtn.setBounds(140, 200, 200, 40);

        JButton customerBtn = new JButton("View Customers");
        customerBtn.setBounds(140, 250, 200, 40);

        // 🔥 Actions
        roomBtn.addActionListener(e -> new RoomForm());
        viewBtn.addActionListener(e -> new ViewRoom());
        bookBtn.addActionListener(e -> new BookingForm());
        customerBtn.addActionListener(e -> new ViewCustomers());

        // 🔥 Add to frame
        add(roomBtn);
        add(viewBtn);
        add(bookBtn);
        add(customerBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}