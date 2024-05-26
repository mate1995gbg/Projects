namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class Team
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime FoundedDate { get; set; }
        public string StadiumName { get; set; }
        public int ChampionshipsWon { get; set; }

        //public ICollection<Player> Players { get; set; } //this would imply a one-to-many relationship between team and Player
        public ICollection<TeamPlayerHistory> TeamPlayerHistories { get; set; }
    }
}
