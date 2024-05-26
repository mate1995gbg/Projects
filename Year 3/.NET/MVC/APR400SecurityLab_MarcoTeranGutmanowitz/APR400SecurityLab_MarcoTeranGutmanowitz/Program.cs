using APR400SecurityLab_MarcoTeranGutmanowitz.Data;
using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity.UI.Services;
using APR400SecurityLab_MarcoTeranGutmanowitz.Services;


namespace APR400SecurityLab_MarcoTeranGutmanowitz
{
    public class Program
    {
        public static async Task Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Configuration.AddUserSecrets<Program>(); //need this to use user secrets (they popuklate the variables ApiKey & ApiSecret in appsettings.json)

            // Add services to the container.
            var connectionString = builder.Configuration.GetConnectionString("DefaultConnection") ?? throw new InvalidOperationException("Connection string 'DefaultConnection' not found.");
            builder.Services.AddDbContext<ApplicationDbContext>(options =>
                options.UseSqlServer(connectionString));


            //3- change to this addidentity instead of old one below
            builder.Services.AddIdentity<ApplicationUser, IdentityRole>(options =>
            {
                options.SignIn.RequireConfirmedAccount = false; //set to false to avoid confirming account during registry

                //set password complexity
                options.Password.RequireDigit = true;
                options.Password.RequiredLength = 10;
                //options.Password.RequireNonAlphanumeric = true;
                options.Password.RequireUppercase = true;
                options.Password.RequireLowercase = true;
                options.Password.RequiredUniqueChars = 6;
            }) 
                .AddDefaultUI() //bibliotek som beh�vs l�ggas till
                .AddDefaultTokenProviders() //bibliotek som ijnneh�ller services som beh�vs f�r att arbeta med ny identity.
                .AddEntityFrameworkStores<ApplicationDbContext>();

            builder.Services.AddDatabaseDeveloperPageExceptionFilter();
            builder.Services.AddControllersWithViews();

            //add authorization policy (driving license)
            builder.Services.AddAuthorization(options =>
            {
                options.AddPolicy("ControlDriversLicense", policy =>
                policy.RequireClaim("DrivingLicense", "B"));
            });

            //for MailJet 
            builder.Services.AddTransient<IEmailSender, MailJetEmailSender>();
            builder.Services.Configure<MailJetSettings>(builder.Configuration.GetSection("MailJetSettings")); //to be able to use dependency injection on other classes to get values for ApiKey, FromEmail & SecretKey

            var app = builder.Build();

            //Seeding START

            using (var scope = app.Services.CreateScope())
            {
                var serviceProvider = scope.ServiceProvider;
                var roleManager = serviceProvider.GetRequiredService<RoleManager<IdentityRole>>();
                var userManager = serviceProvider.GetRequiredService<UserManager<ApplicationUser>>();

                //seed roles
                var roles = new List<string> { "Admin", "User" };
                foreach (var role in roles)
                {
                    if (!await roleManager.RoleExistsAsync(role))
                    {
                        await roleManager.CreateAsync(new IdentityRole(role)); 
                    }
                }

                //seed an admin user
                var adminUser = new ApplicationUser { FirstName = "Admin", LastName = "Admin", Gender = "male", Age = 25, UserName = "admin", Email = "admin@example.com", EmailConfirmed = true }; //create admin user
                if (await userManager.FindByNameAsync("admin") == null)
                {
                    var result = await userManager.CreateAsync(adminUser, "Password123!");
                    if (result.Succeeded)
                    {
                        await userManager.AddToRolesAsync(adminUser, new[] { "Admin", "User" }); //assign all roles to adminUser
                    }
                }

            }

            //Seeding END

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseMigrationsEndPoint();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            app.MapControllerRoute(  //this enables use of MVC architecture
                name: "default",
                pattern: "{controller=Home}/{action=Index}/{id?}");
            app.MapRazorPages();  //this enables use of Razor pages

            app.Run();
        }
    }
}