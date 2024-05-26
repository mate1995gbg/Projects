namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class TeamPlayerHistory //many-to-many table
    {
        public int? TeamPlayerHistoryId { get; set; }
        public int PlayerId { get; set; }
        public Player Player { get; set; }
        public int TeamId { get; set; }
        public Team Team { get; set; }
        public DateTime JoinDate { get; set; }
        public DateTime? LeaveDate { get; set; }
    }
}
