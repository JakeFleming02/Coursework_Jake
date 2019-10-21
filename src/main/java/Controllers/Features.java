package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Features/")
public class Features {
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertFeature(
            @FormDataParam("FeatureID") Integer FeatureID, @FormDataParam("FeatureName") String FeatureName) {
        try {
            if (FeatureID == null || FeatureName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Feature/new FeatureID=" + FeatureID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Features (FeatureID, FeatureName) VALUES (?, ?)");
            ps.setInt(1, FeatureID);
            ps.setString(2, FeatureName);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listFeature() {
        System.out.println("Feature/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT FeatureID, FeatureName FROM Features");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("FeatureID", results.getInt(1));
                item.put("FeatureName", results.getString(2));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }


    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateFeature(
            @FormDataParam("FeatureID") Integer FeatureID, @FormDataParam("FeatureName") String FeatureName) {
        try {
            if (FeatureID == null || FeatureName == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Feature/update FeatureID=" + FeatureID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Features SET FeatureName = ? WHERE FeatureID = ?");
            ps.setString(1, FeatureName);
            ps.setInt(2, FeatureID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteFeature(@FormDataParam("FeatureID") Integer FeatureID) {
        try {
            if (FeatureID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Feature/delete FeatureID=" + FeatureID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Features WHERE FeatureID = ?");
            ps.setInt(1, FeatureID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{FeatureID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFeature(@PathParam("FeatureID") Integer FeatureID) throws Exception {
        if (FeatureID == null) {
            throw new Exception("Feature's 'FeatureID' is missing in the HTTP request's URL.");
        }
        System.out.println("Feature/get/" + FeatureID);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT FeatureName FROM Features WHERE FeatureID = ?");
            ps.setInt(1, FeatureID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("FeatureID", FeatureID);
                item.put("FeatureName", results.getString(1));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}
