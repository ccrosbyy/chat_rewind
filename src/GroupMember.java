import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMember {

    String firstname, lastname, fullname;
    ArrayList<Message> messages = new ArrayList<Message>();

    ArrayList<String> nicknames = new ArrayList<>();

    HashMap<String, Integer> word_freq = new HashMap<>();

    String[] topWords = new String[20];

    Message longestMessage;

    int leaveCount = 0;

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

    Message topReact, topLaugh, topWow, topAngry, topSad, topHeart, topUp, topDown;
    int topReactNum, topLaughNum, topWowNum, topAngryNum, topSadNum, topHeartNum, topUpNum, topDownNum;

    public GroupMember(String name) {
        setName(name);
    }

    public void addNickname(String nickname){
        nicknames.add(nickname);
    }

    public void countLeave(){
        leaveCount++;
    }

    public ArrayList<String> getTitles(){
        return titles;
    }

    public ArrayList<String> getNicknames(){
        return nicknames;
    }


    public void findMostReacted(String reaction){
        int topcount = -1;
        Message top = null;
        for (Message m : messages) {
            int count = 0;
            if (m.getReacts() != null){ //&& m.getContent() != null) { //eventually support photo messages BRO COME ON
                switch (reaction) {
                    case "any":
                        for (Reaction r : m.getReacts()) {
                            count++;
                        }
                        break;

                    case "laugh":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("laugh"))
                                count++;
                        }
                        break;

                    case "wow":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("wow"))
                                count++;
                        }
                        break;

                    case "sad":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("sad"))
                                count++;
                        }
                        break;

                    case "angry":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("angry"))
                                count++;
                        }
                        break;

                    case "heart":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("heart"))
                                count++;
                        }
                        break;

                    case "thumbs up":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("thumbs up"))
                                count++;
                        }
                        break;

                    case "thumbs down":
                        for (Reaction r : m.getReacts()) {
                            if (r.getVibe().equals("thumbs down"))
                                count++;
                        }
                        break;
                }

                if (count > topcount) {
                    topcount = count;
                    top = m;
                }
            }
        }

        switch (reaction) {
            case "any":
                topReact = top;
                topReactNum = topcount;
                break;

            case "laugh":
                topLaugh = top;
                topLaughNum = topcount;
                break;

            case "wow":
                topWow = top;
                topWowNum = topcount;
                break;

            case "sad":
                topSad = top;
                topSadNum = topcount;
                break;

            case "angry":
                topAngry = top;
                topAngryNum = topcount;
                break;

            case "heart":
                topHeart = top;
                topHeartNum = topcount;
                break;

            case "thumbs up":
                topUp = top;
                topUpNum = topcount;
                break;

            case "thumbs down":
                topDown = top;
                topDownNum = topcount;
                break;
        }
    }

    public Message getMostReacted(String reaction){
        switch (reaction) {
            case "any":
                return topReact;

            case "laugh":
                return topLaugh;

            case "wow":
                return topWow;

            case "sad":
                return topSad;

            case "angry":
                return topAngry;

            case "heart":
                return topHeart;

            case "thumbs up":
                return topUp;

            case "thumbs down":
                return topDown;

            default:
                return topReact;
        }
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

    public String getName(){
        return fullname;
    }

    public String getFirstname(){
        return firstname;
    }

    public void setName(String s){
        fullname = s;
        firstname = s.split(" ")[0];
        lastname = s.split(" ")[1];
    }

    public void assignTitle(String title){
        titles.add(title);
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    /**
     *
     * @param ignore list of boring words that we don't want in the top 10 list
     */
    public void findTopTenWords(String[] ignore){
        for (Map.Entry<String,Integer> item : word_freq.entrySet()){
            // skip stupid words
            if (Arrays.asList(ignore).contains(item.getKey()))
                continue;

            for (int i = 0; i < topWords.length; i++){
                if (topWords[i] == null || item.getValue() > word_freq.get(topWords[i])){
                    if (topWords[i] != null) {
                        for (int j = topWords.length - 1; j > i; j--) {
                            topWords[j] = topWords[j - 1];
                        }
                    }
                    topWords[i] = item.getKey();
                    break;
                }
            }
        }
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

    public int countWord(String word){
        if (word_freq.get(word) != null)
            return word_freq.get(word);
        else
            return 0;
    }

    public int countPhrase(String phrase){
        if (phrase.replace(" ", "cum").equals(phrase)) {
            //System.out.println("single word phrase, redirecting to count_word for maximum efficiency");
            return countWord(phrase);
        }
        else {
            int count = 0;
            for (Message msg : messages) {
                if (msg.getContent() != null) {
                    Pattern p = Pattern.compile(phrase);
                    Matcher m = p.matcher(msg.getContent().toLowerCase());
                    while (m.find())
                        count++;
                }
            }
            return count;
        }
    }
}
