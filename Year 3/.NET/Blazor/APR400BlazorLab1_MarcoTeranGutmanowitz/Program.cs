using APR400BlazorLab1_MarcoTeranGutmanowitz.Data;
using APR400BlazorLab1_MarcoTeranGutmanowitz.Model;
using APR400BlazorLab1_MarcoTeranGutmanowitz.Services;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.EntityFrameworkCore;
using Blazored.Modal;
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<MyDbContext>(options =>
options.UseSqlite(builder.Configuration.GetConnectionString("SQLiteDatabase")));

// Add services to the container.
builder.Services.AddRazorPages(); 
builder.Services.AddServerSideBlazor();
builder.Services.AddSingleton<WeatherForecastService>();
builder.Services.AddSingleton<MongoDbService>(); //// Register service for mongodb
builder.Services.AddScoped<PlayerService>();
builder.Services.AddScoped<TeamService>();
builder.Services.AddScoped<TeamPlayerHistoryService>();
builder.Services.AddBlazoredModal();
builder.Services.Configure<MongoDbSettings>(builder.Configuration.GetSection("MongoDbSettings")); // This line is used to configure the app's settings from appsettings.json
builder.Services.AddScoped(sp => new HttpClient());

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}
   

app.UseStaticFiles();

app.UseRouting();

app.MapBlazorHub();
app.MapFallbackToPage("/_Host");

app.Run();
