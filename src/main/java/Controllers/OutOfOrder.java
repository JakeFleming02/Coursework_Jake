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

public class OutOfOrder {
    public static void insertOutOfOrder(int OutOfOrderID, String OutOfOrderName, String OutOfOrderLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (OutOfOrderID) VALUES (?)");
            ps.setInt(1, OutOfOrderID);
            ps.executeUpdate();
            System.out.println("Record added to Controllers.OutOfOrder table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listOutOfOrder() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT OutOfOrderID FROM OutOfOrder");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("OutOfOrderID", results.getInt(1));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    public static void updateOutOfOrder(int OutOfOrderID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE OutOfOrder SET OutOfOrderID = ?");
            ps.setInt(1, OutOfOrderID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
    public static void deleteOutOfOrder(int OutOfOrderID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM OutOfOrder WHERE OutOfOrderID = ?");
            ps.setInt(1, OutOfOrderID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}