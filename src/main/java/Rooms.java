import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Rooms {
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


    public static void updateUser (int RoomID, String RoomName, String RoomLocation){
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