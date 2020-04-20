/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainclass;

import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

/**
 *
 * @author kedar
 */
public class DataType 
{
    PrimitiveValue dataType(ColumnDefinition colD)
    {
        String answer = rowData[pos - 1];//yes
        Execute.variables datatype = Execute.variables.valueOf(colD.getColDataType().getDataType().toUpperCase());
        switch (datatype) {
                    case CHAR:
                    case VARCHAR:
                    case STRING:
                        return new StringValue(answer);
                    case DATE:
                        return new DateValue(answer);
                    case DOUBLE:
                        return new DoubleValue(answer);
                    case INT:
                        return new LongValue(answer);
                }
        throw new UnsupportedOperationException("Invalid");
    }
}
