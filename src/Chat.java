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
    ArrayList<Message> top_messages = new ArrayList<>();

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
                    if (message.getReacts().size() > 3){
                        top_messages.add(message);
                    }
                }

                // if message is a nickname change, add nickname to member
                if (message.getContent() != null && ((message.getContent().contains(" set the nickname for ") ||
                        message.getContent().contains(" set your nickname to ")) ||
                        (message.getContent().contains(" set his own nickname to ")) ||
                                message.getContent().contains(" set her own nickname to "))){
                    for (GroupMember member : members){
                        if (message.getContent().contains(" set the nickname for "+member.getName())) {
                            int i = message.getContent().indexOf(
                                    "for " + member.getName() + " to ") + 8 + member.getName().length();
                            member.addNickname(message.getContent().substring(i, message.getContent().length() - 1));
                            member.nickname_messages.add(message); //yes this is sloppy but i don't feel like making another getter method
                            //System.out.println(member.nicknames);
                        } else if ((message.getContent().contains(" set his own nickname to ")
                                || message.getContent().contains(" set her own nickname to "))
                                && message.getSender() == member){
                            int i = message.getContent().indexOf(
                                    " own nickname to ") + 17;
                            member.addNickname(message.getContent().substring(i, message.getContent().length()-1));
                            member.nickname_messages.add(message); //yes this is sloppy but i don't feel like making another getter method
                            //System.out.println(member.nicknames);
                        } else if (message.getContent().contains(" set your nickname to ") &&
                                member.getFirstname().equals("Dylan")){ // when using other peoples data, generalize this to their name
                            int i = message.getContent().indexOf("set your nickname to ") + 21;
                            member.addNickname(message.getContent().substring(i, message.getContent().length()-1));
                            member.nickname_messages.add(message);
                        }
                    }
                }

                // if message is a left chat alert, count it to the person
                if (message.getType().equals("Unsubscribe")){
                    message.getSender().countLeave();
                    //System.out.println(message.getSenderName() +
                            //" left the chat (" + message.getSender().leaveCount + " times total)");
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
            System.out.println(fullmsg_toString(m));
        }
    }

    public String fullmsg_toString(Message m){
        String s = "";
        s += m.getTimestampFull() + "\n";
        s += m.getSenderName()+ ": ";
        if (m.getPhoto() != null)
                s += m.getPhoto() + "\n";
        if (m.getVideo() != null)
            s += m.getVideo() + "\n";
        if (m.getGif() != null)
            s += m.getGif() + "\n";
        if (m.getContent() != null)
            s += m.getContent() + "\n";
        if (m.getReacts() != null) {
            for (Reaction react : m.getReacts()) {
                s += "\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
        }
        return s;
    }

    public String msg_toString(Message m){
        String s = "";
        if (m.getVideo() != null)
            s += m.video;
        if (m.getGif() != null)
            s += m.gif;
        if (m.getPhoto() != null)
            s += m.photo;
        if (m.getContent() != null)
            s += m.content;
        return s;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Chat cum = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        cum.print_msgs();
    }

}
