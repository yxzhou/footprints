package fgafa.basic.memory;

public class Employee
{
  private Company company;
  private String name;

  Employee(String name){
    this.name = name;
  }
  
  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
    
    company.getEmployees().add(this);
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
//  }

  public String toString(){
    return this.getName();
  }
}
