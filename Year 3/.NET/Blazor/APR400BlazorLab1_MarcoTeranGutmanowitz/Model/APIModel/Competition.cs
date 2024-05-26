namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model.APIModel
{
    public class Competition
    {
        public int Id { get; set; }
        public Area area { get; set; }
        public string Name { get; set; }
        public string Code { get; set; }
        public string Type { get; set; }
        public string Emblem { get; set; }
        public string Plan { get; set; }
        public CurrentSeason? CurrentSeason { get; set; }
        public int NumberOfAvailableSeason { get; set; }
        public DateTime lastUpdated { get; set; }
    }
}
