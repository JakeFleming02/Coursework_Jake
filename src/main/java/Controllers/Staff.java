package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Staff/")
public class Staff {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertStaff(
            @FormDataParam("StaffID") Integer StaffID, @FormDataParam("StaffName") String StaffName) {
        try {
            if (StaffID == null || StaffName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/new StaffID=" + StaffID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Staff (StaffID, StaffName) VALUES (?, ?)");
            ps.setInt(1, StaffID);
            ps.setString(2, StaffName);
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
    public String listStaff() {
        System.out.println("Staff/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StaffID, StaffName FROM Staff");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StaffID", results.getInt(1));
                item.put("StaffName", results.getString(2));
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
    public String updateStaff(
            @FormDataParam("StaffID") Integer StaffID, @FormDataParam("StaffName") String StaffName) {
        try {
            if (StaffID == null || StaffName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/update StaffID=" + StaffID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Staff SET StaffName = ? WHERE StaffID = ?");
            ps.setString(1, StaffName);
            ps.setInt(2, StaffID);
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
    public String deleteStaff(@FormDataParam("StaffID") Integer StaffID) {
        try {
            if (StaffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/delete StaffID=" + StaffID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Staff WHERE StaffID = ?");
            ps.setInt(1, StaffID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{StaffID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStaff(@PathParam("StaffID") Integer StaffID) throws Exception {
        if (StaffID == null) {
            throw new Exception("Staff's 'StaffID' is missing in the HTTP request's URL.");
        }
        System.out.println("Staff/get/" + StaffID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StaffName FROM Staff WHERE StaffID = ?");
            ps.setInt(1, StaffID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("StaffID", StaffID);
                item.put("StaffName", results.getString(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}
