/**
 * @program: Create_database
 * @description: 对数据库进行增删改查
 * @author: Mr.Wang
 * @create: 2021-08-04 16:53
 **/

import java.io.*;
import java.sql.*;


import java.util.*;

public class mysql {

    private static String url_unic = "?useUnicode=true&characterEncoding=utf8";
    private static String url_http = "jdbc:mysql://localhost:";
    public static Connection getConnection(String port,String dataBase,String user,String pwd){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url_total = url_http+port+"/"+dataBase+url_unic;
			System.out.println(url_total);
			conn = DriverManager.getConnection(url_total,user,pwd);
			System.out.println("connection success!");
		} catch(Exception e){
			System.out.println("connection fail!");
			e.printStackTrace();
			System.out.println("e.getMessage="+e.getMessage());
           System.out.println("e="+e);
		}
		return conn;
	}
	public static void closeCon(Connection con){
        if(con != null){
        	try{
            con.close();
        	    }catch(Exception e){
        	    	e.printStackTrace();
            	}
        }
    }
    /*
    * 数据插入到alert_field表中
    * */
   public static boolean insertIntoTable(String port, String dataBase, String user, String pwd, Map<String,String> map){
		Connection conn = getConnection(port,dataBase,user,pwd);

        int columnShow = 0;
        if(map.get("columnShow").trim().equals("是")){
            columnShow=1;
        }
        int searchShow =0;
        if(map.get("searchShow").trim().equals("是")){
            searchShow=1;
        }
        int dict =0;
        if(map.get("dict").trim().equals("是")){
            dict=1;
        }
        int weight =0;
        if(map.get("weight").trim().equals("")){
            weight=0;
        }else {
        	weight=(int)Float.parseFloat(map.get("weight"));
		}

        String sql = "insert into alert_field values(?,?,?,?,?,?,?,?,?,?);";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//System.out.println(sql);
			pstmt.setInt(1,Integer.parseInt(map.get("id")));
			pstmt.setString(2,map.get("fileName"));
			pstmt.setString(3,map.get("propertiesValue"));
			pstmt.setString(4,map.get("type"));
            pstmt.setInt(5,columnShow);
            pstmt.setInt(6,searchShow);
            pstmt.setInt(7,dict);
            pstmt.setString(8,map.get("dictKey"));
            pstmt.setString(9,map.get("widget"));
            pstmt.setInt(10,weight);
			pstmt.executeUpdate();
			System.out.println("insert data success!");
			closeCon(conn);
			return true;

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("insert data fail!");
			closeCon(conn);
			return false;
		}
	}
	/*
	* 查询alert_type表中的内容，并且把值按照List<Map<String,String>>的形式返回
	* */
   public static List<Map<String,String>> getselect(String port,String dataBase,String user, String pwd)  {
    	Connection conn = getConnection(port,dataBase,user,pwd);
    	List<Map<String,String>> list = new LinkedList<>();
    	String sql = "select * from alert_type;";
    	try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//System.out.println(sql);
			ResultSet rs =pstmt.executeQuery();//查询的查找的执行结果是executeQuery
			while (rs.next()) {
				Map<String,String> map =new HashMap<>();
                // 将查询出的内容添加到list中，其中userName为数据库中的字段名称
				String id =rs.getString("id");
				String parentId =rs.getString("parentId");
				String idpath = parentId+"//"+id;
                map.put("id",id);
                map.put("parentId",parentId );
                map.put("idpath",idpath);
                list.add(map);

            }
			System.out.println("select data success!");
			closeCon(conn);
			return list;

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("select data fail!");
			closeCon(conn);
			List<Map<String,String>> list1 = new LinkedList<>();
			return list1;
		}

   }
   /*
   * 更新数据库
   * */
	public static boolean updateTable(String port, String dataBase, String user, String pwd, Map<String,String> map){
		Connection conn = getConnection(port,dataBase,user,pwd);
        String sql = "update alert_type set idpath=? where id =?;";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//System.out.println(sql);
			pstmt.setString(2,map.get("id"));
			pstmt.setString(1,map.get("idpath"));
			pstmt.executeUpdate();
			System.out.println("insert data success!");
			closeCon(conn);
			return true;

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("insert data fail!");
			closeCon(conn);
			return false;
		}
	}

	/*
	*插入alert_type
	* */
	public static boolean insertIntoTable2(String port, String dataBase, String user, String pwd, Map<String,String> map){
		Connection conn = getConnection(port,dataBase,user,pwd);
        String sql = "insert into alert_type(id,parentId,name,value) values(?,?,?,?);";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//System.out.println(sql);
			pstmt.setString(1,map.get("id"));
			pstmt.setString(2,map.get("parentId"));
			pstmt.setString(3,map.get("name"));
            pstmt.setString(4,map.get("value"));
			pstmt.executeUpdate();
			System.out.println("insert data success!");
			closeCon(conn);
			return true;

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("insert data fail!");
			closeCon(conn);
			return false;
		}
	}

}
