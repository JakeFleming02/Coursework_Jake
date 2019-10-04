package Controllers;

import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cleaned {
    public static void insertCleaned(int CleanedID, String CleanedName, String CleanedLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (CleanedID) VALUES (?)");
            ps.setInt(1, CleanedID);
            ps.executeUpdate();
            System.out.println("Record added to Controllers.Cleaned table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listCleaned() {
        System.out.println("Cleaned/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CleanedID FROM Cleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("CleanedID", results.getInt(1));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    public static void updateCleaned(int CleanedID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Cleaned SET CleanedID = ?");
            ps.setInt(1, CleanedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteCleaned(int CleanedID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Cleaned WHERE CleanedID = ?");
            ps.setInt(1, CleanedID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}