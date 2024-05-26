using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Build.Framework;
using System.ComponentModel.DataAnnotations;
using RequiredAttribute = Microsoft.Build.Framework.RequiredAttribute;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Areas.Identity.Pages.Account.Manage
{
    public class ChangeAgeModel : PageModel
    {
        private readonly UserManager<ApplicationUser> _userManager;

        public ChangeAgeModel(UserManager<ApplicationUser> userManager)
        {
            _userManager = userManager;
        }

        public int CurrentAge { get; set; } //to show currentage in the 

        public class InputModel
        {
            [Required]
            [Range(0, 120)]
            public int Age { get; set; }
        }

        [BindProperty]
        public InputModel Input { get; set; }

        public async Task<IActionResult> OnGetAsync()
        {
            var user = await _userManager.GetUserAsync(User);
            if (user == null) 
            {
                return NotFound("Unable to load user.");
            }

            CurrentAge = user.Age;
            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            var user = await _userManager.GetUserAsync(User);
            if (user == null)
            {
                return NotFound("Unable to load user.");
            }
            else
            {
                user.Age = Input.Age;
            }
            await _userManager.UpdateAsync(user);

            return RedirectToPage();
        }
    }
}
