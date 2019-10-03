import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ToBeCleaned {
    public static void listToBeCleaned() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ToBeCleanedID FROM ToBeCleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean ToBeCleanedID = updateToBeCleaned();
                System.out.println(ToBeCleanedID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateToBeCleaned (boolean ToBeCleanedID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE ToBeCleaned SET ToBeCleanedID = ?");
            ps.setBoolean(1, ToBeCleanedID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }