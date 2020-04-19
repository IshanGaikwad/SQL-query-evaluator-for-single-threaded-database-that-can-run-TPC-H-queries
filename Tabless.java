/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

//Data about tables.

import java.util.LinkedHashMap;
import java.util.Map;
import net.sf.jsqlparser.schema.Table;


/**
 *
 * @author kedar
 */
public class Tabless //tableList
{
    String tablePath;
    
    public Map<String, TableStructure> tableStruct = new LinkedHashMap<>();
}
