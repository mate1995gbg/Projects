using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PortfolioAPI.Models;

namespace PortfolioAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProgrammingLanguageController : ControllerBase
    {
        private readonly KnownProgramLanguageContext _context; //_context representerar databasen!

        public ProgrammingLanguageController(KnownProgramLanguageContext context)
        {
            _context = context;
        }

        // GET: api/ProgrammingLanguage
        [HttpGet]
        public async Task<ActionResult<IEnumerable<KnownProgrammingLanguage>>> GetknownProgrammingLanguages()
        {
          if (_context.knownProgrammingLanguages == null)
          {
              return NotFound();
          }
            return await _context.knownProgrammingLanguages.ToListAsync(); //hämta all data av typen knownprogramminglanguages och konvertera itll lista. och returna.
        }

        // GET: api/ProgrammingLanguage/5
        [HttpGet("{id}")]
        public async Task<ActionResult<KnownProgrammingLanguage>> GetKnownProgrammingLanguage(int id)
        {
          if (_context.knownProgrammingLanguages == null)
          {
              return NotFound();
          }
            var knownProgrammingLanguage = await _context.knownProgrammingLanguages.FindAsync(id);

            if (knownProgrammingLanguage == null)
            {
                return NotFound();
            }

            return knownProgrammingLanguage;
        }

        // PUT: api/ProgrammingLanguage/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutKnownProgrammingLanguage(int id, KnownProgrammingLanguage knownProgrammingLanguage)
        {
            if (id != knownProgrammingLanguage.Id)
            {
                return BadRequest();
            }

            _context.Entry(knownProgrammingLanguage).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!KnownProgrammingLanguageExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/ProgrammingLanguage
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<KnownProgrammingLanguage>> PostKnownProgrammingLanguage(KnownProgrammingLanguage knownProgrammingLanguage)
        {
          if (_context.knownProgrammingLanguages == null)
          {
              return Problem("Entity set 'KnownProgramLanguageContext.knownProgrammingLanguages'  is null.");
          }
            _context.knownProgrammingLanguages.Add(knownProgrammingLanguage);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetKnownProgrammingLanguage", new { id = knownProgrammingLanguage.Id }, knownProgrammingLanguage);
        }

        // DELETE: api/ProgrammingLanguage/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteKnownProgrammingLanguage(int id)
        {
            if (_context.knownProgrammingLanguages == null)
            {
                return NotFound();
            }
            var knownProgrammingLanguage = await _context.knownProgrammingLanguages.FindAsync(id);
            if (knownProgrammingLanguage == null)
            {
                return NotFound();
            }

            _context.knownProgrammingLanguages.Remove(knownProgrammingLanguage);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool KnownProgrammingLanguageExists(int id)
        {
            return (_context.knownProgrammingLanguages?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
