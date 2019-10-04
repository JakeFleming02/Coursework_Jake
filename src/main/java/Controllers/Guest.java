package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Guest {
    public static void insertGuest(int GuestID, String GuestName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Guest (int GuestID, String GuestName) VALUES (?, ?)");
            ps.setInt(1, GuestID);
            ps.setString(2, GuestName);
            ps.executeUpdate();
            System.out.println("Record added to Guests table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listGuest() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID,GuestName FROM Guest");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int GuestID = results.getInt(1);
                String GuestName = results.getString(2);
                System.out.println(GuestID + " " + GuestName);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateUser(int GuestID, String GuestName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Guest SET GuestName = ? WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.setString(2, GuestName);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteRequirements(int GuestID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Guest WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
