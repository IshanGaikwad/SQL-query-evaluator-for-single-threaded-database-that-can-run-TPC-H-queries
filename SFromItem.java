package com.mainclass;

import java.io.IOException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 *
 * @author kedar
 */
public class SFromItem extends AbsClass
{
    AbsClass result;
    
    SFromItem(FromItem fromI, Tabless table)throws IOException
    {
        if(fromI instanceof Table)
            result = new OpenTable(fromI, table);
   //     if(fromI instanceof SubSelect)
    //          result = new SSubSelect(fromI, table)        
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
