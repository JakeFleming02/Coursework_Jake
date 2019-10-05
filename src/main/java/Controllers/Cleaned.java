package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cleaned {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertCleaned(
            @FormDataParam("CleanedID") Integer CleanedID){
        try {
            if (CleanedID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new id=" + CleanedID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Cleaned (CleanedID) VALUES (?)");
            ps.setInt(1, CleanedID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
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