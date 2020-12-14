import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ChatRewind {

    HashMap<String, HashMap<GroupMember, Integer>> stats = new HashMap<>();

    HashMap<String, Integer> word_freq = new HashMap<>();

    public ChatRewind(){
        Chat chat = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        chat.add_file("messages/inbox/society_ekt-war5ua/message_2.json");

        // do stats thing yeah
        gather_message_counts(chat);
        gather_word_counts(chat);
        gather_react_counts(chat);
        gather_longest_messages(chat);
        //print_raw_stat("longest message");
        calculate_word_frequency(chat);
        gather_frequent_words(chat);

        /*for (GroupMember member : chat.members){
            System.out.println("\n" + member.getName() + "'s longest message: \n"
                    + member.longestMessage.getContent() + "\n");
        }*/
        //chat.print_msgs();
        //print_raw_stat("message count");
        //print_raw_stat("word count");
        //print_raw_stat("reacts sent");
        //print_raw_stat("reacts received");
        //print_raw_stat("laugh reacts sent");
        //print_raw_stat("laugh reacts received");
        //print_react_counts(chat);

    }

    public void gather_frequent_words(Chat chat){
        stats.put("most frequent word", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {

        }
    }

    public void calculate_word_frequency(Chat chat){
        for (Message message : chat.getMessages()){
            if (message.content != null) {
                GroupMember sender = message.getSender();

                StringTokenizer st = new StringTokenizer(message.content);
                while (st.hasMoreTokens()) {
                    String current = st.nextToken();

                    //remove leading weird characters so that counting is accurate
                    while ((current.startsWith("\"") || current.startsWith("'") || current.startsWith(".")
                            || current.startsWith("(") || current.startsWith(")") || current.startsWith("*"))
                            && current.length() > 1) {
                            current = current.substring(1);
                    }

                    //remove trailing weird characters so that counting is accurate
                    while (current.endsWith("\"") || current.endsWith("'") || current.endsWith("?")
                            || current.endsWith(".") || current.endsWith(":") || current.endsWith("*")
                            || current.endsWith("!") || current.endsWith(")") || current.endsWith("(")) {
                        if (current.length() > 1)
                            current = current.substring(0, current.length() - 1);
                        else
                            break;
                    }

                    //convert everything to lowercase so that counting is accurate
                    current = current.toLowerCase();

                    //for total word freq
                    if (word_freq.containsKey(current))
                        word_freq.put(current, word_freq.get(current) + 1);
                    else
                        word_freq.put(current, 1);

                    //for member word freq
                    if (sender.word_freq.containsKey(current))
                        sender.word_freq.put(current, sender.word_freq.get(current) + 1);
                    else
                        sender.word_freq.put(current, 1);
                }
            }
        }
    }

    /**
     * longest message by character count
     * @param chat
     */
    public void gather_longest_messages(Chat chat){
        stats.put("longest message", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            member.findLongestMessage();
            stats.get("longest message").put(member, member.longestMessage.getContent().length());
        }
        get_highest("longest message").assignTitle("longest message");
    }

    public GroupMember get_highest(String stat){
        GroupMember topdog = null;
        int highest = -1;
        for (Map.Entry<GroupMember,Integer> item : stats.get(stat).entrySet()){
            if (item.getValue() > highest){
                highest = item.getValue();
                topdog = item.getKey();
            }
        }
        return topdog;
    }

    public GroupMember get_lowest(String stat){
        GroupMember bottom = null;
        int lowest = -1;
        for (Map.Entry<GroupMember,Integer> item : stats.get(stat).entrySet()){
            if (item.getValue() < lowest || lowest == -1){
                lowest = item.getValue();
                bottom = item.getKey();
            }
        }
        return bottom;
    }

    public void gather_message_counts(Chat chat){
        stats.put("message count", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("message count").put(member, member.messages.size());
        }
        get_highest("message count").assignTitle("most messages");
        get_lowest("message count").assignTitle("least messages");
    }

    public void gather_word_counts(Chat chat){
        stats.put("word count", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("word count").put(member, member.getWordCount());
        }
        get_highest("word count").assignTitle("most words");
        get_lowest("word count").assignTitle("least words");
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
            stats.get("reacts sent").put(member, member.reactsSent.size());
            stats.get("reacts received").put(member, member.reactsReceived.size());
            stats.get("laugh reacts sent").put(member, member.laughReactsSent);
            stats.get("laugh reacts received").put(member, member.laughReactsReceived);
            stats.get("wow reacts sent").put(member, member.wowReactsSent);
            stats.get("wow reacts received").put(member, member.wowReactsReceived);
            stats.get("heart reacts sent").put(member, member.heartReactsSent);
            stats.get("heart reacts received").put(member, member.heartReactsReceived);
            stats.get("sad reacts sent").put(member, member.sadReactsSent);
            stats.get("sad reacts received").put(member, member.sadReactsReceived);
            stats.get("angry reacts sent").put(member, member.angryReactsSent);
            stats.get("angry reacts received").put(member, member.angryReactsReceived);
            stats.get("thumbs up reacts sent").put(member, member.thumbsUpReactsSent);
            stats.get("thumbs up reacts received").put(member, member.thumbsUpReactsReceived);
            stats.get("thumbs down reacts sent").put(member, member.thumbsDownReactsSent);
            stats.get("thumbs down reacts received").put(member, member.thumbsDownReactsReceived);
        }
    }

    public void print_raw_stat(String stat){
        System.out.println("\n" + stat.toUpperCase() + ":");
        for (Map.Entry<GroupMember,Integer> item : stats.get(stat).entrySet()){
            System.out.println(item.getKey().getName() + ": " + item.getValue());
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