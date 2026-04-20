package com.hotel.management;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.awt.Desktop;

public class BillGenerator {

    public static void generate(int bookingId) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT customer_name, room_number, customer_phone FROM bookings WHERE id=?"
            );

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Booking not found!");
                return;
            }

            String name = rs.getString("customer_name");
            String room = rs.getString("room_number");
            String phone = rs.getString("customer_phone");

            double amount = 2000;
            double gst = amount * 0.12;
            double total = amount + gst;

            String fileName = name.replace(" ", "_") + "_bill.pdf";

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();

            Font title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

            doc.add(new Paragraph("HOTEL MANAGEMENT SYSTEM", title));
            doc.add(new Paragraph(" "));

            try {
                Image logo = Image.getInstance("images/logo.png");
                logo.scaleToFit(100, 100);
                doc.add(logo);
            } catch (Exception e) {}

            doc.add(new Paragraph("-----------------------------"));
            doc.add(new Paragraph("Customer: " + name));
            doc.add(new Paragraph("Room: " + room));
            doc.add(new Paragraph("Phone: " + phone));
            doc.add(new Paragraph(" "));

            doc.add(new Paragraph("Amount: ₹" + amount));
            doc.add(new Paragraph("GST (12%): ₹" + gst));
            doc.add(new Paragraph("TOTAL: ₹" + total));

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Thank you for staying with us!"));

            doc.close();

            JOptionPane.showMessageDialog(null,
                    "Bill Generated: " + fileName);

            Desktop.getDesktop().open(new File(fileName));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}