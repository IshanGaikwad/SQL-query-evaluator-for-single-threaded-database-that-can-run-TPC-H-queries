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
    SWhere(AbsClass fromI, Tabless table, PlainSelect plainS)
    {
        List<String[]> res_where = new ArrayList<>();
        //PrintClass p = new PrintClass(fromI);
        
        //i need to know which is our line coming from on which to apply logic. ok wait.
        String str[] = new String[3];
        try
        {
            while(p.hasNext())
            {
                //PrimitiveValue value = resultCalc(xpress, str);
                if (value.getType().equals(PrimitiveType.BOOL) && value.toBool()) 
                    res_where.add(str);
            }
        }catch(IOException e)
        {
            //IOException handling.
        }

        
    }
    
    @Override
    public boolean isNext()
    {
        return true;
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
