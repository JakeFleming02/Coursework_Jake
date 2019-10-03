import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Requirements {
    public static void insertRequirements(int RequirementsID, String RequirementsName){
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (int RequirementsID, String RequirementsName) VALUES (?, ?)");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
            ps.executeUpdate();
            System.out.println("Record added to Rooms table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listRequirements() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT RequirementsID,RequirementsName FROM Requirements");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int RequirementsID = results.getInt(1);
                String RequirementsName = results.getString(2);
                System.out.println(RequirementsID + " " + RequirementsName);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateUser (int RequirementsID, String RequirementsName){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Requirements SET RequirementsName = ? WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ps.setString(2, RequirementsName);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteRequirements (int RequirementsID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Requirements WHERE RequirementsID = ?");
            ps.setInt(1, RequirementsID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
