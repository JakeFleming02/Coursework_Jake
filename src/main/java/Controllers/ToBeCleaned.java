package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("ToBeCleaned/")
public class ToBeCleaned {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertToBeCleaned(
            @FormDataParam("ToBeCleanedID") Integer ToBeCleanedID, @FormDataParam("ToBeCleanedCheck") Boolean ToBeCleanedCheck){
            try {
                if (listToBeCleaned() == null || ToBeCleanedCheck == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/new ToBeCleanedID=" + ToBeCleanedID);
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO ToBeCleaned (ToBeCleanedID, ToBeCleanedID) VALUES (?, ?)");
                ps.setInt(1, ToBeCleanedID);
                ps.setBoolean(2, ToBeCleanedCheck);
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
        public String listToBeCleaned() {
            System.out.println("thing/list");
            JSONArray list = new JSONArray();
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT ToBeCleanedID, ToBeCleanedCheck FROM ToBeCleaned");

                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("ToBeCleanedID", results.getInt(1));
                    item.put("ToBeCleanedCheck", results.getBoolean(2));
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
    public String updateToBeCleaned(
            @FormDataParam("ToBeCleanedID") Integer ToBeCleanedID, @FormDataParam("ToBeCleanedCheck") Boolean ToBeCleanedCheck) {
            try {

                PreparedStatement ps = Main.db.prepareStatement("UPDATE ToBeCleaned SET ToBeCleanedCheck = ? WHERE ToBeCleanedID = ?");
                ps.setBoolean(1, ToBeCleanedCheck);
                ps.setInt(2, ToBeCleanedID);
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
    public String deleteToBeCleaned(@FormDataParam("ToBeCleanedID") Integer ToBeCleanedID) {
        try {
            if (ToBeCleanedID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete ToBeCleanedID=" + ToBeCleanedID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM ToBeCleaned WHERE ToBeCleanedID = ?");
                ps.setInt(1, ToBeCleanedID);
                ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
            }
        }

    @GET
    @Path("get/{ToBeCleanedID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getToBeCleaned(@PathParam("ToBeCleanedID") Integer ToBeCleanedID) throws Exception {
        if (ToBeCleanedID == null) {
            throw new Exception("ToBeCleaned's 'ToBeCleanedID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + ToBeCleanedID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ToBeCleanedCheck FROM ToBeCleaned WHERE ToBeCleanedID = ?");
            ps.setInt(1, ToBeCleanedID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("ToBeCleanedID", ToBeCleanedID);
                item.put("ToBeCleanedCheck", results.getBoolean(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}