using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class Employee : Person, IDetailed, IComparable<Employee> //using IComparable (C-criteria)
    {
        public int PayRollNumber { get; set; }
        public string Department { get; set; }
        public Employee(string firstName, string lastName, string department) :base(firstName, lastName)
        {
            PayRollNumber = base.GenerateEmployeeNumber();
            Department = department;
        }

        public override string GetDetails()
        {
            return $"Name: {FirstName} {LastName}, Payroll number: {PayRollNumber}, Department: {Department}";
        }
        public int CompareTo(Employee other)
        {
            int result = this.LastName.CompareTo(other.LastName);

            if (result == 0) //if last names are equal then run code inside if statement
            {
                result = this.FirstName.CompareTo(other.FirstName);
            }
            return result;
        }
    }
}
