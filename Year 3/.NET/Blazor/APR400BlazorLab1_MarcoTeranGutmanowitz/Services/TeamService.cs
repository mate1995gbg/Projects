using APR400BlazorLab1_MarcoTeranGutmanowitz.Model;
using Microsoft.EntityFrameworkCore;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Services
{
    public class TeamService
    {
        private readonly MyDbContext _context;
        public TeamService(MyDbContext context)
        {
            _context = context;
        }

        public async Task<List<Team>> GetTeamsAsync()
        {
            return await _context.Team.ToListAsync();
        }
        public async Task<Team> GetTeamByIdAsync(int id)
        {
            return await _context.Team.FindAsync(id);
        }
        public async Task<bool> AddTeamAsync(Team team)
        { 
            _context.Team.Add(team);
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> UpdateTeamAsync(Team team)
        {
            _context.Entry(team).State = EntityState.Modified; //sets the state of the player entity to "Modified." 
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> DeleteTeamAsync(int id)
        {
            var team = await GetTeamByIdAsync(id);
            if (team != null)
            {
                _context.Team.Remove(team);
            }
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<Team> GetTeamByPlayerAsync(Player player)
        {
            var matchingTphObject = _context.TeamPlayerHistory.FirstOrDefault(p => p.PlayerId == player.PlayerId);
            if (matchingTphObject == null)
            {
                return null; 
            }
            return await GetTeamByIdAsync(matchingTphObject.TeamId); 
        }
    }
}
