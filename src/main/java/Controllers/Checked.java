package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Checked {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertChecked(
            @FormDataParam("CheckedID") Integer CheckedID){
        try {
            if (CheckedID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new CheckedID=" + CheckedID);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (CheckedID) VALUES (?)");
            ps.setInt(1, CheckedID);
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