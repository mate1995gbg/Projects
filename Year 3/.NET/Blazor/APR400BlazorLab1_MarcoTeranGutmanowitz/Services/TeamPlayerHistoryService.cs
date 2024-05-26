using APR400BlazorLab1_MarcoTeranGutmanowitz.Model;
using Microsoft.EntityFrameworkCore;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Services
{
    public class TeamPlayerHistoryService
    {
        private readonly MyDbContext _context;
        public TeamPlayerHistoryService(MyDbContext context)
        {
            _context = context;
        }

        //This method retrieves all team player history entries, groups them by player ID, and creates a dictionary.
        public async Task<Dictionary<int, List<TeamPlayerHistory>>> GetAllTeamPlayerHistoriesAsync() 
        {
            return await _context.TeamPlayerHistory
                .GroupBy(tp => tp.PlayerId)
                .ToDictionaryAsync(g => g.Key, g => g.ToList());
        }

        public async Task<List<TeamPlayerHistory>> GetTeamPlayerHistoryListByPlayerIdAsync(int playerId)
        {
            return await _context.TeamPlayerHistory.Where(tp => tp.PlayerId == playerId).ToListAsync();
        }

        public async Task<List<TeamPlayerHistory>> GetTeamPlayerHistoryListByTeamIdAsync(int teamId)
        {
            return await _context.TeamPlayerHistory.Where(tp => tp.TeamId == teamId).ToListAsync();
        }

        public async Task<bool> AddTeamPlayerHistoryAsync(TeamPlayerHistory teamPlayerHistoryObject)
        {
            var latestHistoryRecord = await _context.TeamPlayerHistory.Where(tp => tp.PlayerId == teamPlayerHistoryObject.PlayerId
                                                                            && tp.TeamId == teamPlayerHistoryObject.TeamId)
                                                                            .OrderByDescending(tp => tp.JoinDate)
                                                                            .FirstOrDefaultAsync();
            if (latestHistoryRecord != null)
            {
                latestHistoryRecord.LeaveDate = DateTime.Now;
                teamPlayerHistoryObject.JoinDate = DateTime.Now;
            }

            _context.TeamPlayerHistory.Add(teamPlayerHistoryObject);
            return (await _context.SaveChangesAsync()) > 0;
        }

        public async Task<bool> UpdateTeamPlayerHistoryObjectAsync(TeamPlayerHistory teamPlayerHistoryObject)
        {
            _context.Entry(teamPlayerHistoryObject).State = EntityState.Modified; 
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> DeletePlayerRecordInTphByPlayerIdAsync(int playerId)
        {
            var teamPlayerHistoryRecord = await GetTeamPlayerHistoryListByPlayerIdAsync(playerId);
            if (teamPlayerHistoryRecord != null)
            {
                foreach (TeamPlayerHistory tph in teamPlayerHistoryRecord)
                {
                    _context.TeamPlayerHistory.Remove(tph);
                }
            }
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> DeleteTeamRecordInTphByTeamIdAsync(int teamId)
        {
            var teamPlayerHistoryRecord = await GetTeamPlayerHistoryListByTeamIdAsync(teamId);
            if (teamPlayerHistoryRecord != null)
            {
                foreach (TeamPlayerHistory tph in teamPlayerHistoryRecord)
                {
                    _context.TeamPlayerHistory.Remove(tph);
                }
            }
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<TeamPlayerHistory> GetLatestTphByPlayerIdAsync(Player player)
        {
            return await _context.TeamPlayerHistory
                .Where(tp => tp.PlayerId == player.PlayerId)
                .OrderByDescending(tp => tp.JoinDate).FirstOrDefaultAsync();
        }
    }
}
