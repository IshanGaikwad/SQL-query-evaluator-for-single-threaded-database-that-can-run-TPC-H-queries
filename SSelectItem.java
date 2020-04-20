/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
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
    TableSpecifics get()//this is our tableDetail
    {
        return result.get();//selectExpItem();
    }
    
    private TableSpecifics selectExpItem()//have to implement this
    {
        TableSpecifics tS = result.get();
        TableSpecifics tS2;
        try{
            tS2 = (TableSpecifics) tS.clone();
        
        
        //updateCD(tS, table, tS2)
        
        if(si.get(0) instanceof AllColumns)
        {
            tS2.rowResult = tS.rowResult;
        }
        else
        {
            tS2.rowResult = new ArrayList<>();
            List<Expression> xpress = new ArrayList<>();
            for(SelectItem sel : si)
            {
                if(sel instanceof SelectExpressionItem)
                {
                    xpress.add(((SelectExpressionItem) sel).getExpression());
                }
                else if(sel instanceof AllTableColumns)
                {
                    //Column c;
                    AllTableColumns atc = (AllTableColumns) sel;//right.Specifics is tabledetail
                    TableStructure tstruc = table.tableStruct.get(tS2.tableAlias.get(atc.getTable().getName()));
                   // xpress.addAll(tstruc.column.stream().map(column->(Expression) new Column(atc.getTable(), )));
                }
            }
            
            for(String row[] : tS.rowResult)
            {
                String str[] = new String[xpress.size()];
                for(int i=0; i<xpress.size(); i++)
                {
                 //   PrimitiveValue pVal;// = CalculateResult.xpressEval(xpress.get(i), row, tS2, tS);
                 //   str[i]= pVal.toRawString();
                }
                tS2.rowResult.add(str);
            }
        }
        }catch(CloneNotSupportedException e){}
    }
}