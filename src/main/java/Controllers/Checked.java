package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Checked/")
public class Checked {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertChecked(
            @FormDataParam("CheckedID") Integer CheckedID, @FormDataParam("CheckedCheck") Boolean CheckedCheck){
        try {
            if (CheckedID == null || CheckedCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new CheckedID=" + CheckedID);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Checked (CheckedID, CheckedCheck) VALUES (?, ?)");
            ps.setInt(1, CheckedID);
            ps.setBoolean(2, CheckedCheck);
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
    public String listChecked() {
        System.out.println("Checked/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CheckedID, CheckedCheck FROM Checked");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("CheckedID", results.getInt(1));
                item.put("CheckedCheck", results.getBoolean(2));
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
    public String updateChecked(
            @FormDataParam("CheckedID") Integer CheckedID, @FormDataParam("CheckedCheck") Boolean CheckedCheck){
        try {
            if (CheckedID == null || CheckedCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update CheckedID" + CheckedID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Checked SET CheckedCheck = ? WHERE CheckedID = ?");
            ps.setBoolean(1, CheckedCheck);
            ps.setInt(2, CheckedID);
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
    public String deleteChecked(@FormDataParam("CheckedID") Integer CheckedID) {
        try {
            if (CheckedID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete CheckedID=" + CheckedID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Checked WHERE CheckedID = ?");
            ps.setInt(1, CheckedID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{CheckedID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getChecked(@PathParam("CheckedID") Integer CheckedID) throws Exception {
        if (CheckedID == null) {
            throw new Exception("Checked's 'CheckedID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + CheckedID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CheckedCheck FROM Checked WHERE CheckedID = ?");
            ps.setInt(1, CheckedID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("CheckedID", CheckedID);
                item.put("CheckedCheck", results.getBoolean(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}