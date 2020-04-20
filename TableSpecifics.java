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
public class TableSpecifics //tableDetails.
{
    List<String> columnList = new ArrayList<>();
    List<String[]> rowResult = new ArrayList<>();// yes.
    List<Table> tableList = new ArrayList<>();
    
    Map<String, ColumnDefinition> colDef = new LinkedHashMap<>();
    Map<String, String> tableAlias = new LinkedHashMap<>();
    Map<Column, Integer> colAliasTable = new LinkedHashMap<>();
    
    //Object clone()
    @Override
    public Object clone()throws CloneNotSupportedException
    {
        return super.clone();
    }
}
