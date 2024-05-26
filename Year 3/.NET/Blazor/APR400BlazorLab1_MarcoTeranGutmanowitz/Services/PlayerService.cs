using APR400BlazorLab1_MarcoTeranGutmanowitz.Model;
using Microsoft.EntityFrameworkCore;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Services
{
    public class PlayerService
    {
        private readonly MyDbContext _context;

        public PlayerService(MyDbContext context)
        {
            _context = context;
        }

        public async Task<List<Player>> GetPlayersAsync()
        {
            return await _context.Player.ToListAsync();
        }
        public async Task<Player> GetPlayerByIdAsync(int id)
        {
            return await _context.Player.FindAsync(id);
        }
        public async Task<Player> GetPlayerByNameAsync(Player player)
        {
            return await _context.Player.FirstOrDefaultAsync(p => p.FirstName == player.FirstName && p.LastName == player.LastName);
        }
        public async Task<bool> AddPlayerAsync(Player player)
        {
            _context.Player.Add(player);
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> UpdatePlayerAsync(Player player)
        {
            _context.Entry(player).State = EntityState.Modified; //sets the state of the player entity to "Modified." 
            return (await _context.SaveChangesAsync()) > 0;
        }
        public async Task<bool> DeletePlayerAsync(int id)
        {
            var player = await GetPlayerByIdAsync(id);
            if (player != null)
            {
                _context.Player.Remove(player);
            }
            return (await _context.SaveChangesAsync()) > 0;
        }
    }
}
