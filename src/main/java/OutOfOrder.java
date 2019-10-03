import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OutOfOrder {
    public static void listOutOfOrder() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT OutOfOrderID FROM Cleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean OutOfOrderID = OutOfOrder();
                System.out.println(OutOfOrderID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateCleaned (boolean OutOfOrderID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE OutOfOrder SET OutOfOrderID = ?");
            ps.setBoolean(1, OutOfOrderID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }