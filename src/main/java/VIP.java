import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VIP {
    public static void listVIP() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ToBeCleanedID FROM ToBeCleaned");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean VIPID = updateVIP();
                System.out.println(VIPID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateVIP (boolean VIPID){
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE VIP SET VIPID = ?");
            ps.setBoolean(1, VIPID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }