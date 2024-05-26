using Microsoft.AspNetCore.Identity;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Models
{
    public class ManageUsersViewModel
    {
        public List<ApplicationUser> Users { get; set; }
        public List<IdentityRole> Roles { get; set; }
        public Dictionary<string, List<string>> UserRoles { get; set; }

    }
}
