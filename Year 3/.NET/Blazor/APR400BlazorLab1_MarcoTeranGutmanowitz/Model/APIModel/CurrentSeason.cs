namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model.APIModel
{
    public class CurrentSeason
    {
        public int Id { get; set; }
        public DateTime? StartDate { get; set; }
        public DateTime? EndDate { get; set; }
        public int? currentMatchday { get; set; }
        public Winner? winner { get; set; }
    }
}
