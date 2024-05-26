using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Controllers
{
    public class UserController : Controller
    {

        private readonly RoleManager<IdentityRole> _roleManager;
        private readonly UserManager<ApplicationUser> _userManager;
        private IWebHostEnvironment _env;

        public UserController(RoleManager<IdentityRole> roleManager, UserManager<ApplicationUser> userManager, IWebHostEnvironment env)
        {
            _roleManager = roleManager;
            _userManager = userManager;
            _env = env;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult ProfilePicture()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> ProfilePicture(IFormFile ProfilePicture)
        {
            var user = await _userManager.GetUserAsync(User);

            if (ProfilePicture != null && ProfilePicture.Length > 0)
            {
                string wwwPath = this._env.WebRootPath;
                string path = Path.Combine(wwwPath, "lib/images");
                if(!Directory.Exists(path))
                {
                    Directory.CreateDirectory(path);  //create wwwrooth/lib/images if not exists
                }

                string filename = Path.GetFileName(ProfilePicture.FileName);
                using(FileStream stream = new FileStream(Path.Combine(path, filename), FileMode.Create))
                {
                    ProfilePicture.CopyTo(stream);  //upload file
                }

                string imagePath = "/lib/images";
                string imageUrl = Path.Combine(imagePath, filename);
                user.ProfilePictureUrl = imageUrl;

                var result = await _userManager.UpdateAsync(user);

                if (!result.Succeeded)
                {
                    // Handle the error here
                    foreach (var error in result.Errors)
                    {
                        ModelState.AddModelError(string.Empty, error.Description);
                    }
                    return View();  // or however you wish to handle this
                }
            }

            return RedirectToAction("ProfilePage");
        }
    }
}
