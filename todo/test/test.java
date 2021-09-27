package todo.test;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.lang.reflect.Field;

public class test {

    public static void main(String[] args){

        List<Father> fathers = new ArrayList<>();

        fathers.add(new Child());
        fathers.add(new Child());

        for(Father f : fathers){
            System.out.println(toString(f.getClass()));
            System.out.println(f.toString());


        }

        System.out.println();

        List<Child> children = new ArrayList<>();

        children.add(new Child());
        children.add(new Child());

        for(Child f : children){
            System.out.println(toString(f.getClass()));
            System.out.println(f.toString());


        }

    }

    public static String toString(Class t) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( t.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = t.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(t) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

}


class Father{
    public String label;
    public UUID uuid;
    public String value;

    Father(){
        this.label = "father";

        uuid = UUID.randomUUID();
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(" - ")
                .append(uuid.toString()).append(" - ")
                .append(value);
        return sb.toString();
    }
}


class Child extends Father{

    public String childProperty;

    Child(){
        super();
        this.label = "child";
    }


    //    @Override public String toString() {
    //        return super.toString() + " - " + childProperty;
    //    }

}