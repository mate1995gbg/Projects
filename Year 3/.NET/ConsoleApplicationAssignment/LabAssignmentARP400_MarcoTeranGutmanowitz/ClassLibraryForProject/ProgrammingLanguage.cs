using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public class ProgrammingLanguage
    {
        private static int NextProgrammingLanguageId = 1; //static property for autoincrementing id of objects "automatically"
        public int ProgrammingLanguageId { get; set; }
        public String LanguageName { get; set; }

        public ProgrammingLanguage(string languageName)
        {
            ProgrammingLanguageId = NextProgrammingLanguageId;
            this.LanguageName = languageName;
        }
    }
}
