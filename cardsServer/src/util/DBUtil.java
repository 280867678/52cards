package util;

import java.sql.*;

public class DBUtil {
    private static String url = "jdbc:mysql://localhost:3306/cards?characterEncoding=utf8";
    private static String userName = "root";
    private static String password = "";


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    	try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Connection conn = null;
        conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void realeaseAll(ResultSet rs, PreparedStatement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
                st = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(conn);
    }
}