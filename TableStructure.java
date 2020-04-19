/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

/**
 *
 * @author kedar
 */
public class TableStructure //tableInformation
{
    Table table = new Table();
    Map<String, ColumnDefinition> cd = new LinkedHashMap<>();
    Map<Column, Integer> columnIndex = new LinkedHashMap<>();
    List<Column> column = new ArrayList<>();
    
    TableStructure(Table table, List<ColumnDefinition> colDef)
    {
        this.table = table;
        
        int index = 0;
        for(ColumnDefinition temp : colDef)
        {
            Column col = new Column(table, temp.getColumnName());
            columnIndex.put(col, index);
            cd.put(col.getColumnName(), temp);
            column.add(col);
        }
    }
}
