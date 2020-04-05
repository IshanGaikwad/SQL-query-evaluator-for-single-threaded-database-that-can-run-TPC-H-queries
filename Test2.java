/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.nio.file.*;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Stack;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.eval.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.util.deparser.*;
import net.sf.jsqlparser.schema.*;
import net.sf.jsqlparser.statement.create.table.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.deparser.*;
import net.sf.jsqlparser.parser.*;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.csv.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jsqlparser.statement.Statement;

/**
 *
 * @author kedar
 */
public class Test2 {

    static Map<String, List<ColumnDefinition>> table_schema = new HashMap<>();
    static Map<String, Map<String[], Integer[]>> table_col_index = new HashMap<>();
    static Map<String, String[]> table_col = new HashMap<>();
    static Map<String, Map<String[], String[]>> table_col_datatype = new HashMap<>();

    static Map<String, String> tablename_withalias = new HashMap<>();
    static Map<String, String> alias_withTableName = new HashMap<>();
    static List<String[]> tableData = new ArrayList<>();
    static String csvPath = null;
    static String queryPath = null;
    
    static enum variables {
        CHAR, VARCHAR, STRING, DATE, DOUBLE, INT, NUMBER
    }

    public static void main(String[] args) throws Exception {
        //List<String> tableData = new ArrayList<>(); //we get schema from create table. not csv header.
        queryPath="K:\\test.txt";//args[0];
        csvPath="K:\\R.dat";//args[1];
        String data = readFileAsString(queryPath);
        //System.out.println(data); 

        String[] queries = breakBySemiColon(data);
        getQueryData(queries);

        //for(int i=0; i<queries.length; i++)
        //  System.out.println(queries[i]);
    }

    static String[] breakBySemiColon(String data) {
        String[] temp = {""};
        temp = data.split("\\s*;\\s*");
        //added by Nupur
        return temp;
    }

    //added by Nupur
    public static void getQueryData(String[] values) throws ParseException, SQLException//use try catch 
    {
        //System.out.println("srf"+values[0]);
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            Reader input = new StringReader(values[i]);
            CCJSqlParser parser = new CCJSqlParser(input);
            Statement stmt = parser.Statement();
            while (stmt != null) {
                if (stmt instanceof Select) {
                    Select select = (Select) stmt;
                    SelectBody body = select.getSelectBody();
                    if (body instanceof PlainSelect) {
                        PlainSelect plain = (PlainSelect) body;
                        Distinct distinct = plain.getDistinct();
                        List<Join> join = plain.getJoins();
                        
                        //where
                        FromItem fromitem = plain.getFromItem();
                        List<SelectItem> selectitem = plain.getSelectItems();
                        //getFromItem(fromitem, join);
                        List<String[]> printRes = printselect(selectitem);

                        //resultCalc(xpress, str);
                        for (String[] string : printRes) {
                            for (String st : string) {
                                System.out.print(st + "|");
                            }
                            System.out.println("");
                        }
                        Expression where = plain.getWhere();
                        if (where != null) {
                            tableData = getWhereCond(where);
                        }
                        
                        getFromItem(fromitem, join);
                        tableData = readFullCSV(csvPath/* +"\\"+ ((Table) fromitem).getName() + ".dat"*/);
                    }
                    if (body instanceof Union){
                        Union union = (Union) body;
                        if(body instanceof plainSelect){
                            
                        }
                        Distinct distinct = union.getPlainSelects();
                        List<Join> join = union.();
                        
                        //where
                        FromItem fromitem = union.getFromItem();
                        List<SelectItem> selectitem = union.getSelectItems();
                        //getFromItem(fromitem, join);
                        List<String[]> printRes = printselect(selectitem);

                        //resultCalc(xpress, str);
                        for (String[] string : printRes) {
                            for (String st : string) {
                                System.out.print(st + "|");
                            }
                            System.out.println("");
                        }
                        Expression where = union.getWhere();
                        if (where != null) {
                            tableData = getWhereCond(where);
                        }
                        
                        getFromItem(fromitem, join);
                        tableData = readFullCSV(csvPath/* +"\\"+ ((Table) fromitem).getName() + ".dat"*/);
                        
                    }
                } else if (stmt instanceof CreateTable) {
                    CreateTable createTable = (CreateTable) stmt;
                    /*int z=0;
                        MyTable newTab = new MyTable(createTable);
                        Table newTable = createTable.getTable();
                        //System.out.println(newTable);
                        
                        List<ColumnDefinition> schema = createTable.getColumnDefinitions();
                        
                       // System.out.print("main "+schema);
                        List<ColDataType> temp = newTab.getColDataType();
                       // System.out.println("\nmytable: "+newTab.getColumnDef(createTable).get(0));
                     */
                    // System.out.print("create error");
                    tableCreate(stmt, createTable);
                } else {
                    //  throw new SqlException("cant handle"+stmt);
                }
                stmt = parser.Statement();
            }
            System.out.println("=");
        }
        //Select selectstat   = (Select) CCJSqlParserUtil.parse(values[0]);
        //Statement stateme = parser.Statement();

    } // End by Nupur

    static  List<String[]> printselect(List<SelectItem> selectItems) throws SQLException {
        List<Expression> expre = new ArrayList<>();
        List<String[]> sendRes=new ArrayList<>();
        for (SelectItem selectItem : selectItems) {
            SelectExpressionItem selItemEx = (SelectExpressionItem) selectItem;
            expre.add(selItemEx.getExpression());
        }
        //System.out.println("selcEx: "+selectItems.size());
            
        for (String[] strings : tableData) {
            String[] resultArray = new String[selectItems.size()];

            for (int i = 0; i < selectItems.size(); i++) {
                PrimitiveValue pmv = resultCalc(expre.get(i), strings);
                //
                resultArray[i]=pmv.toRawString();
//                System.out.println("$$$$  "+resultArray[i]);
            }
//            for (String string : resultArray) {
//                System.out.println("RE- "+string);
//            }
        sendRes.add(resultArray);
        }
        
        return sendRes;
               
    }

    static void tableCreate(Statement stmt, CreateTable create) {
        String name = create.getTable().getName();
        List<ColumnDefinition> colDef = create.getColumnDefinitions();

        String temp1[] = new String[colDef.size()];
        Integer temp2[] = new Integer[colDef.size()];
        String temp3[] = new String[colDef.size()];

        //System.out.print("asdl");
        int index = 0;
        for (ColumnDefinition c : colDef) {
            //System.out.print("\ntblcreataefor");
            //System.out.println(index);
            temp1[index] = c.getColumnName();
            temp2[index] = index + 1;
            temp3[index] = c.getColDataType().getDataType();
            index++;
        }

        Map<String[], Integer[]> temp4 = new HashMap<>();
        temp4.put(temp1, temp2);
        Map<String[], String[]> temp5 = new HashMap<>();
        temp5.put(temp1, temp3);

        table_schema.put(name, colDef);
        System.out.println("name"+ name);
        table_col_index.put(name, temp4);
        table_col.put(name, temp1);
        table_col_datatype.put(name, temp5);
    }

    static List<String[]> getWhereCond(Expression xpress) throws SQLException {
        List<String[]> res_where = new ArrayList<>();
        for (String str[] : tableData)//result_set contains row values.
        {
            PrimitiveValue value = resultCalc(xpress, str);
            if (value.getType().equals(PrimitiveType.BOOL) && value.toBool()) {
                res_where.add(str);
            }
        }

        return res_where;
    }

    static PrimitiveValue resultCalc(Expression xpress, String rowData[]) throws SQLException {
        Eval eval = new Eval() {
            @Override
            public PrimitiveValue eval(Column col) throws SQLException {
                String tableName = null;
                int pos = -1;
                Table table = col.getTable();
                //System.out.println(col.getTable().getName());
                ColumnDefinition colD = new ColumnDefinition();

                for (Map.Entry<String, String> entry : tablename_withalias.entrySet()) {
                    String str[] = table_col.get(entry.getKey());
                    List list = Arrays.asList(str);
                    if (list.contains(col.getColumnName())) {
                        tableName = entry.getValue();
                    }
                    System.out.println("tableNameNN  "+tableName);
                    System.out.println("tablesch = "+table_schema.toString()+"tablename: "+table_schema.get(tableName));
                    List<ColumnDefinition> cd = table_schema.get(tableName);
                    
                    if (cd == null) {
                       // System.out.println("alias = "+alias_withTableName.toString()+tableName);
                        tableName = alias_withTableName.get(table.getName());
                        
                      //  System.out.print("schema "+table_schema.toString());
                        cd = table_schema.get(tableName);
                        //System.out.print("cd "+cd.toString());
                    }
                  //  System.out.print(cd.toString());
                    for (ColumnDefinition c : cd) {
                        if (Objects.equals(c.getColumnName(), col.getColumnName())) {
                            
                        }
                        colD = c;
                    }

                    Map<String[], Integer[]> temp = table_col_index.get(tableName);
                    for (Map.Entry<String[], Integer[]> entry1 : temp.entrySet()) {
                        for (int i = 0; i < entry1.getKey().length; i++) {

                            if (Objects.equals(entry1.getKey()[i], col.getColumnName())) {

                                pos = entry1.getValue()[i];
                                break;
                            }
                            if (pos == -1) {
                                continue;
                            }
                        }
                    }

                    //System.out.println("--"+pos);
                }
                String answer = rowData[pos - 1];
                
                variables datatype = variables.valueOf(colD.getColDataType().getDataType().toUpperCase());

                switch (datatype) {
                    case CHAR:
                    case VARCHAR:
                    case STRING:
                        return new StringValue(answer);
                    case DATE:
                        return new DateValue(answer);
                    case DOUBLE:
                        return new DoubleValue(answer);
                    case INT:
                        return new LongValue(answer);
                }
                throw new UnsupportedOperationException("Invalid");
            }
        };
        return eval.eval(xpress);
    }

    static void getFromItem(FromItem fromI, List<Join> join) {
        if (fromI.getAlias() == null) {
            tablename_withalias.put(((Table) fromI).getName(), ((Table) fromI).getName());//hashmapping of alias and table names
        } else {
            tablename_withalias.put(((Table) fromI).getName(), fromI.getAlias());
            alias_withTableName.put(fromI.getAlias(), ((Table) fromI).getName());
        }

        if (join != null) {
            for (Join temp : join) {
                Table table = (Table) temp.getRightItem();
                if (temp.getRightItem().getAlias() == null) {
                    tablename_withalias.put(table.getName(), table.getName());
                } else {
                    tablename_withalias.put(table.getName(), temp.getRightItem().getAlias());
                }
            }
        }

    }

    public static String readFileAsString(String fileName)//throws Exception 
    {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            //IOException handling.
        }
        return data;
    }

    static List<String> readSchema(String path) {
        String line = new String();
        String splitBy = ",";
        List<String> schema = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            line = br.readLine();
            String temp[] = line.split(splitBy);

            for (int i = 0; i < temp.length; i++) {
                schema.add(i, temp[i]);
            }

            //printing list values
            Iterator itr = schema.iterator();
            while (itr.hasNext()) {
                String abc = (String) itr.next();
                System.out.print(abc);
            }

            //return schema;
        } catch (IOException e) {
            //Exception handling pending.
        }
        return schema;
    }

    static List<String[]> readFullCSV(String path) {
        String line = new String();
        String splitBy = "\\|";
        List<String[]> csvData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String temp[] = line.split(splitBy);
                csvData.add(temp);
            }
        } catch (IOException e) {
        }

        return csvData;
    }
}
