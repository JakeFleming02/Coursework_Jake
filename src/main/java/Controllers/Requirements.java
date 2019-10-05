package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Requirements {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertRequirements(
            @FormDataParam("RequirementsID") Integer RequirementsID, @FormDataParam("RequirementsName") String RequirementsName){
        try {
            if (RequirementsID == null || RequirementsName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new RequirementsID=" + RequirementsID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (int RequirementsID, String RequirementsName) VALUES (?, ?)");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
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