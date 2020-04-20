/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.io.IOException;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 *
 * @author kedar
 */
public class SSelectBody extends AbsClass
{
    AbsClass result;
    SSelectBody(SelectBody selectB, Tabless table)
    {
        if(selectB instanceof PlainSelect)
        {
            try{
                result = new SPlainSelect(selectB, table);
            }catch(IOException e){}
        }
    }
    
    @Override
    public boolean isNext()
    {
        boolean var;
        var = (!result.isNext())?(false):(true);
        return var;
    }
    
    @Override
    public void clear()
    {
        result.clear();
    }
    
    @Override
    public TableSpecifics get()
    {
        return result.get();
    }
}
