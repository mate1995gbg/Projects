using Microsoft.EntityFrameworkCore; //m�ste ha med dessa tv�
using PortfolioAPI.Models;

namespace PortfolioAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.
            //nedan kopplar ihop DbContexten till Servern.
            builder.Services.AddDbContext<KnownProgramLanguageContext>(options => options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));
            //<- vid options.useSqlServer hade du kunnat skriva Sqllite om du vilel ha sqllite.
            //<- vid str�ngen i GetConnectionString s� m�ste det vara samma som i appsettings.json.
            builder.Services.AddCors(options => options.AddPolicy("MarcoReactPolicy", builder => //h�r skapas policyn som skall anv�ndas av CORS-grejen.
            builder.AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader()));

            builder.Services.AddControllers();
            builder.Services.AddControllers();

            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            //if (app.Environment.IsDevelopment())
            //{
                app.UseSwagger();
                app.UseSwaggerUI();
            //}
            app.UseCors("MarcoReactPolicy");
            app.UseAuthorization();
            app.MapControllers().RequireCors("MarcoReactPolicy");

            app.Run();
        }
    }
}