using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Controllers
{
    [Authorize(Roles = "Admin")]
    public class AdminController : Controller
    {
        private readonly RoleManager<IdentityRole> _roleManager;
        private readonly UserManager<ApplicationUser> _userManager;
        
        public AdminController(RoleManager<IdentityRole> roleManager, UserManager<ApplicationUser> userManager)
        {
            _roleManager = roleManager;
            _userManager = userManager;
        }

        public async Task<IActionResult> Index()
        {
            var roles = _roleManager.Roles.ToList();
            return View(roles);
        }

        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Create(RoleViewModel model)
        {
            if (!String.IsNullOrEmpty(model.RoleName))
            {
                var result = await _roleManager.CreateAsync(new IdentityRole(model.RoleName));
                if(result.Succeeded) 
                {
                    return RedirectToAction("Index");
                }
                else
                {
                    return RedirectToAction("Error");
                }
            }
            else
            {
                return RedirectToAction("Error");
            }
        }

        public async Task<IActionResult> UsersQuick()
        {
            var users = await _userManager.Users.ToListAsync();
            var roles = await _roleManager.Roles.ToListAsync();
            var userRoles = new Dictionary<string, List<String>>();

            foreach (var user in users)
            {
                var rolesForUser = await _userManager.GetRolesAsync(user);
                if (rolesForUser != null)
                {
                    userRoles.Add(user.Id, rolesForUser.ToList());
                }
            }

            ViewBag.Users = users;
            ViewBag.Roles = roles; 
            ViewBag.UserRoles = userRoles; 
            return View();
        }

        [HttpGet]
        public async Task<IActionResult> UsersLong()
        {
            var users = await _userManager.Users.ToListAsync();
            var roles = await _roleManager.Roles.ToListAsync();
            var userRoles = new Dictionary<string, List<string>>();

            foreach (var user in users)
            {
                var rolesForUser = await _userManager.GetRolesAsync(user); 
                if (rolesForUser != null) 
                {
                    userRoles.Add(user.Id, rolesForUser.ToList());
                }
            }

            var model = new ManageUsersViewModel(); 
            model.Users = users;
            model.Roles = roles;
            model.UserRoles = userRoles;

            return View(model);
        }

        [HttpPost] 
        public async Task<IActionResult> UsersLong(ApplicationUser user, IdentityRole role)
        {
            if(user != null && role != null) 
            {
                await _userManager.AddToRoleAsync(user, role.ToString());
                return View();
            }
            else
            {
                return RedirectToAction("Error");
            }
        }

        [HttpPost]
        public async Task<IActionResult> RemoveRole(string userId, string roleName)
        {
            var user = await _userManager.FindByIdAsync(userId);
            var role = await _roleManager.FindByNameAsync(roleName);
            if (user != null && role != null)
            {
                var result = await _userManager.RemoveFromRoleAsync(user, roleName);

                if (result.Succeeded)
                {
                    return RedirectToAction("UsersLong"); // Redirect to your view that shows all users and roles
                }
                else
                {
                    // Handle errors here, e.g., by displaying them in the view
                    return View("Error");
                }
            }
            else
            {
                // Handle errors here, e.g., user or role not found
                return View("Error");
            }
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Delete(string id)
        {
            var role = await _roleManager.FindByIdAsync(id);

            if (role == null)
            {
                return NotFound(); // or handle the error as needed
            }

            var result = await _roleManager.DeleteAsync(role);

            if (result.Succeeded)
            {
                return RedirectToAction("Index");
            }
            else
            {
                // Handle the error, for example, by displaying a message to the user.
                ModelState.AddModelError(string.Empty, "Role deletion failed.");
                return RedirectToAction("Index");
            }
        }

        [HttpPost]  
        public async Task<IActionResult> AddRole(string userId, string roleName)
        {
            var user = await _userManager.FindByIdAsync(userId);
            var role = await _roleManager.FindByNameAsync(roleName);

            if (user != null && role != null)
            {
                var result = await _userManager.AddToRoleAsync(user, roleName);

                if (result.Succeeded)
                {
                    return RedirectToAction("UsersLong"); // Redirect to your view that shows all users and roles
                }
                else
                {
                    // Handle errors here, e.g., by displaying them in the view
                    return View("Error");
                }
            }
            else
            {
                // Handle errors here, e.g., user or role not found
                return View("Error");
            }
        }

    }
}
