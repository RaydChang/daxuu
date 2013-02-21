/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.Data;

import java.sql.*;

/**
 *
 * @author Administrator
 * @date 2010/4/1, 上午 10:30:27
 */
public class test {

    public static void getOracl() {

        // Create a variable for the connection string.士大夫
        String className = "oracle.jdbc.driver.OracleDriver";
        String user = "am";
        String pwd = "amadmin0824";
        String url = "jdbc:oracle:thin:@172.18.60.24:1521:eps";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            Class.forName(className);
            con = DriverManager.getConnection(url, user, pwd);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT * FROM TEST";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + "--" + rs.getString(2) + "--" + rs.getString(3));
            }
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void getSql() {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://172.18.60.42:1433;databaseName=pfs_test;integratedSecurity=true;";
        String user = "pubuser";
        String pwd = "pfs";
        String url = "jdbc:sqlserver://172.18.60.42:1433;databaseName=pfs_test;";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, pwd);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT TOP 10 * FROM pfs_userm";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + "--" + rs.getString(2));
            }
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void getSqlite() {

        // Create a variable for the connection string.
        String className = "org.sqlite.JDBC";
        String user = "am";
        String pwd = "amadmin0824";
        //jdbc:sqlite:/path
        String url = "jdbc:sqlite://D://Apps//JavaWorkspace//PWF//PWF3//db/LOG.sl";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            Class.forName(className);
            // con = DriverManager.getConnection(url,user,pwd);
            con = DriverManager.getConnection(url);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT * FROM PM_LOG";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + "--" + rs.getString(2) + "--" + rs.getString(3));
            }

        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        String user = "pubuser";
        String pwd = "pfs";
        String url = "jdbc:microsoft:sqlserver://172.18.60.42:1433;DatabaseName=pfs_test";
        Connection conn = null;
        conn = DriverManager.getConnection(url, user, pwd);
        return conn;
    }

    public static void queryDemo1() {
        String sql = "SELECT top 10 * FROM pfs_userm";
        System.out.println("SQL:" + sql);
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // Class.forName("myDriverClassName");
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            conn = getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("ID:" + rs.getString(1));
                System.out.println("Account:" + rs.getString(2));
//                System.out.println("Age:" + rs.getInt("age"));
//                System.out.println("Phone:" + rs.getString("Phone"));
                System.out.println("===============================\n");

            }
        } catch (SQLException e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }


        }


    }
}

