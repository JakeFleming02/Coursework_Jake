import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VIP {
    public static void insertVIP(int VIPID, String VIPName, String VIPLocation) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Requirements (VIPID) VALUES (?)");
            ps.setInt(1, VIPID);
            ps.executeUpdate();
            System.out.println("Record added to VIP table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong. Please contact the administrator with the error code WC-WA.");
        }
    }

    public static void listVIP() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT VIPID FROM VIP");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                boolean VIPID = updateVIP();
                System.out.println(VIPID);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }


    public static void updateVIP(boolean VIPID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE VIP SET VIPID = ?");
            ps.setBoolean(1, VIPID);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void deleteVIP(int VIPID) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM VIP WHERE VIPID = ?");
            ps.setInt(1, VIPID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}