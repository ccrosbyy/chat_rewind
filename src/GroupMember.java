import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMember {

    String firstname, lastname, fullname, lasting_nickname, descriptor;
    ArrayList<Message> messages = new ArrayList<Message>();

    ArrayList<String> nicknames = new ArrayList<>();
    ArrayList<Message> nickname_messages = new ArrayList<>();

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

    public void findLastingNickname(){
        long time = -1;
        Message lasting_name = null;
        int lasting = 0;
        //System.out.println(getNicknames());
        if (nickname_messages.size() > 1) {
            for (int i = 0; i < nickname_messages.size()-1; i++) {
                if (nickname_messages.get(i).getTimestamp_ms() - nickname_messages.get(i + 1).getTimestamp_ms() > time) {
                    //System.out.print("PISS " + fullname + " " +
                            //(nickname_messages.get(i).getTimestamp_ms() - nickname_messages.get(i + 1).getTimestamp_ms()) + " ");
                    //lasting_name = nickname_messages.get(i);
                    lasting = i;
                    time = nickname_messages.get(i).getTimestamp_ms() - nickname_messages.get(i + 1).getTimestamp_ms();

                    //System.out.println(nickname_messages.get(i + 1).timestamp_date +
                            //" - " + nickname_messages.get(i).timestamp_date);
                }
            }
        }
        if (!nicknames.isEmpty()){
            lasting_nickname = nicknames.get(lasting);
        }
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
                    //System.out.println(getName() + " " + reaction + " " + topcount + "\n" + top.getContent() + "\n\n");
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


    public void findDescriptors(){
        String sender_desc = "";
        int[] senders = {angryReactsSent*4, laughReactsSent, wowReactsSent, sadReactsSent, heartReactsSent/4,
                thumbsUpReactsSent/2, thumbsDownReactsSent/2};
        int topSent = -1;
        int topVal = -1;
        int secondSent = -1;
        int secondVal = -1;
        //System.out.println(fullname);
        //for (int i : senders)
            //System.out.print(i + "  ");
        for (int i = 0; i < senders.length; i++){
            if (senders[i] > topVal){
                topSent = i;
                topVal = senders[i];
            }
        }
        for (int i = 0; i < senders.length; i++) {
            if (senders[i] > secondVal && i != topSent) {
                secondSent = i;
                secondVal = senders[i];
            }
        }
        //System.out.println("\n"+topSent);
       // System.out.println((secondSent +"\n"));

        switch (secondSent){
            case 0:
                sender_desc += "Irate ";
                break;
            case 1:
                sender_desc += "Amused ";
                break;
            case 2:
                sender_desc += "Astonished ";
                break;
            case 3:
                sender_desc += "Depressed ";
                break;
            case 4:
                sender_desc += "Sweet ";
                break;
            case 5:
                sender_desc += "Agreeing ";
                break;
            case 6:
                sender_desc += "Disagreeing ";
                break;
        }

        switch (topSent){
            case 0:
                sender_desc += "Misanthrope";
                break;
            case 1:
                sender_desc += "Chucklehead";
                break;
            case 2:
                sender_desc += "Pogger";
                break;
            case 3:
                sender_desc += "Pessimist";
                break;
            case 4:
                sender_desc += "Lover";
                break;
            case 5:
                sender_desc += "Advocate";
                break;
            case 6:
                sender_desc += "Contrarian";
                break;
        }

        String receiver_desc = "";
        int[] receivers = {angryReactsReceived*4, laughReactsReceived, wowReactsReceived, sadReactsReceived,
                heartReactsReceived/4, thumbsUpReactsReceived/2, thumbsDownReactsReceived/2}; //divide or else everyone is lover
        int topRec = -1;
        topVal = -1;
        int secondRec = -1;
        secondVal = -1;
        for (int i = 0; i < receivers.length; i++){
            if (receivers[i] > topVal){
                topRec = i;
                topVal = receivers[i];
            }
        }
        for (int i = 0; i < receivers.length; i++) {
            if (receivers[i] > secondVal && i != topRec) {
                secondRec = i;
                secondVal = receivers[i];
            }
        }

        switch (secondRec){
            case 0:
                receiver_desc += "Infuriating ";
                break;
            case 1:
                receiver_desc += "Funny ";
                break;
            case 2:
                receiver_desc += "Surprising ";
                break;
            case 3:
                receiver_desc += "Pitiful ";
                break;
            case 4:
                receiver_desc += "Beloved ";
                break;
            case 5:
                receiver_desc += "Agreeable ";
                break;
            case 6:
                receiver_desc += "Controversial ";
                break;
        }

        switch (topRec){
            case 0:
                receiver_desc += "Madman";
                break;
            case 1:
                receiver_desc += "Comedian";
                break;
            case 2:
                receiver_desc += "Shocker";
                break;
            case 3:
                receiver_desc += "Loser";
                break;
            case 4:
                receiver_desc += "Darling";
                break;
            case 5:
                receiver_desc += "Champion";
                break;
            case 6:
                receiver_desc += "Opposition";
                break;
        }
        descriptor = sender_desc + "; " + receiver_desc;
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
        findDescriptors();
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
