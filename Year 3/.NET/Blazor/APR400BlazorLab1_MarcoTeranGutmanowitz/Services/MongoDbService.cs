using APR400BlazorLab1_MarcoTeranGutmanowitz.Model;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Services
{
    public class MongoDbService
    {
        private readonly IMongoDatabase _database;
        private readonly MongoDbContext _context;

        public MongoDbService(IOptions<MongoDbSettings> settings)
        {
            var client = new MongoClient(settings.Value.ConnectionString);
            _database = client.GetDatabase(settings.Value.DatabaseName);

            _context = new MongoDbContext(_database);
        }

        public async Task<List<Trainer>> GetAllTrainers()
        {
            return await _context.MyTrainers.Find(_ => true).ToListAsync();
        }

        public async Task AddTrainer(Trainer trainer)
        {
            await _context.MyTrainers.InsertOneAsync(trainer);
        }

        // BELOW ARE TRAINER TEAM METHODS
        public async Task<List<TrainerTeam>> GetAllTrainerTeams()
        {
            return await _context.MyTrainerTeams.Find(_ => true).ToListAsync();
        }
        public async Task<TrainerTeam> GetTrainerTeamViaTeamId(Trainer trainer)
        {
            var filter = Builders<TrainerTeam>.Filter.Eq(t => t.id, trainer.team_id); //the Find-method of class MongoDbContext requires a "filter". 
            return await _context.MyTrainerTeams.Find(filter).FirstOrDefaultAsync();
        }
    }
}
