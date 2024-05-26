using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassLibraryForProject
{
    public abstract class Person : Company, IDetailed //this is my abstract class (C-criteria)
    {
        public String FirstName { get; set; }
        public String LastName { get; set; }

        public Person(string firstName, string lastName)
        {
            LastName = lastName;
            FirstName = firstName;
        }

        public abstract string GetDetails();
    }

}
