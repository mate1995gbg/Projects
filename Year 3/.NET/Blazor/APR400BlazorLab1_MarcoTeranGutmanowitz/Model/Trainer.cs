using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace APR400BlazorLab1_MarcoTeranGutmanowitz.Model
{
    public class Trainer
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)] //represents primary ked from mongo db
        public string id { get; set; }
        public string name { get; set; }
        public string born { get; set; }
        public string gender { get; set; }
        public string recruited { get; set; }

        [BsonRepresentation(BsonType.ObjectId)]
        public string team_id { get; set; }  // Representing the team_id from MongoDB
    }
}
