using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Models
{
    public class ApplicationUser : IdentityUser
    {
        [Required, MaxLength(100)] //1 - makes it obligatory to enter firstname & maxcharacters 100
        public string FirstName { get; set; }

        [Required, MaxLength(100)]
        public string LastName { get; set; }

        [Required]
        public int Age { get; set; }

        [Required]
        public string Gender { get; set; }

        public string? ProfilePictureUrl { get; set; }
    }
}
