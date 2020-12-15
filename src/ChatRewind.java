import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ChatRewind {

    HashMap<String, HashMap<GroupMember, Integer>> stats = new HashMap<>();

    HashMap<String, Integer> word_freq = new HashMap<>();

    String[] words_to_ignore = {"a", "an", "the", "be", "can", "could", "do", "have", "not", "that", "it", "its",
            "it's", "that's", "there", "but", "when", "u", "in", "and", "is", "to", "i", "you", "my", "for", "just"
            ,"this", "i'll", "of", "so", "i'm", "me", "on", "was", "as", "with", "if", "what", "we", "at", "your"
            ,"you're", "are", "up", "get", "don't", "all", "one", "or", "how", "out", "go", "he", "she"
            , "her", "about", "then", "know", "they", "too", "got", "gonna", "like", "now", "from", "also", "want"
            , "really", "why", "did", "didn't", "think", "yea", "yeah", "no", "still", "good", "yes", "haha", "hahaha"
            , "them", "oh", "would", "come", "more", "cause", "here", "were", "him", "had", "will", "even", "tho"
            , "has", "say", "his", "because", "wanna", ":", "they're", "after", "am", "need", "much", "going"
            , "let", "dont", "thats", "see", "sounds", "than", "over", "been", "some", "into", "way", "doing"
            , "their", "though", "he's", "only", "lot", "off", "can't", "said", "1", "2", "3", "4", "5", "6"
            ,  "might", "sent", "should", "feel", "right", "back", "well", "someone", "something"
            , "youre", "ive", "probs", "very", "make", "next", "us", "what's", "coming", "take", "wait", "w"
            , "i've", "our", "we're", "day", "those", "better", "two", "one", "she's", "few", "by", "rn", "tell"
            , "before", "doesn't", "does", "where", "things", "who", "asked", "time", "again", "never", "i'd", "isn't"
            , "im", "first", "pretty", "being", "there's", "thought", "didnt", "thing", "other", "already", "any"
            , "last"};

    String[] profanities = {"fuck", "fucking", "fuckin", "fucker", "shit", "shitty", "shitter", "motherfucker",
        "bastard", "shithead", "shit", "ass", "asshole", "ass", "fuckwad", "bitch", "cunt", "piss", "prick",
            "shitting", "goddamn", "damn", "hell", "dumbass", "fatass", "bitchass", "bitchy", "bitching", "bitchin",
                "bitcher", "bitches", "fucks", "f u c k", "s h i t"};

    String[] derogatory_words = {"nigger", "nigga", "gook", "chink", "spic", "kike", "faggot", "homo", "retard"};

    String[] horny_words = {"horny", "gay", "sex", "penis", "dick", "cock", "anal", "missionary", "doggystyle",
        "doggy style", "pussy", "clit", "clitoris", "cum", "jizz", "ejaculate", "blowjob", "blow job", "suck dick",
            "suck his dick", "suck your dick", "jack off", "masturbate", "rub one out", "rubbing one out", "cumming",
                "coom", "cooming", "jizzing", "ejaculating", "blow him", "blowing him", "suck off", "suck him off",
                    "squeeze", "jerk off", "jerking off", "jerk it", "jerking it", "swallow", "spitters are quitters",
                        "handjob", "hand job", "dildo", "vibrator", "lube", "lubed", "nipple", "nipples", "penises",
                            "penis", "dicks", "cocks", "boner", "erection", "erect", "boned", "boning", "rockhard",
        "rock hard", "dicked", "dick down", "poundtown", "pound town", "vagina", "slut", "kinky", "kink", "intercourse",
        "sexual", "sexy", "stroke it", "stroke it off", "docking", "nutting", "nice ass", "make me",
        "nice body", "aroused", "eat pussy", "go down on", "analingus", "eat ass", "eating ass", "ate ass", "eat out",
        "eating lunch", "eats lunch", "humping", "grinding", "rimjob", "rim job", "wanna fuck", "to fuck", "would fuck",
        "would smash", "wanna smash", "i'd smash", "kiss", "kissing", "making out", "make out", "makeout", "he's hot",
        "that's hot", "thats hot", "hes hot", "id smash", "spread it", "tits", "boobs", "titties", "boobies", "thicc",
        "thiccc", "thic", "meaty", "meat", "moist", "daddy", "puss", "slutty", "sext", "sexting", "nude", "asspounder",
        "nudes", "naked", "stripping", "stripper", "porn", "gayporn", "pornography", "pornographic", "pornhub", "porno",
        "bend over", "bend me over", "pound me", "pound my ass", "pounder", "bulge", "bulging", "pulsing", "throbbing",
        "sexually", "tie me up", "whip me", "grope", "fondle", "feel up", "fingered", "fingering", "slapping", "smack",
        "dicking", "stroking", "deepthroat", "deep throat", "deep throating", "gagging on", "take it off", "juicy",
        "come find out", "i'm gonna pre", "im gonna pre", "blow my load", "blow your load", "blow a load", "top him",
        "bottom me", "bottoming", "powerbottom", "power bottom", "bear", "bears", "twinks", "twink", "hunk", "hunks"};



    public ChatRewind(){
        Chat chat = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        //for (int i = 2; i < 15; i++)
            //chat.add_file("messages/inbox/society_ekt-war5ua/message_" + i + ".json");

        // do stats thing yeah
        gather_message_counts(chat);
        gather_word_counts(chat);
        gather_react_counts(chat);
        gather_longest_messages(chat);
        //gather_best_messages(chat);

        print_raw_stat("message count");
        calculate_word_frequency(chat);
        horniness(chat);
        normalize(chat, "laugh reacts sent", "laugh reacts received");
        //most_frequent_words(chat);
        //count_phrase(chat,"     ");

        //print_raw_stat("said who asked");



         //GET/PRINT TOP TEN WORDS FOR EACH PERSON
        /*for (int j = 0; j < 8; j++) {
            chat.getMembers().get(j).findTopTenWords(words_to_ignore);
            System.out.println(chat.members.get(j).getName() +":");
            for (int i = 0; i < 20; i++) {
                System.out.print(chat.getMembers().get(j).topWords[i] + "   ");
            }
            System.out.print("\n");
            for (int i = 0; i < 20; i++) {
                System.out.print(chat.getMembers().get(j).word_freq.get(chat.getMembers().get(j).topWords[i]) + "   ");
            }
            System.out.println("\n");
        }*/

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

    /**
     * retrieve the value of a given stat for a given member
     * @param stat
     * @param m
     * @return
     */
    public int get_stat(String stat, GroupMember m){
        return stats.get(stat).get(m);
    }

    /**
     * divides another stat by the value of a given stat (message count is default stat2)
     * @param chat
     * @param stat
     */
    public void normalize(Chat chat, String stat){
        stats.put(stat + " / message count", new HashMap<>());
        for (GroupMember member : chat.getMembers()){
            float normo = 1.0f* get_stat(stat, member) / get_stat("message count", member);
            stats.get(stat + " / message count").put(member, (int)(normo * 10000));
        }
        print_raw_stat(stat + " / message count");
    }

    public void normalize(Chat chat, String stat, String stat2){
        stats.put(stat + " / " + stat2, new HashMap<>());
        for (GroupMember member : chat.getMembers()){
            float normo = 1.0f* get_stat(stat, member) / get_stat(stat2, member);
            stats.get(stat + " / " + stat2).put(member, (int)(normo * 10000));
        }
        print_raw_stat(stat + " / " + stat2);
    }

    public void horniness(Chat chat){
        stats.put("horniness", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("horniness").put(member, 0); //initialize stat
            for (String phrase : horny_words)
                stats.get("horniness").put(member, stats.get("horniness").get(member) + member.countPhrase(phrase));
        }
        GroupMember topdog = get_highest("horniness");
        GroupMember bottom = get_lowest("horniness");
        topdog.assignTitle("most horny");
        bottom.assignTitle("least horny");
        //System.out.println(topdog.getName() + " was the most horny!\n");
        print_raw_stat("horniness");

    }

    public void count_phrase(Chat chat, String phrase){
        if (phrase.replace(" ", "cum").equals(phrase)){
            //System.out.println("single word phrase, redirecting to count_word for maximum efficiency");
            count_word(chat, phrase);
        } else {
            stats.put("said " + phrase, new HashMap<>());
            for (GroupMember member : chat.getMembers()) {
                stats.get("said " + phrase).put(member, member.countPhrase(phrase));
            }
            GroupMember topdog = get_highest("said " + phrase);
            topdog.assignTitle("said " + phrase);
            System.out.println(topdog.getName() + " said " + phrase + " "
                    + stats.get("said " + phrase).get(topdog) + " times!");
        }

    }

    public void count_word(Chat chat, String word){
        stats.put("said " + word, new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("said " + word).put(member, member.countWord(word));
        }
        GroupMember topdog = get_highest("said " + word);
        topdog.assignTitle("said " + word);
        System.out.println(topdog.getName() + " said " + word + " "
                + stats.get("said " + word).get(topdog) + " times!");
    }

    public void most_frequent_words(Chat chat){

        String[] topWords = new String[20];

        for (Map.Entry<String,Integer> item : word_freq.entrySet()){
            // skip stupid words
            if (Arrays.asList(words_to_ignore).contains(item.getKey()))
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
        for (int i = 0; i < 20; i++) {
            System.out.print(topWords[i] + "   ");
        }
        System.out.println("\n");
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
                            || current.endsWith("!") || current.endsWith(")") || current.endsWith("(")
                            || current.endsWith(",")) {
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