import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

public class ChatRewind {

    HashMap<String, HashMap<String, Integer>> stats = new HashMap<>();

    public ChatRewind(){
        Chat chat = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        chat.add_file("messages/inbox/society_ekt-war5ua/message_2.json");

        // do stats thing yeah
        gather_message_counts(chat);
        gather_word_counts(chat);
        gather_react_counts(chat);
        print_raw_stat("message counts");
        print_raw_stat("word counts");
        print_raw_stat("reacts sent");
        print_raw_stat("reacts received");
        print_raw_stat("laugh reacts sent");
        print_raw_stat("laugh reacts received");
        //print_react_counts(chat);

    }


    public void gather_message_counts(Chat chat){
        stats.put("message counts", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("message counts").put(member.getName(), member.messages.size());
        }
    }

    public void gather_word_counts(Chat chat){
        stats.put("word counts", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("word counts").put(member.getName(), member.getWordCount());
        }
    }

    public void gather_react_counts(Chat chat){
        stats.put("reacts sent", new HashMap<>());
        stats.put("reacts received", new HashMap<>());
        stats.put("laugh reacts sent", new HashMap<>());
        stats.put("laugh reacts received", new HashMap<>());
        stats.put("wow reacts sent", new HashMap<>());
        stats.put("wow reacts received", new HashMap<>());
        stats.put("heart reacts sent", new HashMap<>());
        stats.put("heart reacts received", new HashMap<>());
        stats.put("sad reacts sent", new HashMap<>());
        stats.put("sad reacts received", new HashMap<>());
        stats.put("angry reacts sent", new HashMap<>());
        stats.put("angry reacts received", new HashMap<>());
        stats.put("thumbs up reacts sent", new HashMap<>());
        stats.put("thumbs up reacts received", new HashMap<>());
        stats.put("thumbs down reacts sent", new HashMap<>());
        stats.put("thumbs down reacts received", new HashMap<>());
        for (GroupMember member : chat.getMembers()){
            member.sortReacts();
            stats.get("reacts sent").put(member.getName(), member.reactsSent.size());
            stats.get("reacts received").put(member.getName(), member.reactsReceived.size());
            stats.get("laugh reacts sent").put(member.getName(), member.laughReactsSent);
            stats.get("laugh reacts received").put(member.getName(), member.laughReactsReceived);
            stats.get("wow reacts sent").put(member.getName(), member.wowReactsSent);
            stats.get("wow reacts received").put(member.getName(), member.wowReactsReceived);
            stats.get("heart reacts sent").put(member.getName(), member.heartReactsSent);
            stats.get("heart reacts received").put(member.getName(), member.heartReactsReceived);
            stats.get("sad reacts sent").put(member.getName(), member.sadReactsSent);
            stats.get("sad reacts received").put(member.getName(), member.sadReactsReceived);
            stats.get("angry reacts sent").put(member.getName(), member.angryReactsSent);
            stats.get("angry reacts received").put(member.getName(), member.angryReactsReceived);
            stats.get("thumbs up reacts sent").put(member.getName(), member.thumbsUpReactsSent);
            stats.get("thumbs up reacts received").put(member.getName(), member.thumbsUpReactsReceived);
            stats.get("thumbs down reacts sent").put(member.getName(), member.thumbsDownReactsSent);
            stats.get("thumbs down reacts received").put(member.getName(), member.thumbsDownReactsReceived);
        }
    }

    public void print_raw_stat(String stat){
        System.out.println("\n" + stat.toUpperCase() + ":");
        for (Map.Entry<String,Integer> item : stats.get(stat).entrySet()){
            System.out.println(item.getKey() + ": " + item.getValue());
        }
    }

    public void print_react_counts(Chat chat){

        for (GroupMember member : chat.getMembers()){
            member.sortReacts();
        }

        System.out.println("\nREACTS SENT:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.reactsSent.size());
        }

        System.out.println("\nREACTS RECEIVED:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.reactsReceived.size());
        }



        System.out.println("\nLAUGH REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.laughReactsSent + " sent, "
                    + member.laughReactsReceived + " received.");
        }

        System.out.println("\nWOW REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.wowReactsSent + " sent, "
                    + member.wowReactsReceived + " received.");
        }

        System.out.println("\nANGRY REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.angryReactsSent + " sent, "
                    + member.angryReactsReceived + " received.");
        }

        System.out.println("\nSAD REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.sadReactsSent + " sent, "
                    + member.sadReactsReceived + " received.");
        }

        System.out.println("\nHEART REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.heartReactsSent + " sent, "
                    + member.heartReactsReceived + " received.");
        }

        System.out.println("\nTHUMBS UP REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.thumbsUpReactsSent + " sent, "
                    + member.thumbsUpReactsReceived + " received.");
        }

        System.out.println("\nTHUMBS DOWN REACTS:");
        for (GroupMember member : chat.getMembers()){
            System.out.println(member.getName() + ": " + member.thumbsDownReactsSent + " sent, "
                    + member.thumbsDownReactsReceived + " received.");
        }



    }

    public static void main(String[] args){
        new ChatRewind();
        //chat.print_msgs();
    }
}