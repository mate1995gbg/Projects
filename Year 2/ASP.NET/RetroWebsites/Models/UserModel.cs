namespace RetroWebsites.Models
{
    public class UserModel /*//används som mall av formuläret i /Account/Index för att för över data till Controllern*/
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public int UserId { get; set; }
    }
}
