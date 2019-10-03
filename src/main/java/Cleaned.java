import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cleaned {
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


    public static void updateCleaned (boolean CleanedID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Cleaned SET CleanedID = ?");
            ps.setBoolean(1, CleanedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }