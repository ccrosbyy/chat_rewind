import org.json.simple.JSONObject;

public class Reaction {

    String actorName, vibe;
    GroupMember actor;

    public Reaction(JSONObject react){
        actorName = (String)react.get("actor");
        vibe = reaction_decoder((String)react.get("reaction"));
    }

    public void setActor(GroupMember member){
        member.addReactSent(this);
        actor = member;
    }

    public String getActorName(){
        return actorName;
    }

    public GroupMember getActor(){
        return actor;
    }

    public String getVibe(){
        return vibe;
    }

    /**
     * facebook's unicode format fucks everything up and i must translate manually like this
     * @param reaction stupid symbols
     * @return the actual react
     */
    String reaction_decoder(String reaction){
        String decoded = "unknown";
        switch (reaction){
            case "\u00f0\u009f\u0098\u0086":
                decoded = "laugh";
                break;

            case "\u00e2\u009d\u00a4":
                decoded = "heart";
                break;

            case "\u00f0\u009f\u0098\u00ae":
                decoded = "wow";
                break;

            case "\u00f0\u009f\u0098\u00a2":
                decoded = "sad";
                break;

            case "\u00f0\u009f\u0098\u00a0":
                decoded = "angry";
                break;

            case "\u00f0\u009f\u0091\u008d":
                decoded = "thumbs up";
                break;

            case "\u00f0\u009f\u0091\u008e":
                decoded = "thumbs down";
                break;
        }
        return decoded;
    }
}