using Microsoft.EntityFrameworkCore;

namespace PortfolioAPI.Models
{
    public class KnownProgramLanguageContext : DbContext
    {
        public DbSet<KnownProgrammingLanguage> knownProgrammingLanguages { get; set; } //varje record i dbn skall vara av typen knownprogramminglanguage.
        public DbSet<Person> Persons { get; set; }
        public KnownProgramLanguageContext(DbContextOptions options) : base(options) //constructorn.
        {

        }
    }
}
