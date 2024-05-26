using Microsoft.AspNetCore.Authentication.Cookies; //lägg till när du skall använda Authorize

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();


builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme).AddCookie(options => { options.LoginPath = "/Account/Index/"; }); //detta lägger till stöd för loginfunktionen. obs LoginPath obs authentication
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthentication(); //aktiverar authentication. Viktigt! OBS MÅSTE VARA FÖRE UseAuthorization!!!

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Account}/{action=Index}/{id?}"); //defaultrutten 

app.Run();
