using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Migrations
{
    /// <inheritdoc />
    public partial class ModifiedRelationships : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Player_Team_CurrentTeamId",
                table: "Player");

            migrationBuilder.DropIndex(
                name: "IX_Player_CurrentTeamId",
                table: "Player");

            migrationBuilder.DropColumn(
                name: "CurrentTeamId",
                table: "Player");

            migrationBuilder.CreateTable(
                name: "TeamPlayerHistory",
                columns: table => new
                {
                    TeamPlayerHistoryId = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    PlayerId = table.Column<int>(type: "INTEGER", nullable: false),
                    TeamId = table.Column<int>(type: "INTEGER", nullable: false),
                    JoinDate = table.Column<DateTime>(type: "TEXT", nullable: false),
                    LeaveDate = table.Column<DateTime>(type: "TEXT", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TeamPlayerHistory", x => x.TeamPlayerHistoryId);
                    table.ForeignKey(
                        name: "FK_TeamPlayerHistory_Player_PlayerId",
                        column: x => x.PlayerId,
                        principalTable: "Player",
                        principalColumn: "PlayerId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_TeamPlayerHistory_Team_TeamId",
                        column: x => x.TeamId,
                        principalTable: "Team",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_TeamPlayerHistory_PlayerId",
                table: "TeamPlayerHistory",
                column: "PlayerId");

            migrationBuilder.CreateIndex(
                name: "IX_TeamPlayerHistory_TeamId",
                table: "TeamPlayerHistory",
                column: "TeamId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "TeamPlayerHistory");

            migrationBuilder.AddColumn<int>(
                name: "CurrentTeamId",
                table: "Player",
                type: "INTEGER",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Player_CurrentTeamId",
                table: "Player",
                column: "CurrentTeamId");

            migrationBuilder.AddForeignKey(
                name: "FK_Player_Team_CurrentTeamId",
                table: "Player",
                column: "CurrentTeamId",
                principalTable: "Team",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
