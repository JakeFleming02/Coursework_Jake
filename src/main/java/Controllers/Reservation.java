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
    public String insertReservation(
            @FormDataParam("ReservationID") Integer ReservationID, @FormDataParam("GuestID") Integer GuestID, @FormDataParam("RoomID") Integer RoomID, @FormDataParam("StaffID") Integer StaffID) {
        try {
            if (ReservationID == null || GuestID == null || RoomID == null || StaffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/new ReservationID=" + ReservationID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Reservation (ReservationID, GuestID, RoomID, StaffID) VALUES (?, ?, ?, ?)");
            ps.setInt(1, ReservationID);
            ps.setInt(2, GuestID);
            ps.setInt(3, RoomID);
            ps.setInt(4, StaffID);
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
            PreparedStatement ps = Main.db.prepareStatement("SELECT ReservationID, GuestID, RoomID, StaffID FROM Reservation");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("ReservationID", results.getInt(1));
                item.put("GuestID", results.getInt(2));
                item.put("RoomID", results.getInt(3));
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
            PreparedStatement ps = Main.db.prepareStatement("SELECT ReservationID, GuestID, RoomID, StaffID FROM Reservation WHERE ReservationID = ?");
            ps.setInt(1, id);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                JSONObject item = new JSONObject();
                item.put("ReservationID", results.getInt(1));
                item.put("GuestID", results.getString(2));
                item.put("RoomID", results.getString(3));
                item.put("StaffID", results.getString(4));
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
            @FormDataParam("ReservationID") Integer ReservationID, @FormDataParam("GuestID") Integer GuestID, @FormDataParam("RoomID") Integer RoomID, @FormDataParam("StaffID") Integer StaffID) {
        try {
            if (ReservationID == null || GuestID == null || RoomID == null || StaffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/update ReservationID=" + ReservationID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Reservation SET GuestID = ?, RoomID = ?, StaffID = ? WHERE ReservationID = ?");
            ps.setInt(1, StaffID);
            ps.setInt(2, GuestID);
            ps.setInt(3, RoomID);
            ps.setInt(4, ReservationID);
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
    public String deleteReservation(@FormDataParam("ReservationID") Integer ReservationID) {
        try {
            if (ReservationID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Reservation/delete ReservationID=" + ReservationID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Reservation WHERE ReservationID = ?");
            ps.setInt(1, ReservationID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }
}