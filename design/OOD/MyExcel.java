package fgafa.design.OOD;

import java.util.List;

/**
 * 
 * Design data structure something like excel's cell. 

    The cell has two types: value type and formula type (sum of other cells)
 *
 */


    public class MyExcel {
    //base interface
    interface Observe{
       void notify1();
    }
    
    //base class
    class Cell implements Observe{
        int rowId;
        int columnId;
        Object value;
        
        @Override
        public void notify1(){
            
        }
    }
    
    class ValueCell extends Cell {
        
        
        
    }
    
    class FormulaCell extends Cell{
        List<Cell> sums;
    }
    
    class MySheet{
        List<List<Cell>> matrix;
    }
}
