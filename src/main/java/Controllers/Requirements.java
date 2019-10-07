package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Requirements/")
public class Requirements {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertRequirements(
            @FormDataParam("RequirementsID") Integer RequirementsID, @FormDataParam("RequirementsName") String RequirementsName){
        try {
            if (RequirementsID == null || RequirementsName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new RequirementsID=" + RequirementsID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (int RequirementsID, String RequirementsName) VALUES (?, ?)");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
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
    public String listRequirements() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RequirementsID,RequirementsName FROM Requirements");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("RequirementsID", results.getInt(1));
                item.put("RequirementsName", results.getString(2));
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
    public String updateRequirements(
            @FormDataParam("RequirementsID") Integer RequirementsID, @FormDataParam("RequirementsName") String RequirementsName) {
        try {
            if (RequirementsID == null || RequirementsName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update RequirementsID=" + RequirementsID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Requirements SET RequirementsName = ? WHERE RequirementsID = ?");
            ps.setString(1, RequirementsName);
            ps.setInt(2, RequirementsID);
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
    public String deleteRequirements(@FormDataParam("RequirementsID") Integer RequirementsID) {
        try {
            if (RequirementsID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete RequirementsID=" + RequirementsID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Requirements WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{RequirementsID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVIP(@PathParam("RequirementsID") Integer RequirementsID) throws Exception {
        if (RequirementsID == null) {
            throw new Exception("Requirements's 'RequirementsID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + RequirementsID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RequirementsName FROM Requirements WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("RequirementsID", RequirementsID);
                item.put("RequirementsCheck", results.getString(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}