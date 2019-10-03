import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cleaned {
    public static void insertCleaned(int CleanedID, String CleanedName, String CleanedLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (CleanedID) VALUES (?)");
            ps.setInt(1, CleanedID);
            ps.executeUpdate();
            System.out.println("Record added to Cleaned table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listCleaned() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CleanedID FROM Cleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean CleanedID = updateCleaned();
                System.out.println(CleanedID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateCleaned(boolean CleanedID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Cleaned SET CleanedID = ?");
            ps.setBoolean(1, CleanedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteCleaned(int CleanedID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Cleaned WHERE CleanedID = ?");
            ps.setInt(1, CleanedID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}