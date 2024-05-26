//Min db modell, inget speciellt här 
using Microsoft.EntityFrameworkCore;

namespace RetroWebsites.Models
{
    public class Post
    {
        public int PostId { get; set; }
        public string PostUrl { get; set; }
        public string PostDescription { get; set; }
        public string ImageURL { get; set; }

    }
    public class User
    {
        public int UserId { get; set; }
        public string User_username { get; set; }
        public string User_pw { get; set; }
        public string User_email { get; set; }


    }
}
