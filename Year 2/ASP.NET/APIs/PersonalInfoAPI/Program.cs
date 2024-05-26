using PersonalInfoAPI.Models;

namespace PersonalInfoAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            //SQLite
            builder.Services.AddEntityFrameworkSqlite().AddDbContext<PersonContext>();

            builder.Services.AddCors(options => options.AddPolicy("MarcoReactPolicy", builder => //här skapas policyn som skall användas av CORS-grejen.
            builder.AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader()));

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }
            app.UseCors("MarcoReactPolicy");
            app.UseAuthorization();


            app.MapControllers();

            app.Run();
        }
    }
}