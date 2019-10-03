import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Rooms {
    public static void insertRooms(int RoomID, String RoomName, String RoomLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (RoomID, RoomName, RoomLocation) VALUES (?, ?, ?)");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
            ps.executeUpdate();
            System.out.println("Record added to Rooms table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listRooms() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RoomID,RoomName, RoomLocation FROM Rooms");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int RoomID = results.getInt(1);
                String RoomName = results.getString(2);
                String RoomLocation = results.getString(3);
                System.out.println(RoomID + " " + RoomName + " " + RoomLocation);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateUser(int RoomID, String RoomName, String RoomLocation) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Rooms SET RoomName = ?, RoomLocation = ? WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.setString(2, RoomName);
            ps.setString(3, RoomLocation);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteRoom(int RoomID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Rooms WHERE RoomID = ?");
            ps.setInt(1, RoomID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}