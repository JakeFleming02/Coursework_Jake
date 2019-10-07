package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Rooms/")
public class Rooms {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertRoom(
            @FormDataParam("RoomID") Integer RoomID, @FormDataParam("RoomName") String RoomName, @FormDataParam("RoomLocation") String RoomLocation) {
        try {
            if (RoomID == null || RoomName == null || RoomLocation == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new RoomID=" + RoomID);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Rooms (RoomID, RoomName, RoomLocation) VALUES (?, ?, ?)");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
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
    public String listRooms() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RoomID,RoomName, RoomLocation FROM Rooms");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("RoomID", results.getInt(1));
                item.put("RoomName", results.getString(2));
                item.put("RoomLocation", results.getString(3));
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
    public String updateRooms(
            @FormDataParam("RoomID") Integer RoomID, @FormDataParam("RoomName") String RoomName, @FormDataParam("RoomLocation") String RoomLocation) {
        try {
            if (RoomID == null || RoomName == null || RoomLocation == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update RoomID=" + RoomID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Rooms SET RoomName = ?, RoomLocation = ? WHERE RoomID = ?");
            ps.setString(1, RoomName);
            ps.setString(2, RoomLocation);
            ps.setInt(3, RoomID);
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
    public String deleteRooms(@FormDataParam("RoomID") Integer RoomID) {
        try {
            if (RoomID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete RoomID=" + RoomID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Rooms WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{RoomID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVIP(@PathParam("RoomID") Integer RoomID) throws Exception {
        if (RoomID == null) {
            throw new Exception("Room's 'RoomID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + RoomID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RoomName, RoomLocation FROM Rooms WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("RoomID", RoomID);
                item.put("RoomName", results.getString(1));
                item.put("RoomLocation", results.getString(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}