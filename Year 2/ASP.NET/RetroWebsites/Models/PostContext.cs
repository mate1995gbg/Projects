using Microsoft.EntityFrameworkCore;

namespace RetroWebsites.Models
{
    public class PostContext : DbContext
    {
        public DbSet<Post> Posts { get; set; }
        public DbSet<User> Users { get; set; }
        public PostContext()
        {
            Database.EnsureCreated();
        }
        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            options.UseSqlite("DataSource = ProjektDB.db");
            //Databasfilens namn
        }
    }
}
