namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model.APIModel
{
    public class CompetitionResponse
    {
        public int Count { get; set; }
        public Dictionary<string, object> Filters { get; set; }
        public List<Competition> Competitions { get; set; }
    }
}
