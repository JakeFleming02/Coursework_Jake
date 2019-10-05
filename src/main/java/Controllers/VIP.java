package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VIP {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertVIP(
            @FormDataParam("VIPID") Integer VIPID){
        try {
            if (VIPID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new VIPID=" + VIPID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (VIPID) VALUES (?)");
            ps.setInt(1, VIPID);
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
    public String listVIP() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT VIPID FROM VIP");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("VIPID", results.getInt(1));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    public static void updateVIP(int VIPID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE VIP SET VIPID = ?");
            ps.setInt(1, VIPID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteVIP(int VIPID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM VIP WHERE VIPID = ?");
            ps.setInt(1, VIPID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}