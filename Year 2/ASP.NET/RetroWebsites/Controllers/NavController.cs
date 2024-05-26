//Hela Controllern använder Authorization --> (G)
using Microsoft.AspNetCore.Mvc;
using RetroWebsites.Models;
using System.IO;
using Microsoft.AspNetCore.Http; //Required for IFromFile
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Authorization;

namespace RetroWebsites.Controllers
{
    [Authorize] /*Hela Controllern använder Authorization --> (G)*/
    public class NavController : Controller
    {
        private IWebHostEnvironment env; //deklarerar en IWebHostEnvironment variabel för att kunna hitta "Root Path". se andra Create metoden nedan.

        public NavController(IWebHostEnvironment _env)
        {
            env = _env;
        }

        [Route("Index")]
        public IActionResult Index()
        {
            using (PostContext context = new PostContext()) /*//skapar en db connection*/
            {
                List<Post> postList = context.Posts.ToList(); /*hämtar en lista av Posts från dbn (G)*/
                return View(postList); /*//visar upp listan */
            }

        }
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

        [RequestFormLimits(MultipartBodyLengthLimit = 5000000)] //sätter maxfilesize till 5 mb
        [HttpPost] //körs när en postmetod detekteras (typ)
        public IActionResult Create(Post newPost, IFormFile postedImage) //denna körs endast när formen submittas i den första createmetoden ovan.
        {
            string wwwPath = this.env.WebRootPath;
            string path = Path.Combine(wwwPath, "lib/images");
            if (Directory.Exists(path))
            {
                Directory.CreateDirectory(path); //skapar sökvägen om den ej finns.
            }
            string filename = Path.GetFileName(postedImage.FileName);
            using (FileStream stream = new FileStream(Path.Combine(path, filename), FileMode.Create))
            {
                ViewBag.Message = string.Format("<b>{0}</b>uploaded.<br>", filename);
                postedImage.CopyTo(stream);
            }
            string imagePath = "lib/images";
            string imageUrl = Path.Combine(imagePath, filename);
            newPost.ImageURL = imageUrl; //lägger till postens imageUrl från variablerna path &filename.
            using (PostContext context = new PostContext())
            {
                context.Posts.Add(newPost); //lägger till ny post objektet.
                context.SaveChanges(); //sparar ändringar i db.
            }
            return RedirectToAction("Index"); //tar dig helt enkelt till index.
        }
        public IActionResult Search()
        {
            return View();
        }
        public IActionResult About()
        {
            return View();
        }
        public IActionResult Weather()
        {
            return View();
        }


    }
}
