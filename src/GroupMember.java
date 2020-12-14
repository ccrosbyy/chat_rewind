import java.util.ArrayList;
import java.util.HashMap;

public class GroupMember {

    String firstname, lastname, fullname;
    ArrayList<Message> messages = new ArrayList<Message>();

    HashMap<String, Integer> word_freq = new HashMap<>();

    Message longestMessage;

    /**
     * this will store earned titles, such as "longest message";
     * the actual stat will be stored in the stats dictionary in ChatRewind
     * (each string in this will be a key to the value)
     */
    ArrayList<String> titles = new ArrayList<>();

    ArrayList<Reaction> reactsSent = new ArrayList<Reaction>();
    ArrayList<Reaction> reactsReceived = new ArrayList<Reaction>();

    int angryReactsSent = 0;
    int heartReactsSent = 0;
    int thumbsUpReactsSent = 0;
    int thumbsDownReactsSent = 0;
    int wowReactsSent = 0;
    int laughReactsSent = 0;
    int sadReactsSent = 0;

    int angryReactsReceived = 0;
    int heartReactsReceived = 0;
    int thumbsUpReactsReceived = 0;
    int thumbsDownReactsReceived = 0;
    int wowReactsReceived = 0;
    int laughReactsReceived = 0;
    int sadReactsReceived = 0;

    public GroupMember(String name) {
        fullname = name;
    }

    public int getWordCount(){
        int count = 0;
        for (Message m : messages){
            count += m.getWordCount();
        }
        return count;
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void addReactSent(Reaction react){
        reactsSent.add(react);
    }

    public void addReactReceived(Reaction react){
        reactsReceived.add(react);
    }

    public void sortReacts(){
        for (Reaction react : reactsSent){
            switch (react.getVibe()){
                case "angry":
                    angryReactsSent++;
                    break;

                case "wow":
                    wowReactsSent++;
                    break;

                case "laugh":
                    laughReactsSent++;
                    break;

                case "sad":
                    sadReactsSent++;

                case "thumbs up":
                    thumbsUpReactsSent++;

                case "thumbs down":
                    thumbsDownReactsSent++;

                case "heart":
                    heartReactsSent++;
            }
        }

        for (Reaction react : reactsReceived){
            switch (react.getVibe()){
                case "angry":
                    angryReactsReceived++;
                    break;

                case "wow":
                    wowReactsReceived++;
                    break;

                case "laugh":
                    laughReactsReceived++;
                    break;

                case "sad":
                    sadReactsReceived++;

                case "thumbs up":
                    thumbsUpReactsReceived++;

                case "thumbs down":
                    thumbsDownReactsReceived++;

                case "heart":
                    heartReactsReceived++;
            }
        }
    }

    String getName(){
        return fullname;
    }

    public void assignTitle(String title){
        titles.add(title);
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }


    public void findLongestMessage(){
        int len = -1;
        for (Message m : messages){
            if (m.getContent() != null && m.getContent().length() > len) {
                len = m.getContent().length();
                longestMessage = m;
            }
        }
    }
}
