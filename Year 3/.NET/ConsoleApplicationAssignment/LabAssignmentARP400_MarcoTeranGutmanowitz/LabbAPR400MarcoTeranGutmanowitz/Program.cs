using System;
using System.Collections.Generic;
using System.Linq;
using ClassLibraryForProject;
using System.IO;

namespace LabbAPR400MarcoTeranGutmanowitz
    //NOTE: i have used List<T> as my collection type. it is because i have experience with this collection and it is useful and flexible when processing for example null or incomplete objects, or large amounts of data. Also LINQ is easy to use with List<T>
{
    internal class Program
    {
        private static Company myCompany;

        public delegate string CalculateSalaryDelegate(Programmer programmer);
        static void Main(string[] args) //this is my main method. here the user can select which function to run.
        {
            myCompany = new Company();
            createObjects();
            bool exit = false;
            while (!exit)
            {
                Console.WriteLine("Press 1 to show the report (or any other key to exit):");
                Console.WriteLine("Press 2 to change specialization of a Programmer:");
                Console.WriteLine("Press 3 to add a Programmer, Mentor or Mentee:");
                Console.WriteLine("Press 4 to add an existing Mentee to another Mentor:");
                Console.WriteLine("Press 5 to read from text file and show employees:");
                ConsoleKeyInfo keyInfo = Console.ReadKey();
                if (keyInfo.KeyChar == '1')
                {
                    showReport();
                }

                else if (keyInfo.KeyChar == '2')
                {
                    changeSpecialization();
                }

                else if (keyInfo.KeyChar == '3')
                {
                    addProgrammer();
                }
                else if (keyInfo.KeyChar == '4')
                {
                    addMenteeToMentor();
                }
                else if (keyInfo.KeyChar == '5')
                {
                    getEmployeesFromTxtFile();
                }
                else
                {
                    exit = true;
                }
            }
        }

        public static void addProgrammer() //this is the function which adds a programmer by prompting user to enter first name last nae and skill level. user must also select a programming language which is derived from the programminglanguage class
        {
            Console.WriteLine("Enter first name:");
            string firstName = Console.ReadLine();

            Console.WriteLine("Enter last name:");
            string lastName = Console.ReadLine();

            Console.WriteLine("Enter skill level (1-5):");
            int skillLevel;
            while (true)
            {
                if (int.TryParse(Console.ReadLine(), out skillLevel) && skillLevel >= 1 && skillLevel <= 5)
                {
                    break;
                }
                Console.WriteLine("Invalid input. Please enter a number.");
            }


            Console.WriteLine("Select programming language:");
            for (int i = 0; i < myCompany.ProgrammingLanguageList.Count; i++)
            {
                Console.WriteLine($"{i + 1}. {myCompany.ProgrammingLanguageList[i].LanguageName}");
            }

            int selectedLanguageIndex;
            while (!int.TryParse(Console.ReadLine(), out selectedLanguageIndex) || selectedLanguageIndex < 1 || selectedLanguageIndex > myCompany.ProgrammingLanguageList.Count) //check that selectedlanguageId is not out of bounds
            {
                Console.WriteLine("Invalid input. Please select a number from the list.");
            }

            ProgrammingLanguage selectedLanguage = myCompany.ProgrammingLanguageList[selectedLanguageIndex - 1]; //must be -1 since index = 0

            Console.WriteLine("Select programmer type:");
            Console.WriteLine("1. Programmer");
            Console.WriteLine("2. Mentor");
            Console.WriteLine("3. Mentee");

            int selectedType;
            while (!int.TryParse(Console.ReadLine(), out selectedType) || selectedType < 1 || selectedType > 3) //check so that user doesnt select anything above or below 1-3.
            {
                Console.WriteLine("Invalid input. Please select a number from the list.");
            }

            if (selectedType == 1)
            {
                Programmer newProgrammer = new Programmer(firstName, lastName, selectedLanguage, skillLevel, "Research & Development");
                newProgrammer.SkillLevel = skillLevel;
                myCompany.AddEmployee(newProgrammer);
                Console.WriteLine("Programmer added successfully!");
                Console.WriteLine(newProgrammer.GetDetails());
            }
            else if (selectedType == 2)
            {
                Mentor newMentor = new Mentor(firstName, lastName, selectedLanguage, skillLevel, "Research & Development");
                newMentor.SkillLevel = skillLevel;
                myCompany.AddEmployee(newMentor);
                Console.WriteLine("Mentor added successfully!");
                Console.WriteLine(newMentor.GetDetails());
            }
            else if (selectedType == 3)
            {
                List<Mentor> allMentors = new List<Mentor>();
                foreach (Employee employee in myCompany.EmployeeList)
                {
                    if (employee is Mentor mentor)
                    {
                        allMentors.Add(mentor);
                    }
                }

                Mentor selectedMentor = null;
                while (true) //while loop so that user must select a mentee as well.
                {
                    Console.WriteLine("Select a mentor from the list below by entering their payroll number: ");
                    for (int i = 0; i < allMentors.Count; i++)
                    {
                        Console.WriteLine($"{i + 1}. {allMentors[i].FirstName} {allMentors[i].LastName} (Payroll Number: {allMentors[i].PayRollNumber})");
                    }

                    int mentorNumber;
                    if(int.TryParse(Console.ReadLine(), out mentorNumber) && mentorNumber >= 1 && mentorNumber <= allMentors.Count)
                    {
                        selectedMentor = allMentors[mentorNumber - 1];
                        break;
                    }
                    else
                    {
                        Console.WriteLine("Invalid input. Please enter a nubmer between 1 and " + allMentors.Count);
                    }
                }
                if (skillLevel >= selectedMentor.SkillLevel)
                {
                    Console.WriteLine("the skillLevel of the mentee is higher or equal to the mentor! try again.");
                }

                else if (skillLevel < selectedMentor.SkillLevel)
                {
                    Mentee newMentee = new Mentee(firstName, lastName, selectedLanguage, selectedMentor, skillLevel, "Research & Development");
                    myCompany.AddEmployee(newMentee);
                    Console.WriteLine("Mentee added successfully!");
                    Console.WriteLine(newMentee.GetDetails());
                }

                else
                {
                    Console.WriteLine("Something went wrong, try again!");
                }

            }
        }
        public static void createObjects() //this method creates objects of the relevant classes so that there is test data when starting the program.
        {
            ProgrammingLanguage csharp = new ProgrammingLanguage("C#");
            ProgrammingLanguage java = new ProgrammingLanguage("Java");
            ProgrammingLanguage ruby = new ProgrammingLanguage("Ruby");
            
            Programmer programmer1 = new Programmer("Elin", "Efternamn", ruby, 1, "Research & Development");
            Programmer programmer2 = new Programmer("Marco", "Teran", java, 1, "Research & Development");

            Mentor mentor1 = new Mentor("Hannah", "Torsteinsrud", csharp, 5, "Research & Development");
            Mentee mentee1 = new Mentee("Mike", "Almalla", csharp, mentor1, 1, "Research & Development");

            Mentor.AddMenteeToMentor(mentor1, mentee1);

            myCompany.AddEmployee(programmer1);
            myCompany.AddEmployee(programmer2);
            myCompany.AddEmployee(mentor1);
            myCompany.AddEmployee(mentee1);

            myCompany.ProgrammingLanguageList.Add(csharp);
            myCompany.ProgrammingLanguageList.Add(java);
            myCompany.ProgrammingLanguageList.Add(ruby);

            Console.WriteLine("createObjects has been run");
        }
        internal static void showReport() //this  method shows the report that is described in the assignment description
        {
            Console.WriteLine("DETAILED REPORT");
            double totalSalary = 0;
            myCompany.EmployeeList.Sort(); //using IComparable here
            foreach (var employee in myCompany.EmployeeList) //gets all employees from the list in Company class.
            {
                if (employee is Programmer programmer) //if employee is programmer then show details for the programmer.
                {
                    totalSalary += programmer.CurrentSalary;

                    Console.WriteLine("Programmer: " + programmer.FirstName + " " + programmer.LastName);
                    Console.WriteLine("Payroll number: " + programmer.PayRollNumber);
                    Console.WriteLine("Specialist language: " + programmer.SpecialistLanguage.LanguageName);
                    Console.WriteLine("Current salary: " + programmer.CurrentSalary);

                    if (programmer is Mentor mentor) //if programmer is also mentor then show info for that aswell
                    {
                        Console.WriteLine("Mentoring: ");
                        foreach (var mentee in mentor.Mentees)
                        {
                            Console.WriteLine(mentee.FirstName + " " + mentee.LastName);
                        }
                    }

                    if (programmer is Mentee mentee2) //if programmer is mentee then show info for that aswell.

                    {
                        Console.WriteLine("Mentored by: " + mentee2.Mentor.FirstName + " " + mentee2.Mentor.LastName);
                    }
                    Console.WriteLine();
                }
            }

            Console.WriteLine("Total montly salary bill is: " + totalSalary);
            Console.WriteLine("Press any key to continue...");
            Console.ReadKey();
        }
        internal static void changeSpecialization() //this method allows user to change its specialization. the selected programmers specialization change will also result in a change in salary if the C# language is changed.
        {
            Console.WriteLine("select the programmer you wish to change specialization for:");
            List<Programmer> programmers = new List<Programmer>();
            List<ProgrammingLanguage> programmingLanguages = myCompany.ProgrammingLanguageList;

            foreach (var employee in myCompany.EmployeeList)
            {
                if (employee is Programmer programmer)
                {
                    Console.WriteLine("enter number: " + programmer.PayRollNumber + " to change programmer: " + programmer.FirstName + " " +
                    programmer.LastName + " with specialization: " + programmer.SpecialistLanguage.LanguageName);
                    programmers.Add(programmer);
                }
            }

            while (true)
            {
                Console.WriteLine("enter a programmer number or 'X' to exit.");
                string input = Console.ReadLine();

                if (input.ToUpper() == "X")
                {
                    Console.WriteLine("Exiting...");
                    break;
                }

                if (int.TryParse(input, out int selectedNumber))
                {
                    var selectedProgrammer = programmers.FirstOrDefault(p => p.PayRollNumber == selectedNumber);

                    if (selectedProgrammer != null)
                    {
                        while (true)
                        {
                            Console.WriteLine("Enter the new specialization for: " + selectedProgrammer.FirstName + " " + selectedProgrammer.LastName + " with specialization language: " + selectedProgrammer.SpecialistLanguage.LanguageName);
                            string newSpecialization = Console.ReadLine();
                            ProgrammingLanguage inputLanguage = programmingLanguages.FirstOrDefault(s => string.Equals(s.LanguageName, newSpecialization, StringComparison.OrdinalIgnoreCase));

                            CalculateSalaryDelegate calculateSalary = SalaryCalculator.CalculateEnhancedSalary;
                            if (inputLanguage != null)
                            {
                                selectedProgrammer.ChangeSpecialization(inputLanguage);
                                Console.WriteLine("changed language successfully!");
                                string result = calculateSalary(selectedProgrammer);
                                Console.WriteLine(result);
                                break;
                            }
                            else
                            {
                                Console.WriteLine("Could not find input language!! Try again or enter 'X' to exit.");
                                if (Console.ReadLine().ToUpper() == "X")
                                {
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        Console.WriteLine("Programmer with the entered number does not exist. Try again.");
                    }
                }
                else
                {
                    Console.WriteLine("Invalid input. Please enter a number or 'X' to exit.");
                }
            }
        }
        internal static void addMenteeToMentor() //this method allows you to add a new mentee to mentor.
        {
            List<Mentor> allMentors = new List<Mentor>();
            List<Mentee> allMentees = new List<Mentee>();
            List<Employee> allEmployees = myCompany.EmployeeList;

            Console.WriteLine("Select a Mentor:");
            foreach (var employee in allEmployees)
            {
                if (employee is Mentor mentor)
                {
                    Console.WriteLine($"enter number: {mentor.PayRollNumber}  for: {mentor.FirstName} {mentor.LastName} with specialization: {mentor.SpecialistLanguage.LanguageName}");
                    allMentors.Add(mentor);
                }
            }
            int input = int.Parse(Console.ReadLine());
            Mentor selectedMentor = allMentors.FirstOrDefault(mentor => mentor.PayRollNumber == input);
            if (selectedMentor != null)
            {
                int selectedMenteePayrollNumber = -1;
                Console.WriteLine("Select a mentee to add to your mentor:");
                foreach (var employee in allEmployees)
                {
                    if (employee is Mentee mentee)
                    {
                        Console.WriteLine($"enter number: {mentee.PayRollNumber}  for: {mentee.FirstName} {mentee.LastName} with specialization: {mentee.SpecialistLanguage.LanguageName} ");
                        allMentees.Add(mentee);
                    }
                }
                while (selectedMenteePayrollNumber == -1)
                {
                    selectedMenteePayrollNumber = int.Parse(Console.ReadLine());
                }
                Mentee selectedMentee = allMentees.FirstOrDefault(mentee => mentee.PayRollNumber == selectedMenteePayrollNumber);
                if (selectedMentee != null)
                {
                    Boolean lastLoop = false;
                    while (lastLoop == false)
                    {
                        try
                        {
                            Mentor.AddMenteeToMentor(selectedMentor, selectedMentee);
                            Console.WriteLine($"Success in adding {selectedMentee.FirstName} {selectedMentee.LastName} to the mentee list of: {selectedMentor.FirstName} {selectedMentor.LastName}");
                            lastLoop = true;
                        }
                        catch (MentorSkillLevelException ex) //throws the exception defined in Mentor class method AddMenteeToMentor
                        {
                            Console.WriteLine(ex.Message);
                        }
                        catch (Exception ex) //if other exception is thrown, not related to Mentor skill level, this catch is executed.
                        {
                            Console.WriteLine("An unexpected error occurred: " + ex.Message);
                        }
                    }
                }
                else
                {
                    Console.WriteLine("You've enetered a wrong integer for selectedMentee, try again!");
                }
            }
            else
            {
                Console.WriteLine("You've entered a wrong input for the number for Mentor you want to select! try again..");
            }
            
        }
        internal static void getEmployeesFromTxtFile() //this method is for the A-grade criteria of the assignment. i am using SortedDictionary with Department as key and a List<Employee> as value for each key.
        {
            string filePath = "Employees.txt"; //employees.txt has "Copy to output directory" set to "copy always", so this filepath should work on your pc aswell!
            List<string> lines = File.ReadAllLines(filePath).ToList(); //read from text file.
            
            SortedDictionary<string, List<Employee>> departmentEmployees = new SortedDictionary<string, List<Employee>>(); // Create the sorted dictionary to hold departments and their employees (one List<Employee> for each department)

            foreach (var line in lines)
            {
                if(string.IsNullOrEmpty(line)) // if line is just blank space or empty, then skip the foreach loop for this line
                {
                    continue;
                }

                string[] entries = line.Split(new char[] { ' ', '|' }, StringSplitOptions.RemoveEmptyEntries); //make a string array for each line with firstname, last name and department
                Employee employee = new Employee(entries[0], entries[1], entries[2]); //create employee object from string array values.

                if(!departmentEmployees.ContainsKey(employee.Department)) //if department doesnt exist in the SortedDictionary, then it creates a new "row" in the SortedDictionary.
                {
                    departmentEmployees[employee.Department] = new List<Employee>(); // Create a new list for this department
                }

                departmentEmployees[employee.Department].Add(employee); // Add the employee to the appropriate list with department as Key
            }

            foreach (var kvp in departmentEmployees) //for each key value pair in departmentEmployees
            {
                Console.Write($"{kvp.Key}: "); //prints department (remember key is department and value is the list of employees (one key (department) = one value (list of employees)

                
                var sortedEmployees = kvp.Value.OrderBy(e => e.LastName).ThenBy(e => e.FirstName).ToList(); // set order of emplyoees by last name, and if lastNames are the same then sort by first name
                
                for(int i = 0; i < sortedEmployees.Count; i++)
                {
                    Console.Write($"{sortedEmployees[i].FirstName} {sortedEmployees[i].LastName}"); //print out employee firt name and last name
                    
                    if(i != sortedEmployees.Count - 1) //if employee isnt the last in the list, then add comma and space for next employee name 
                    {
                        Console.Write(", ");
                    }
                }
                Console.WriteLine();
            }
        }
    }
}
