using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class Programmer : Employee, IDetailed
    {
        public double BaseSalary { get; set; }
        public double CurrentSalary { get; set; }
        public ProgrammingLanguage SpecialistLanguage { get; set; }

        public int SkillLevel { get; set; }

        public Programmer(string firstName, string lastName, ProgrammingLanguage specialistLanguage, int skillLevel, string department)
            : base(firstName, lastName, department)
        {
            BaseSalary = 30000;
            CurrentSalary = BaseSalary;
            SpecialistLanguage = specialistLanguage;
            SkillLevel = skillLevel;

            PayRollNumber = GenerateEmployeeNumber();
            if (specialistLanguage.LanguageName == "C#")
            {
                CurrentSalary *= 1.1;
            }
        }

        public void ChangeSpecialization(ProgrammingLanguage newSpecialization)
        {
            SpecialistLanguage = newSpecialization;
            CurrentSalary = BaseSalary;
            if (newSpecialization.LanguageName == "C#")
            {
                CurrentSalary *= 1.1;
            }
        }

        public Programmer(string firstName, string lastName,
                         double baseSalary, double currentSalary, ProgrammingLanguage specialistLanguage, string department)
            : base(firstName, lastName, department)
        {
            BaseSalary = baseSalary;
            CurrentSalary = currentSalary;
            SpecialistLanguage = specialistLanguage;
            PayRollNumber = GenerateEmployeeNumber();
        }

        public override string ToString()
        {
            return $"Programmer's name is: {base.FirstName}. His current salary is: {CurrentSalary}. " +
                   $"Specialized in programming language: {SpecialistLanguage.LanguageName}";
        }

        public override string GetDetails()
        {
            return $"Name: {FirstName} {LastName}, Language: {SpecialistLanguage.LanguageName}, Skill Level: {SkillLevel}";
        }
    }
}
