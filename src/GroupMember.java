import java.util.ArrayList;

public class GroupMember {

    String firstname, lastname, fullname;
    ArrayList<Message> messages = new ArrayList<Message>();

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
}
