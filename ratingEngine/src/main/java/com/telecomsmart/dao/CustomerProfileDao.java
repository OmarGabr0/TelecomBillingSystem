
import com.telecomsmart.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerProfileDao {

    private Connection con;

    private Connection connect() {
        if (con == null) {
            try {
                con = DatabaseManager.getConnection();
            } catch (SQLException e) {
                System.out.println("Error connecting to the database");
                return null;
            }
        }
        return con;
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error disconnecting from the database");
            }
            con = null;
        }
    }

    public Map<String,CustomerProfile> getCustomerProfiles() {
        Map<String,CustomerProfile> customerProfiles = new HashMap<>();
        String query = "SELECT msisdn, credit_limit,ror_usage, balance, rated_plan, free_units_remaining FROM customer_profile";
    }
/*
    public boolean createCustomerProfile(CustomerProfile customerProfile) {
        Connection con = connect();
        if (con == null) {
            return false;
        }   
        try {
            String query = "INSERT INTO customer_profile (msisdn, credit_limit, balance, rated_plan, free_units_remaining) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, customerProfile.getMsisdn());
            ps.setInt(2, customerProfile.getCreditLimit());
            ps.setBigDecimal(3, customerProfile.getBalance());
            ps.setInt(4, customerProfile.getRatedPlan());
            ps.setBigInteger(5, customerProfile.getFreeUnitsRemaining());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating customer profile");
            return false;
        } finally {
            disconnect();
        }
    }
}
**/ 

