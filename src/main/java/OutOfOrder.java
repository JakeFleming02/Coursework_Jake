import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OutOfOrder {
    public static void insertOutOfOrder(int OutOfOrderID, String OutOfOrderName, String OutOfOrderLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (OutOfOrderID) VALUES (?)");
            ps.setInt(1, OutOfOrderID);
            ps.executeUpdate();
            System.out.println("Record added to OutOfOrder table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listOutOfOrder() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT OutOfOrderID FROM OutOfOrder");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean OutOfOrderID = OutOfOrder();
                System.out.println(OutOfOrderID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateOutOfOrder(boolean OutOfOrderID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE OutOfOrder SET OutOfOrderID = ?");
            ps.setBoolean(1, OutOfOrderID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }
    public static void deleteOutOfOrder(int OutOfOrderID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM OutOfOrder WHERE OutOfOrderID = ?");
            ps.setInt(1, OutOfOrderID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}