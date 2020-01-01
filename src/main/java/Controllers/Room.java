package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Room/")
public class Room {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertRoom(
            @FormDataParam("RoomID") Integer RoomID, @FormDataParam("RoomName") String RoomName, @FormDataParam("RoomLocation") String RoomLocation, @FormDataParam("Cleaned") Boolean Cleaned, @FormDataParam("Checked") Boolean Checked, @FormDataParam("OutOfOrder") Boolean OutOfOrder) {
        try {
            if (RoomID == null || RoomName == null || RoomLocation == null || Cleaned == null || Checked == null || OutOfOrder == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("room/new RoomID=" + RoomID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Room (RoomID, RoomName, RoomLocation, Cleaned, Checked, OutOfOrder) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
            ps.setBoolean(4, Cleaned);
            ps.setBoolean(5, Checked);
            ps.setBoolean(6, OutOfOrder);
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
    public String listRoom() {
        System.out.println("room/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RoomID, RoomName, RoomLocation, Cleaned, Checked, OutOfOrder FROM Room");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("RoomID", results.getInt(1));
                item.put("RoomName", results.getString(2));
                item.put("RoomLocation", results.getString(3));
                item.put("Cleaned", results.getString(4));
                item.put("Checked", results.getString(5));
                item.put("OutOfOrder", results.getString(6));
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
    public String getRoom(@PathParam("id") Integer id) {
        System.out.println("Room/get/" + id);

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RoomID, RoomName, RoomLocation, Cleaned, Checked, OutOfOrder FROM Room WHERE RoomID = ?");
            ps.setInt(1, id);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                JSONObject item = new JSONObject();
                item.put("RoomID", results.getInt(1));
                item.put("RoomName", results.getString(2));
                item.put("RoomLocation", results.getString(3));
                item.put("Cleaned", results.getString(4));
                item.put("Checked", results.getBoolean(5));
                item.put("OutOfOrder", results.getBoolean(6));
                return item.toString();
            } else {
                return "{\"error\": \"Unable to get room with id " + id + ", please see server console for more info.\"}";
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
    public String updateRoom(
            @FormDataParam("RoomID") Integer RoomID, @FormDataParam("RoomName") String RoomName, @FormDataParam("RoomLocation") String RoomLocation, @FormDataParam("Cleaned") Boolean Cleaned, @FormDataParam("Checked") Boolean Checked, @FormDataParam("ToBeCleaned") Boolean ToBeCleaned, @FormDataParam("OutOfOrder") Boolean OutOfOrder) {
        try {
            if (RoomID == null || RoomName == null || RoomLocation == null || Cleaned == null || Checked == null || OutOfOrder == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Room/update RoomID=" + RoomID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Room SET RoomName = ?, RoomLocation = ?, Cleaned = ?, Checked = ?, OutOfOrder = ? WHERE RoomID = ?");
            ps.setString(1, RoomName);
            ps.setString(2, RoomLocation);
            ps.setBoolean(3, Cleaned);
            ps.setBoolean(4, Checked);
            ps.setBoolean(5, OutOfOrder);
            ps.setInt(6, RoomID);
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
    public String deleteRoom(@FormDataParam("RoomID") Integer RoomID) {
        try {
            if (RoomID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Room/delete RoomID=" + RoomID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Room WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}
