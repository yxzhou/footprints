package basic.memory;

import java.util.Vector;

public class Company
{
  private Vector<Employee> employees = new Vector<Employee>();
  private String name;
  
  Company(String name){
    this.name = name;
  }

  public Vector<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Vector<Employee> employees) {
    this.employees = employees;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
//  protected void finalize() {
//    // Note that if there is a finalize() method PhantomReference's don't get appended to a ReferenceQueue
//    System.out.println("Good bye cruel world -" + this.getName());
//    }

  public String toString(){
    return this.getName();
  }
}
