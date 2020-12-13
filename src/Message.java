import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Message {

    String content, type, senderName;
    long timestamp_ms;
    ArrayList<Reaction> reactions;
    GroupMember sender;

    public Message(JSONObject obj){
        content = (String)obj.get("content");
        type = (String)obj.get("type");
        timestamp_ms = (long)obj.get("timestamp_ms");
        senderName = (String)obj.get("sender_name");
       /* for (GroupMember m : super.members){
            if (m.fullname.equals(obj.get("sender_name"))) {
                sender = m;
                break;
            }
        }*/

        if (obj.containsKey("reactions")){
            reactions = new ArrayList<Reaction>();
            JSONArray reactslist = (JSONArray)obj.get("reactions");
            Iterator reacts = reactslist.iterator();

            while(reacts.hasNext()) {
                JSONObject react = (JSONObject) reacts.next();
                reactions.add(new Reaction(react));
            }
        }
    }

    public int getWordCount(){
        if (content != null) {
            StringTokenizer st = new StringTokenizer(content);
            return st.countTokens();
        } else {
            return 0;
        }

    }

    public void setSender(GroupMember member){
        member.addMessage(this);
        sender = member;
    }

    String getContent(){
        return content;
    }

    String getType(){
        return type;
    }

    long getTimestamp(){
        return timestamp_ms;
    }

    GroupMember getSender(){
        return sender;
    }

    String getSenderName(){
        return senderName;
    }

    ArrayList<Reaction> getReacts(){
        return reactions;
    }
}