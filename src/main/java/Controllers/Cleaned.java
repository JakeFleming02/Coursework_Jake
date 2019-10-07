package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cleaned {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertCleaned(
            @FormDataParam("CleanedID") Integer CleanedID, @FormDataParam("CleanedCheck") Boolean CleanedCheck){
        try {
            if (CleanedID == null || CleanedCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new id=" + CleanedID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Cleaned (CleanedID, CleanedCheck) VALUES (?, ?)");
            ps.setInt(1, CleanedID);
            ps.setBoolean(2, CleanedCheck);
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
    public String listCleaned() {
        System.out.println("Cleaned/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CleanedID, CleanedID FROM Cleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("CleanedID", results.getInt(1));
                item.put("CleanedCheck", results.getBoolean(2));
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
    public String updateCleaned(
            @FormDataParam("CleanedID") Integer CleanedID, @FormDataParam("CleanedCheck") Boolean CleanedCheck) {
        try {
            if (CleanedID == null || CleanedCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update CleanedID=" + CleanedID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Cleaned SET CleanedCheck = ? WHERE CleanedID = ?");
            ps.setBoolean(1, CleanedCheck);
            ps.setInt(2, CleanedID);
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
    public String deleteCleaned(@FormDataParam("CleanedID") Integer CleanedID) {
        try {
            if (CleanedID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete CleanedID=" + CleanedID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Cleaned WHERE CleanedID = ?");
            ps.setInt(1, CleanedID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{CleanedID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCleaned(@PathParam("CleanedID") Integer CleanedID) throws Exception {
        if (CleanedID == null) {
            throw new Exception("Cleaned's 'CleanedID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + CleanedID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CleanedCheck FROM Cleaned WHERE CleanedID = ?");
            ps.setInt(1, CleanedID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("CleanedID", CleanedID);
                item.put("CleanedCheck", results.getBoolean(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}