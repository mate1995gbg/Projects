using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class Company
    {
        private static int employeeNumberCounter = 1;
        public List<Programmer> ProgrammerList { get; set; }
        public List<Mentor> MentorList { get; set; }
        public List<Mentee> MenteeList { get; set; }
        public List<Employee> EmployeeList { get; set; }
        public List<ProgrammingLanguage> ProgrammingLanguageList { get; set; }

        public Company()
        {
            ProgrammerList = new List<Programmer>();
            MentorList = new List<Mentor>();
            MenteeList = new List<Mentee>();
            EmployeeList = new List<Employee>();
            ProgrammingLanguageList = new List<ProgrammingLanguage>();
        }
        // Method to add a new employee and automatically generate an employee number
        public void AddEmployee(Employee employee)
        {
            int employeeNumber = GenerateEmployeeNumber();
            EmployeeList.Add(employee);
        }

        // Method to generate a unique employee number
        public int GenerateEmployeeNumber()
        {
            int generatedNumber = employeeNumberCounter;
            employeeNumberCounter++;
            return generatedNumber;
        }
    }
}
