package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author Punnajee
 */
public class DB {


    static Connection c = null;


    //   local
    public static final String DBPATH = "jdbc:mysql://localhost:3306/atd?zeroDateTimeBehavior=convertToNull";
    public static final String USER = "root";
    public static final String PASS = "root";


    public static Connection getConnection() throws Exception {
        if (c == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(DBPATH, USER, PASS);

        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = DB.getConnection().createStatement().executeUpdate(sql);
        System.out.println("===============\n" + sql + "\n====================");
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = DB.getConnection().createStatement().executeQuery(sql);
        System.out.println("===============\n" + sql + "\n====================");
        return executeQuery;
    }


}
