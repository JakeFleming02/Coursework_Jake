import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Checked {
    public static void listChecked() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CheckedID FROM Checked");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean CheckedID = updateChecked();
                System.out.println(CheckedID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateChecked (boolean CleanedID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Checked SET CheckedID = ?");
            ps.setBoolean(1, CheckedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }