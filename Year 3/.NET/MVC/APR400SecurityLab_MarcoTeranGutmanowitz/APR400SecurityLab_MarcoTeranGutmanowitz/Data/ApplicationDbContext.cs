using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>  //2 - have added <ApplicationUser> here
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
    }
}