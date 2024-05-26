using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class Mentor : Programmer, IDetailed
    {
        public List<Mentee> Mentees { get; set; }
        public bool EligibleMentor { get; set; }

        public Mentor(string firstName, string lastName, ProgrammingLanguage specialistLanguage, int skillLevel, string department)
            : base(firstName, lastName, specialistLanguage, skillLevel, department)
        {
            Mentees = new List<Mentee>();
            EligibleMentor = true;
        }

        public static void AddMenteeToMentor(Mentor mentor, Mentee mentee)
        {
            if (mentor.SpecialistLanguage != mentee.SpecialistLanguage)
            {
                throw new MentorSkillLevelException("Mentee's specialist language does not match the mentors!"); //here my own exception class errors are defined.
            }
            if (mentor.SkillLevel <= mentee.SkillLevel)
            {
                throw new MentorSkillLevelException("Mentors skill levels is equal to or lower than the mentees!"); //here my own exception class errors are defined.
            }

            mentee.Mentor = mentor;
            mentor.Mentees.Add(mentee);

            // Calculate the enhancement only once based on the current number of mentees
            double salaryEnhancement = mentor.Mentees.Count * 0.05 * mentor.CurrentSalary;
            mentor.CurrentSalary += salaryEnhancement;
        }

        public static void RemoveMenteeFromMentor(Mentor mentor, Mentee mentee)
        {
            mentee.Mentor = null;
            mentor.Mentees.Remove(mentee);

            // Calculate the reduction only once based on the current number of mentees
            double salaryReduction = mentor.Mentees.Count * 0.05 * mentor.BaseSalary;
            mentor.CurrentSalary -= salaryReduction;
        }

        public override string GetDetails()
        {
            string menteeNames = string.Join(",", Mentees.Select(m => m.FirstName + m.LastName));
            return base.GetDetails() + $" ElegibleMentor status: {EligibleMentor} Mentees: {menteeNames}" ;
        }
    }
}
