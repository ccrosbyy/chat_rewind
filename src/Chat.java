import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;


public class Chat {
    ArrayList<GroupMember> members = new ArrayList<GroupMember>();
    ArrayList<Message> messages = new ArrayList<Message>();

    public Chat(String file){
        add_file(file);
    }

    public Chat(){
    }

    public void add_file(String file){
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(file)){
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // import groupmembers listed in file (don't add ones already in members)
            JSONArray peeplist = (JSONArray) jsonObject.get("participants");
            Iterator peeps = peeplist.iterator();

            while(peeps.hasNext()){
                JSONObject peep = (JSONObject) peeps.next();
                boolean duplicate = false;
                for (GroupMember m : members){
                    if (m.getName().equals(peep.get("name"))){
                        duplicate = true;
                    }
                }
                if (!duplicate)
                    members.add(new GroupMember((String)peep.get("name")));
            }

            // import messages
            JSONArray msglist = (JSONArray)jsonObject.get("messages");
            Iterator msgs = msglist.iterator();

            while (msgs.hasNext()){
                JSONObject msg = (JSONObject)msgs.next();
                Message message = new Message(msg);
                // link member to sender
                for (GroupMember member : members){
                    if (member.getName().equals(message.senderName))
                        message.setSender(member);
                }
                // link members to reacts
                if (message.getReacts() != null) {
                    for (Reaction react : message.getReacts()) {
                        for (GroupMember member : members) {
                            if (member.getName().equals(react.getActorName())) {
                                react.setActor(member);
                            }
                        }
                        message.getSender().reactsReceived.add(react);
                    }
                }
                messages.add(message);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<GroupMember> getMembers(){
        return members;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void print_msgs(){
        for (Message m : messages){
            System.out.println(m.getSenderName()+ ": " + m.getContent());
            if (m.getReacts() != null) {
                for (Reaction react : m.getReacts()) {
                    System.out.println("\t" + react.getActorName() + " reacted with a " + react.vibe);
                }
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Chat cum = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        cum.print_msgs();
    }

}
