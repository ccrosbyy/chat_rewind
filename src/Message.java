import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Message {

    String content, type, senderName, photo, timestamp_date, timestamp_time;
    long timestamp_ms;
    Instant timestamp_raw;
    ArrayList<Reaction> reactions;
    GroupMember sender;

    public Message(JSONObject obj){
        content = (String)obj.get("content");
        if (content != null) {
            content = message_decoder(content);
        }
        type = (String)obj.get("type");

        timestamp_ms = (long)obj.get("timestamp_ms");
        timestamp_raw = Instant.ofEpochMilli(timestamp_ms);
        timestamp_date = LocalDateTime.ofInstant(timestamp_raw, ZoneOffset.UTC).format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        timestamp_time = LocalDateTime.ofInstant(timestamp_raw, ZoneOffset.UTC).format(
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));

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

        if (obj.containsKey("photos")){
            JSONObject pic = (JSONObject) ((JSONArray)obj.get("photos")).get(0);
            photo = (String) pic.get("uri");
        }
    }

    public String getPhoto(){
        return photo;
    }


    /**
     * I need to figure out how to actually fix the encoding on this shit
     * cause this is too tedious for every special char
     * https://stackoverflow.com/questions/50008296/facebook-json-badly-encoded
     * @param message
     * @return
     */
    String message_decoder(String message){
        String dic = message;
        dic = dic.replaceAll("\u00e2\u0080\u0099", "'");
        dic = dic.replaceAll("\u00e2\u0080\u009c", "\"");
        dic = dic.replaceAll("\u00e2\u0080\u009d", "\"");
        dic = dic.replaceAll("ð\u009F\u0098\u0082", "\uD83D\uDE02");
        dic = dic.replaceAll("ð\u009F\u008D\u0086", "\uD83C\uDF46");
        dic = dic.replaceAll("ð\u009F\u0098®", "\uD83D\uDE2E");
        dic = dic.replaceAll("ð\u009F\u0091\u008C", "\uD83D\uDC4C");
        dic = dic.replaceAll("ð\u009F\u0096\u0095", "\uD83D\uDD95");
        dic = dic.replaceAll("ð\u009F\u0092©", "\uD83D\uDCA9");
        dic = dic.replaceAll("ð\u009F\u0098³", "\uD83D\uDE33");
        return dic;
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

    String getTimestampFull(){
        return timestamp_date + " " + timestamp_time;
    }

    long getTimestamp_ms(){
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