package basic;

/**
 * What Closure is?
 * From wikipedia:
 * In programming languages, closures (also lexical closures or function closures) are techniques for implementing lexically scoped name binding in languages with first-class functions.
 * Operationally, a closure is a record storing a function together with an environment:
 * a mapping associating each free variable of the function (variables that are used locally, but defined in an enclosing scope) with the value or reference to which the name was bound when the closure was created.
 * A closure—unlike a plain function—allows the function to access those captured variables through the closure's copies of their values or references,
 * even when the function is invoked outside their scope.
 *
 * Two one sentence summaries:
 * 1) a closure is one way of supporting first-class functions; it is an expression that can reference variables within its scope
 *    (when it was first declared), be assigned to a variable, be passed as an argument to a function, or be returned as a function result.
 * 2) a closure is a stack frame which is allocated when a function starts its execution, and not freed after the function returns
 *    (as if a 'stack frame' were allocated on the heap rather than the stack!).
 *
 * 大白话不怎么严谨的说就是：
 * 1) 一个依赖于外部环境自由变量的函数
 * 2) 这个函数能够访问外部环境里的自由变量
 *
 * 闭包, 和JAVA Object一样, 也是一种封装(Encapsulation),
 *
 * Created by joeyz on 7/6/17.
 */
public class ClosureTest {


    public static void main(String[] args){

    }

    //private void

}

class SimpleObject{
    private int x;
    int AddWith(int y){
        return this.x + y;
    }
}
//x is the free variable,  y is the local variable, AddWith() is the function

class outerXY {

    private class Inner {
        private int y = 100;

        public int innerAdd(){
            return x + y;
        }

    }

    private int x = 10;
}


interface AnnoInner{
    int addXYZ();
}

class outerXYZ{
    AnnoInner getAnnoInner(int x){
        int y = 100;

        return new AnnoInner() {
            int z = 1000;

            @Override
            public int addXYZ(){
                return x + y + z;
            }
        };
    }

    //private int num = 10;
}


