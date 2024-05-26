using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class Mentee : Programmer, IDetailed
    {
        public Mentor Mentor { get; set; }
        public bool EligibleMentee { get; set; }

        public Mentee(string firstName, string lastName, ProgrammingLanguage specialistLanguage, Mentor mentor, int skillLevel, string department)
            : base(firstName, lastName, specialistLanguage, skillLevel, department)
        {
            EligibleMentee = true;
            this.Mentor = mentor;
        }

        public Mentee(Programmer mentee, Mentor mentor)
            : base(mentee.FirstName, mentee.LastName, mentee.SpecialistLanguage, mentee.SkillLevel, mentee.Department)
        {
            Mentor = mentor;
            this.EligibleMentee = true;

        }

        public override string GetDetails()
        {
            return base.GetDetails() + $", Mentee status: {EligibleMentee}, Mentor: {Mentor}";
        }
    }
}
