using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class MentorSkillLevelException : Exception //this is my own custom exception class (B-criteria)
    {
        public MentorSkillLevelException() { }

        public MentorSkillLevelException(string message)
            : base(message) { }

        public MentorSkillLevelException(string message, Exception inner)
            : base(message, inner) { }
    }
}
