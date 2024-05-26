namespace PortfolioAPI.Models
{
    public class KnownProgrammingLanguage
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string? subName { get; set; }
        public string? Description { get; set; }
        public int SkillLevel { get; set; }
        public DateTime StartedLearning { get; set; }


    }
}
