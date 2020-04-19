/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.util.List;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 *
 * @author kedar
 */
public class SSelectItem extends AbsClass
{
    AbsClass result;
    List<SelectItem> si;
    Tabless table;
    
    SSelectItem(AbsClass result, Tabless table, PlainSelect plainS)
    {
        this.si = plainS.getSelectItems();
        this.result = result;
        this.table = table;
    }
    
    @Override
    public boolean isNext()
    {
        boolean var;
        var = (!result.isNext() || result == null)?(false):(true);
        return var;
    }
    
    @Override
    public void clear()
    {
        result.clear();
    }
    
    @Override
    TableSpecifics get()
    {
        return result.get();//new TableSpecifics();
    }
}
