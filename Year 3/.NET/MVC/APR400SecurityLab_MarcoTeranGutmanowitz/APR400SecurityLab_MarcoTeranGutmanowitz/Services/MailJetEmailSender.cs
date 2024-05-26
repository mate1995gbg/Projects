using APR400SecurityLab_MarcoTeranGutmanowitz.Models;
using Mailjet.Client;
using Mailjet.Client.Resources;
using Microsoft.AspNetCore.Identity.UI.Services;
using Microsoft.Extensions.Options;
using Newtonsoft.Json.Linq;

namespace APR400SecurityLab_MarcoTeranGutmanowitz.Services
{
    public class MailJetEmailSender : IEmailSender  //inherits from IEmailSender
    {
        private readonly MailJetSettings _mailJetSettings;
        private readonly ILogger _logger;
        public MailJetEmailSender(IOptions<MailJetSettings> options, ILogger<MailJetEmailSender> logger)
        {
            _mailJetSettings = options.Value;
            _logger = logger;
        }

        public async Task SendEmailAsync(string email, string subject, string htmlMessage)
        {
            MailjetClient client = new MailjetClient(_mailJetSettings.ApiKey, _mailJetSettings.ApiSecret);
            MailjetRequest request = new MailjetRequest
            {
                Resource = Send.Resource,
            }
            .Property(Send.FromEmail, _mailJetSettings.FromEmail)
            .Property(Send.FromName, "Marco")
            .Property(Send.Subject, subject)
            .Property(Send.TextPart, "My first Mailjet email")
            .Property(Send.HtmlPart, htmlMessage)
            .Property(Send.Recipients, new JArray {
                new JObject {
                    {"Email", email}
                }
            });

            MailjetResponse response = await client.PostAsync(request);

            if (response.IsSuccessStatusCode)
            {
                _logger.LogInformation($"Email sent to {email}");
            }
            else
            {
                _logger.LogError($"Failed to send email to {email}. Error: {response.GetErrorMessage()}");
            }
        }
    }
}
