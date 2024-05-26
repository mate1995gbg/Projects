 using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public static class SalaryCalculator //here is my static class (D-criteria)
    {
        public static String CalculateEnhancedSalary(Programmer programmer)
        {
            double enhancedSalary = programmer.CurrentSalary;

            if (programmer.SpecialistLanguage.LanguageName == "C#")
            {
                enhancedSalary *= 1.1;
                programmer.CurrentSalary = enhancedSalary;
                return "salary for: " + programmer.FirstName + " has been updated.";
            }
            else
            {
                return "salary is unchanged due to specialist language not matching C#.";
            }

        }
    }
}
