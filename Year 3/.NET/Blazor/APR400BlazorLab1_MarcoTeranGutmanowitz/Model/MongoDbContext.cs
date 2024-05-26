using MongoDB.Driver;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{

    public class MongoDbContext
    {
        public IMongoCollection<Trainer> MyTrainers { get; set; }
        public IMongoCollection<TrainerTeam> MyTrainerTeams { get; set; }

        public MongoDbContext(IMongoDatabase database)
        {
            MyTrainers = database.GetCollection<Trainer>("trainers");
            MyTrainerTeams = database.GetCollection<TrainerTeam>("teams");

        }
    }
}
