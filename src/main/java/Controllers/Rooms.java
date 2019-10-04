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

public class Rooms {
    public static void insertRooms(int RoomID, String RoomName, String RoomLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (RoomID, RoomName, RoomLocation) VALUES (?, ?, ?)");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
            ps.executeUpdate();
            System.out.println("Record added to Controllers.Rooms table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
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


    public static void updateUser(int RoomID, String RoomName, String RoomLocation) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Rooms SET RoomName = ?, RoomLocation = ? WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteRoom(int RoomID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Rooms WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}