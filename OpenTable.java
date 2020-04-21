/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.select.FromItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class OpenTable extends AbsClass
{
    File file;
    TableSpecifics tf;
    LineIterator iterator;
    
    OpenTable(FromItem fromI, Tabless table)throws IOException
    {
        Table table1 = (Table) fromI;
       // String filePath = table.tablePath + table1.getName() + ".txt";
        String filePath = table.tablePath;
        
        file = new File(filePath);
        iterator = FileUtils.lineIterator(file);
        
        
        TableStructure tabStruc = table.tableStruct.get(table1.getName());
        tf = new TableSpecifics();
        
        if(table1.getAlias() != null)
            tf.tableAlias.put(table1.getAlias(), table1.getName());
        else
            tf.tableAlias.put(table1.getName(), table1.getName());
        tf.tableList.add(table1);
        tf.colDef = tabStruc.cd;
        
        int pos = 0;
        for(Map.Entry<String, ColumnDefinition> entry : tabStruc.cd.entrySet())
        {
            tf.columnList.add(entry.getValue().getColumnName());
            if(table1.getAlias() != null)
                tf.colAliasTable.put(new Column(new Table(table1.getAlias()), entry.getValue().getColumnName()), pos);
            else
                tf.colAliasTable.put(new Column(new Table(table1.getName()), entry.getValue().getColumnName()), pos);
            pos++;
        }
    }
    
    @Override
    public boolean isNext()
    {
        boolean var;
        var = (!iterator.hasNext())?(false):(true);
        return var;
    }
    @Override
    public void clear()
    {
        try
        {
            iterator = FileUtils.lineIterator(file);
            
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public TableSpecifics get()
    {
        List<String []> row = new ArrayList<>();
        String s = iterator.nextLine();
        
        row.add(s.split(","));
        
        tf.rowResult = row;
        
        return tf;
    }
}
