package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Checked {
    public static void insertChecked(int CheckedID) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (CheckedID) VALUES (?)");
            ps.setInt(1, CheckedID);
            ps.executeUpdate();
            System.out.println("Record added to Controllers.Checked table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listChecked() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CheckedID FROM Checked");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int CheckedID = results.getInt(1);
                System.out.println(CheckedID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateChecked (int CheckedID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Checked SET CheckedID = ?");
            ps.setInt(1, CheckedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteChecked(int CheckedID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Checked WHERE CheckedID = ?");
            ps.setInt(1, CheckedID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}