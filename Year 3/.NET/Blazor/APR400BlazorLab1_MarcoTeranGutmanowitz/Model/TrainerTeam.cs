using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class TrainerTeam
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)] //represents primary ked from mongo db
        public string id { get; set; }
        public string name { get; set; }
        public string founded { get; set; }
        public string home { get; set; }
    }
}
