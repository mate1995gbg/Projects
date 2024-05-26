namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class Player
    {
        public int PlayerId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime DateOfBirth { get; set; }
        public PlayerPosition PlayerPosition { get; set; }

        //public int CurrentTeamId { get; set; } //this line and the one below would imply a one-to-many relationship 
        //public Team CurrentTeam { get; set; }

        public ICollection<TeamPlayerHistory> TeamPlayerHistories { get; set; } //this would imply a many-to-many relationship between Player and TeamPlayerHistory
    }
}
