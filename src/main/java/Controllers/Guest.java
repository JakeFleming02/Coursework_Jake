package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Guest/")
public class Guest {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertGuest(
            @FormDataParam("GuestID") Integer GuestID, @FormDataParam("GuestName") String GuestName, @FormDataParam("GuestArrive") String GuestArrive, @FormDataParam("GuestLeave") String GuestLeave, @FormDataParam("Requirements") String Requirements) {
        try {
            if (GuestID == null || GuestName == null || GuestArrive == null || GuestLeave == null || Requirements == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new GuestID=" + GuestID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Guest (GuestID, GuestName, GuestArrive, GuestLeave, Requirements) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, GuestID);
            ps.setString(2, GuestName);
            ps.setString(3, GuestArrive);
            ps.setString(4, GuestLeave);
            ps.setString(5, Requirements);
            ps.execute();
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
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID, GuestName, GuestArrive, GuestLeave, Requirements FROM Guest");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getInt(1));
                item.put("GuestName", results.getString(2));
                item.put("GuestArrive", results.getString(3));
                item.put("GuestLeave", results.getString(4));
                item.put("Requirements", results.getString(5));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateGuest(
            @FormDataParam("GuestID") Integer GuestID, @FormDataParam("GuestName") String GuestName, @FormDataParam("GuestArrive") String GuestArrive, @FormDataParam("GuestLeave") String GuestLeave, @FormDataParam("Requirements") String Requirements) {
        try {
            if (GuestID == null || GuestName == null || GuestArrive == null || GuestLeave == null || Requirements == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update GuestID=" + GuestID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Guest SET GuestName = ?, GuestArrive = ?, GuestLeave = ?, Requirements = ? WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.setString(2, GuestName);
            ps.setString(3, GuestArrive);
            ps.setString(4, GuestLeave);
            ps.setString(5, Requirements);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteGuest(@FormDataParam("GuestID") Integer GuestID) {
        try {
            if (GuestID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete GuestID=" + GuestID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Guest WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{GuestID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGuest(@PathParam("GuestID") Integer GuestID) throws Exception {
        if (GuestID == null) {
            throw new Exception("Guest's 'GuestID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + GuestID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestName FROM Guest WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("GuestID", GuestID);
                item.put("GuestName", results.getString(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}