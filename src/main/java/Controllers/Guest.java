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
            @FormDataParam("GuestName") String GuestName,
            @FormDataParam("GuestArrive") String GuestArrive,
            @FormDataParam("GuestLeave") String GuestLeave,
            @DefaultValue("false") @FormDataParam("VIP") String VIP) {
        try {
            if (GuestName == null || GuestArrive == null || GuestLeave == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Guest/new GuestID=");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Guest (GuestName, GuestArrive, GuestLeave, VIP) VALUES (?, ?, ?, ?)");
            ps.setString(1, GuestName);
            ps.setString(2, GuestArrive);
            ps.setString(3, GuestLeave);
            ps.setBoolean(4, VIP.toLowerCase().equals("true") || VIP.toLowerCase().equals("on"));
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
        System.out.println("Guest/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID, GuestName, GuestArrive, GuestLeave, VIP FROM Guest");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getInt(1));
                item.put("GuestName", results.getString(2));
                item.put("GuestArrive", results.getString(3));
                item.put("GuestLeave", results.getString(4));
                item.put("VIP", results.getBoolean(5));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGuest(@PathParam("id") Integer id) {
        System.out.println("Guest/get/" + id);

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID, GuestName, GuestArrive, GuestLeave, VIP FROM Guest WHERE GuestID = ?");
            ps.setInt(1, id);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getInt(1));
                item.put("GuestName", results.getString(2));
                item.put("GuestArrive", results.getString(3));
                item.put("GuestLeave", results.getString(4));
                item.put("VIP", results.getBoolean(5));
                return item.toString();
            } else {
                return "{\"error\": \"Unable to get guest with id " + id + ", please see server console for more info.\"}";
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get guest for some reason, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateGuest(
            @FormDataParam("GuestID") Integer GuestID,
            @FormDataParam("GuestName") String GuestName,
            @FormDataParam("GuestArrive") String GuestArrive,
            @FormDataParam("GuestLeave") String GuestLeave,
            @DefaultValue("false") @FormDataParam("VIP") String VIP) {
        try {
            if (GuestID == null || GuestName == null || GuestArrive == null || GuestLeave == null || VIP == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Guest/update GuestID=" + GuestID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Guest SET GuestName = ?, GuestArrive = ?, GuestLeave = ?, VIP = ? WHERE GuestID = ?");
            ps.setString(1, GuestName);
            ps.setString(2, GuestArrive);
            ps.setString(3, GuestLeave);
            ps.setBoolean(4, VIP.toLowerCase().equals("true") || VIP.toLowerCase().equals("on"));
            ps.setInt(5, GuestID);
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
            System.out.println("Guest/delete GuestID=" + GuestID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Guest WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

}
