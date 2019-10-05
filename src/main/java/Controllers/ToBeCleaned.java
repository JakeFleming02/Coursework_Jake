package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ToBeCleaned {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertToBeCleaned(
            @FormDataParam("ToBeCleanedID") Integer ToBeCleanedID){
            try {
                if (listToBeCleaned() == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/new ToBeCleanedID=" + ToBeCleanedID);
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (ToBeCleanedID) VALUES (?)");
                ps.setInt(1, ToBeCleanedID);
                ps.executeUpdate();
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
                PreparedStatement ps = Main.db.prepareStatement("SELECT ToBeCleanedID FROM ToBeCleaned");

                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("ToBeCleanedID", results.getInt(1));
                    list.add(item);
                }
                return list.toString();

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }

        }


        public static void updateToBeCleaned(int ToBeCleanedID) {
            try {

                PreparedStatement ps = Main.db.prepareStatement("UPDATE ToBeCleaned SET ToBeCleanedID = ?");
                ps.setInt(1, ToBeCleanedID);

            } catch (Exception e) {

                System.out.println(e.getMessage());

            }

        }

        public static void deleteToBeCleaned(int ToBeCleanedID) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM ToBeCleaned WHERE ToBeCleanedID = ?");
                ps.setInt(1, ToBeCleanedID);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
}