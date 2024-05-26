using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Components
{
    public class ProfilePictureViewComponent : ViewComponent
    {
        private readonly UserManager<ApplicationUser> _userManager;

        public ProfilePictureViewComponent(UserManager<ApplicationUser> userManager)
        {
            userManager = _userManager;
        }

        public async Task<IViewComponentResult> InvokeAsync()
        {
            if (HttpContext != null && HttpContext.User != null)
            {
                var user = await _userManager.GetUserAsync(HttpContext.User);
                // Do something with user.
                return View(user?.ProfilePictureUrl);
            }
            else
            {
                // Handle error, perhaps log it or set a default value.
                return View();
            }
            
        }
    }
}
