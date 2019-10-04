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


    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listChecked() {
        System.out.println("Checked/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CheckedID FROM Checked");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("CheckedID", results.getInt(1));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
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