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
            @FormDataParam("StaffName") String StaffName) {
        try {
            if (StaffName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/new StaffID=");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Staff (StaffName) VALUES (?)");
            ps.setString(1, StaffName);
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

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStaff(@PathParam("id") Integer id) {
        System.out.println("Staff/get/" + id);

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT StaffID, StaffName FROM Staff WHERE StaffID = ?");
            ps.setInt(1, id);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                JSONObject item = new JSONObject();
                item.put("StaffID", results.getInt(1));
                item.put("StaffName", results.getString(2));
                return item.toString();
            } else {
                return "{\"error\": \"Unable to get staff with id " + id + ", please see server console for more info.\"}";
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get guest for some reason, please see server console for more info.\"}";
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
}
