package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Guest {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertGuest(
            @FormDataParam("GuestID") Integer GuestID, @FormDataParam("GuestName") String GuestName) {
        try {
            if (GuestID == null || GuestName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new GuestID=" + GuestID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Guest (int GuestID, String GuestName) VALUES (?, ?)");
            ps.setInt(1, GuestID);
            ps.setString(2, GuestName);
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
    public String listGuest() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID,GuestName FROM Guest");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getInt(1));
                item.put("GuestName", results.getString(2));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
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
