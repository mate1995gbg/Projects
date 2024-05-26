using Microsoft.EntityFrameworkCore;

namespace PersonalInfoAPI.Models
{
    public class PersonContext : DbContext
    {
        public DbSet<Person> Persons { get; set; }

        //SQLite
        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            options.UseSqlite("Data Source=persons.db");
        }
    }
}
