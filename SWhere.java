/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import static com.mainclass.Execute.resultCalc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 *
 * @author kedar
 */
public class SWhere extends AbsClass
{
    AbsClass result;
    Expression where;
    SWhere(AbsClass fromI, Tabless table, PlainSelect plainS)
    {
        this.result=fromI;
        
        
    }
    
    @Override
    public boolean isNext()
    {
        while (result.isNext())
        {
            
        }
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
