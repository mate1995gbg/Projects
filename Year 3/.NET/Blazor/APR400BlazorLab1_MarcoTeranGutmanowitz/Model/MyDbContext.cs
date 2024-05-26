using Microsoft.EntityFrameworkCore;
namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class MyDbContext : DbContext
    {
        public MyDbContext(DbContextOptions<MyDbContext> options) : base(options)
        {
        }
        public DbSet<Player> Player { get; set; }
        public DbSet<Team> Team { get; set; }

        public DbSet<TeamPlayerHistory> TeamPlayerHistory { get; set; }
        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            options.UseSqlite(@"DataSource = FootballDB.db");
        }

    }
}
