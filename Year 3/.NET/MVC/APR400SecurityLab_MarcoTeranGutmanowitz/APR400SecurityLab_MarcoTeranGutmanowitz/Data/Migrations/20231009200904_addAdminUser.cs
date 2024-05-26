using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Data.Migrations
{
    public partial class addAdminUser : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.Sql("INSERT INTO [dbo].[AspNetUsers] ([Id], [UserName], [NormalizedUserName], [Email], [NormalizedEmail], [EmailConfirmed], [PasswordHash], [SecurityStamp], [ConcurrencyStamp], [PhoneNumber], [PhoneNumberConfirmed], [TwoFactorEnabled], [LockoutEnd], [LockoutEnabled], [AccessFailedCount], [FirstName], [LastName], [Age], [Gender]) VALUES (N'7f78881a-5f4d-4949-8816-eb1cd69e9947', N'admin', N'ADMIN', N'admin@test.com', N'ADMIN@TEST.COM', 0, N'AQAAAAEAACcQAAAAED6iaRPyXWnoVSaKVggIRsHs39mjYUQAWz+zwKC0uIxSzVYD3Dk57f/JqwBBRKp6vg==', N'K3275HXWBKGXHCW6Q7XGT36AI6ZRKHYD', N'11b96cb3-b3a1-4117-813f-5d860dbb4fe7', NULL, 0, 0, NULL, 1, 0, N'Abdullah', N'Abbas', 25, N'male')");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.Sql("DELETE FROM [AspNetUsers] WHERE ID = '4338ca25-1ec0-475c-838f-2a411d46f09b' ");
        }
    }
}
