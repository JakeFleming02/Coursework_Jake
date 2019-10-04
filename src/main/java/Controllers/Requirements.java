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

public class Requirements {
    public static void insertRequirements(int RequirementsID, String RequirementsName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (int RequirementsID, String RequirementsName) VALUES (?, ?)");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
            ps.executeUpdate();
            System.out.println("Record added to Controllers.Requirements table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRequirements() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RequirementsID,RequirementsName FROM Requirements");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("RequirementsID", results.getInt(1));
                item.put("RequirementsName", results.getString(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    public static void updateUser(int RequirementsID, String RequirementsName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Requirements SET RequirementsName = ? WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteRequirements(int RequirementsID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Requirements WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}