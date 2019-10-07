package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OutOfOrder {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertOutOfOrder(
            @FormDataParam("OutOfOrderID") Integer OutOfOrderID, @FormDataParam("OutOfOrderCheck") Boolean OutOfOrderCheck){
        try {
            if (OutOfOrderID == null || OutOfOrderCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new OutOfOrderID=" + OutOfOrderID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (OutOfOrderID, OutOfOrderCheck) VALUES (?, ?)");
            ps.setInt(1, OutOfOrderID);
            ps.setBoolean(2, OutOfOrderCheck);
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
    public String listOutOfOrder() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT OutOfOrderID, OutOfOrderCheck FROM OutOfOrder");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("OutOfOrderID", results.getInt(1));
                item.put("OutOfOrderCheck", results.getBoolean(2));
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
    public String updateOutOfOrder(
            @FormDataParam("OutOfOrderID") Integer OutOfOrderID, @FormDataParam("OutOfOrderCheck") Boolean OutOfOrderCheck) {
        try {
            if (OutOfOrderID == null || OutOfOrderCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update OutOfOrderID=" + OutOfOrderID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE OutOfOrder SET OutOfOrderCheck = ? WHERE OutOfOrderID = ?");
            ps.setBoolean(1, OutOfOrderCheck);
            ps.setInt(2, OutOfOrderID);
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
    public String deleteOutOfOrder(@FormDataParam("OutOfOrderID") Integer OutOfOrderID) {
        try {
            if (OutOfOrderID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete OutOfOrderID=" + OutOfOrderID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM OutOfOrder WHERE OutOfOrderID = ?");
            ps.setInt(1, OutOfOrderID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{OutOfOrderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOutOfOrder(@PathParam("OutOfOrderID") Integer OutOfOrderID) throws Exception {
        if (OutOfOrderID == null) {
            throw new Exception("OutOfOrder's 'OutOfOrderID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + OutOfOrderID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT OutOfOrderCheck FROM OutOfOrder WHERE OutOfOrderID = ?");
            ps.setInt(1, OutOfOrderID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("OutOfOrderID", OutOfOrderID);
                item.put("OutOfOrderCheck", results.getBoolean(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}