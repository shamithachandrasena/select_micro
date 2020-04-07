package com.samsl.select_micro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QueryController {
    @Value("${host}")
    public String host;

    @Value("${database}")
    public String database;

    @Value("${user}")
    public String user;

    @Value("${password}")
    public String password;

    @RequestMapping("query")
    public Object queryExecutor(@RequestBody String query){
//        query = "select * from cnxanalyzetime_temp  limit 1000";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://"+host+":3306/"+database,user,password);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            List jsonArray = ResultSetConverter.convert(rs);
            con.close();
            return jsonArray;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

}

