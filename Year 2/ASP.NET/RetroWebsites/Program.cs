using Microsoft.AspNetCore.Authentication.Cookies; //l�gg till n�r du skall anv�nda Authorize

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();


builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme).AddCookie(options => { options.LoginPath = "/Account/Index/"; }); //detta l�gger till st�d f�r loginfunktionen. obs LoginPath obs authentication
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthentication(); //aktiverar authentication. Viktigt! OBS M�STE VARA F�RE UseAuthorization!!!

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Account}/{action=Index}/{id?}"); //defaultrutten 

app.Run();
