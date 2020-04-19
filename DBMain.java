/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.io.*;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.*;

/**
 *
 * @author kedar
 */
public class DBMain 
{
    
    public static void main(String args[]) throws ParseException
    {
        String queryFilePath = "K:\\test.txt"; //add path
        Tabless table1 = new Tabless();
        table1.tablePath = "tablepath"; //add path
        String queries[] = readQueries(queryFilePath);
        /*for(int i=0; i<queries.length; i++)
            System.out.println(queries[i]);*/ //for testing query reading.
            
        for(String query: queries) //extract one query at a time and run following code on each query.
        {
            Reader input = new StringReader(query);
            CCJSqlParser parser = new CCJSqlParser(input);
            
            Statement stmt = parser.Statement();
            
            if(stmt instanceof Select)
            {
                //select code
                Select slct=(Select) stmt;
                AbsClass result = new SSelectBody(slct.getSelectBody(), table1);
                while(result.isNext())
                    displayResult(result.get().rowResult);
            }
            
            else if(stmt instanceof CreateTable)
            {
                CreateTable createTable = (CreateTable) stmt;
                tableCreate(stmt, createTable, table1);
            }
        }
    }
    static void displayResult(List<String[]> resultofRow){
        for(String[] s:resultofRow){
            for(String st:s){
                System.out.println(st+"|");
            }System.out.println();
        }
    }
    static void tableCreate(Statement stmt, CreateTable cT, Tabless table)
    {
        String tableName = cT.getTable().getName();
        Table t = new Table();
        List<ColumnDefinition> colDef = cT.getColumnDefinitions();
        TableStructure ts = new TableStructure(t, colDef);
        table.tableStruct.put(tableName, ts);
    }
    
    static String[] readQueries(String path)
    {
        String data = readFileAsString(path);
        String queries[] = breakBySemiColon(data);
        
        return queries;
    }
    
    public static String readFileAsString(String fileName)//throws Exception 
    {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            //IOException handling.
        }
        return data;
    }
    
    static String[] breakBySemiColon(String data) 
    {
        String[] temp = {""};
        temp = data.split("\\s*;\\s*");
        //added by Nupur
        return temp;
    }
}
