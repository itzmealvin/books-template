import java.sql.*;

public class ConnectionSQL {
    static final String connectionUrl =
            "jdbc:sqlserver://localhost;databaseName=books;user=sa;password=;encrypt=true;trustServerCertificate=true;";

    public static void closeConnect(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }

    public static void query(String inputQuery) {
        Connection con = null;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.prepareStatement(inputQuery);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Query return nothing!");
            } else {
                while (rs.next()) {
                    for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                        System.out.println(" " + rs.getMetaData().getColumnName(i) + " = " + rs.getObject(i));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnect(con);
        }
    }

    public static boolean update(int authorID, String firstName, String lastName) {
        Connection con = null;
        PreparedStatement stmt;
        int rs;
        boolean isUpdated = false;
        try {
            con = DriverManager.getConnection(connectionUrl);
            con.setAutoCommit(false);
            String updateString =
                    """
                            INSERT INTO authors
                            VALUES (?,?,?)
                                    """;
            stmt = con.prepareStatement(updateString);
            stmt.setInt(1, authorID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            rs = stmt.executeUpdate();
            if (rs > 0) {
                isUpdated = true;
            }
            con.commit();
        } catch (SQLException e) {
            return isUpdated;
        } finally {
            closeConnect(con);
        }
        return isUpdated;
    }


}
