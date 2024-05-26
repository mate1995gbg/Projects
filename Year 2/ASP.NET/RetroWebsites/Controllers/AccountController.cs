//Detta är inloggningskontrollern. Den verifierar att en användare finns i databasen med funktionen längst ner-
//samt sätter cookies med hjälp av de magiska metoderna i Indexcontrollern-
//return URL används också för att returnera till inloggningsvyn ifall sessionen expirar (?)
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using RetroWebsites.Models;
using System.Security.Claims;

namespace RetroWebsites.Controllers
{
    public class AccountController : Controller
    {
        private List<User> users;
        public IActionResult Index(string returnUrl = "")
        {
            @ViewData["ReturnUrl"] = returnUrl;
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> Index(UserModel userModel, string returnUrl = "")
        {
            bool validUser = CheckUser(userModel);
            if(validUser == true)
            {
                //om CheckUser-metoden returnerar true så körs koden nedan. sätter en identity och claimar username.
                var identity = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
                identity.AddClaim(new Claim(ClaimTypes.Name, userModel.UserName));

                var userId = new ClaimsIdentity(CookieAuthenticationDefaults.AuthenticationScheme);
                userId.AddClaim(new Claim(ClaimTypes.Name, userModel.UserId.ToString())); //sätter UserId till en Auth Cookie via ClaimsPrincipal
                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(identity)); //signar in med identity-varen.. SignInAsync signar in asynkront (för att inte pausa hela programmet) obs att metodens returparameter nu är "async"
                if (returnUrl != "")
                {
                    return Redirect(returnUrl);
                }
                else
                {
                    return RedirectToAction("Index", "Nav");
                }
            }
            else
            {
                ViewBag.ErrorMessage = "Login was not sucessful. Try again!";
                @ViewData["ReturnUrl"] = returnUrl;
                return View();

            }
        }

        private bool CheckUser(UserModel userModel)
        {
            bool returnvalue = false;
            users = getUsers();
            foreach (var user in users)
            {
                if(userModel.UserName == user.User_username && userModel.Password == user.User_pw) 
                {
                    userModel.UserId = user.UserId; //sätter userId från databasen till modellens userId variabel.

                    returnvalue = true;
                }
                else
                {
                    returnvalue = false;  
                }
                
            }
            return returnvalue;
        }
        public List<User> getUsers() //hämtar users från databasen.
        {
            using (PostContext context = new PostContext())
            {
                users = new List<User>();
                users = context.Users.ToList();
                return users;
            }
        }

        public async Task<IActionResult> SignOutUser()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Index", "Account");
        }
    }
}
