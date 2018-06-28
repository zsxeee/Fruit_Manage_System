package project.FMS.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

/**
 * @description Database Access Object
 * @author zsxeee
 */
class DAO {
    private Connection conn;
    private Statement stmt;
    private String driver;
    private String url;
    private String user;
    private String pass;



    DAO() throws Throwable {
        try{
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream("database.properties");
            prop.load(in);
            in.close();

            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            pass = prop.getProperty("pass");
        }catch (Throwable e){
            throw new Throwable("Database Properties Open Error!",e);
        }
    }

    private Boolean conn(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            stmt = conn.createStatement();
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void disconn(){
        try {
            stmt.close();
            conn.close();
        }catch (Throwable e){
            System.out.println("Database Disconnect Error!");
            e.printStackTrace();
        }

    }

    List<Object> select(String sql){
        List<Object> list = new ArrayList<>();
        if(this.conn()){
            try{
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();

                while(rs.next()){
                    Map<String,Object> rowData = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(md.getColumnName(i), rs.getObject(i));
                    }
                    list.add(rowData);
                }
                rs.close();
            }catch (Throwable e){
                System.out.println("Database Query Error!");
                e.printStackTrace();
                return new ArrayList<>();
            }finally {
                this.disconn();
            }
        }else {
            return new ArrayList<>();
        }
        return list;
    }

    int operate(String sql){
        if(this.conn()){
            try{
                return stmt.executeUpdate(sql);
            }catch (Throwable e){
                System.out.println("Database Operate Error!");
                e.printStackTrace();
                return -1;
            }finally {
                this.disconn();
            }
        }else {
            return -1;
        }
    }

    PreparedStatement preSet(String sql) throws Throwable {
        if(this.conn()){
            try{
                return conn.prepareStatement(sql);
            }catch (Throwable e){
                this.disconn();
                throw new Throwable("Database preOperate Error!",e);
            }
        }else {
            return null;
        }
    }

    int runSet(PreparedStatement set){
        if(this.conn()){
            try{
                return set.executeUpdate();
            }catch (Throwable e){
                System.out.println("Database Operate Error!");
                e.printStackTrace();
                return -1;
            }finally {
                this.disconn();
            }
        }else {
            return -1;
        }
    }
}
