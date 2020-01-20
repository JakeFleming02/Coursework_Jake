package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Reservation/")
public class Reservation {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertReservation(@FormDataParam("GuestID") Integer GuestID, @FormDataParam("RoomID") Integer RoomID, @FormDataParam("StaffID") Integer StaffID) {
        try {
            if (GuestID == null || RoomID == null || StaffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/new GuestID");
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Reservation (GuestID, RoomID, StaffID) VALUES (?, ?, ?)");
            ps.setInt(1, GuestID);
            ps.setInt(2, RoomID);
            ps.setInt(3, StaffID);
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
    public String listReservation() {
        System.out.println("Reservation/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID, RoomID, StaffID FROM Reservation");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getInt(1));
                item.put("RoomID", results.getInt(2));
                item.put("StaffID", results.getInt(3));
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
    public String getReservation(@PathParam("id") Integer id) {
        System.out.println("Reservation/get/" + id);

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT GuestID, RoomID, StaffID FROM Reservation WHERE GuestID = ?");
            ps.setInt(1, id);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                JSONObject item = new JSONObject();
                item.put("GuestID", results.getString(1));
                item.put("RoomID", results.getString(2));
                item.put("StaffID", results.getString(3));
                return item.toString();
            } else {
                return "{\"error\": \"Unable to get reservation with id " + id + ", please see server console for more info.\"}";
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
    public String updateReservation(
            @FormDataParam("GuestID") Integer GuestID, @FormDataParam("RoomID") Integer RoomID, @FormDataParam("StaffID") Integer StaffID) {
        try {
            if (GuestID == null || RoomID == null || StaffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/update GuestID=");

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Reservation SET GuestID = ?, RoomID = ?, StaffID = ?");
            ps.setInt(1, GuestID);
            ps.setInt(2, RoomID);
            ps.setInt(3, StaffID);
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
    public String deleteReservation(@FormDataParam("GuestID") Integer GuestID) {
        try {
            if (GuestID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/delete GuestID=" + GuestID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Reservation WHERE GuestID = ?");
            ps.setInt(1, GuestID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

}