package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("VIP/")
public class VIP {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertVIP(
            @FormDataParam("VIPID") Integer VIPID, @FormDataParam("VIPCheck") Boolean VIPCheck){
        try {
            if (VIPID == null || VIPCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/new VIPID=" + VIPID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO VIP (VIPID, VIPCheck) VALUES (?, ?)");
            ps.setInt(1, VIPID);
            ps.setBoolean(2, VIPCheck);
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
    public String listVIP() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT VIPID, VIPName FROM VIP");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("VIPID", results.getInt(1));
                item.put("VIPName", results.getString(2));
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
    public String updateVIP(
            @FormDataParam("VIPID") Integer VIPID, @FormDataParam("VIPCheck") Boolean VIPCheck) {
        try {
            if (VIPID == null || VIPCheck == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update VIPID=" + VIPID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE VIP SET VIPCheck = ? WHERE VIPID = ?");
            ps.setInt(1, VIPID);
            ps.setBoolean(2, VIPCheck);
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
    public String deleteVIP(@FormDataParam("VIPID") Integer VIPID) {
        try {
            if (VIPID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete VIPID=" + VIPID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM VIP WHERE VIPID = ?");
            ps.setInt(1, VIPID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{VIPID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVIP(@PathParam("VIPID") Integer VIPID) throws Exception {
        if (VIPID == null) {
            throw new Exception("VIP's 'VIPID' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + VIPID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT VIPCheck FROM VIP WHERE VIPID = ?");
            ps.setInt(1, VIPID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("VIPID", VIPID);
                item.put("VIPCheck", results.getBoolean(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}