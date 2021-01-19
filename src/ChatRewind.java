import java.lang.reflect.Member;
import java.rmi.MarshalException;
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

    String[] attacks = {"fuck you", "hate you", "fuck off", "eat shit", "you suck", "you bitch", "stupid bitch",
            "get fucked", "you pussy", "you're a pussy", "kill yourself", "kys", "suck a dick", "suck my dick", "smd",
            "i will kill you", "i'll kill you", "who asked", "i don't care", "you're a bitch", "you dumbass",
            "i dont care", "dont care", "don't care", "nobody cares", "fuck yourself", "you're stupid", "your stupid",
            "your dumb", "you're dumb", "bitch boy", "kill your self", "kill you're self", "fuck your self",
            "you stupid", "you dumb", "eat a dick", "you're ugly", "you're so ugly", "your ugly", "your so ugly",
            "you are annoying", "you are so annoying", "you're annoying", "you're so annoying", "your annoying",
            "your so annoying", "you are ugly", "you are so ugly"};

    String[] horny_words = new String[]{"horny", "gay", "sex", "penis", "dick", "cock", "anal", "missionary",
            "doggystyle", "doggy style", "pussy", "clit", "clitoris", "cum", "jizz", "ejaculate", "blowjob", "blow job",
            "suck dick", "suck his dick", "suck your dick", "jack off", "masturbate", "rub one out", "rubbing one out",
            "cumming", "coom", "cooming", "jizzing", "ejaculating", "blow him", "blowing him", "suck off",
            "suck him off", "squeeze", "jerk off", "jerking off", "jerk it", "jerking it", "swallow",
            "spitters are quitters", "handjob", "hand job", "dildo", "vibrator", "lube", "lubed", "nipple", "nipples",
            "penises", "penis", "dicks", "cocks", "boner", "erection", "erect", "boned", "boning", "rockhard",
            "rock hard", "dicked", "dick down", "poundtown", "pound town", "vagina", "slut", "kinky", "kink",
            "intercourse", "sexual", "sexy", "stroke it", "stroke it off", "docking", "nutting", "nice ass", "make me",
            "nice body", "aroused", "eat pussy", "go down on", "analingus", "eat ass", "eating ass", "ate ass",
            "eat out", "eating lunch", "eats lunch", "humping", "grinding", "rimjob", "rim job", "wanna fuck",
            "to fuck", "would fuck", "would smash", "wanna smash", "i'd smash", "kiss", "kissing", "making out",
            "make out", "makeout", "he's hot", "that's hot", "thats hot", "hes hot", "id smash", "spread it", "tits",
            "boobs", "titties", "boobies", "thicc", "thiccc", "thic", "meaty", "meat", "moist", "daddy", "puss",
            "slutty", "sext", "sexting", "nude", "asspounder", "nudes", "naked", "stripping", "stripper", "porn",
            "gayporn", "pornography", "pornographic", "pornhub", "porno", "bend over", "bend me over", "pound me",
            "pound my ass", "pounder", "bulge", "bulging", "pulsing", "throbbing", "sexually", "tie me up", "whip me",
            "grope", "fondle", "feel up", "fingered", "fingering", "slapping", "smack", "dicking", "stroking",
            "deepthroat", "deep throat", "deep throating", "gagging on", "take it off", "juicy", "come find out",
            "i'm gonna pre", "im gonna pre", "blow my load", "blow your load", "blow a load", "top him", "bottom me",
            "bottoming", "powerbottom", "power bottom", "bear", "bears", "twinks", "twink", "hunk", "hunks", "top me"};

    /*String[] horny_words = {"horny", "sex", "gay", "penis", "dick", "cock", "anal", "missionary", "doggystyle",
        "doggy style", "pussy", "clit", "clitoris", "cum", "jizz", "ejaculate", "blowjob", "blow job", "suck dick",
            "suck his dick", "suck your dick", "jack off", "masturbate", "rub one out", "rubbing one out", "cumming",
                "coom", "cooming", "jizzing", "ejaculating", "blow him", "blowing him", "suck off", "suck him off",
                    "squeeze", "jerk off", "jerking off", "jerk it", "jerking it", "swallow", "spitters are quitters",
                        "handjob", "hand job", "dildo", "vibrator", "lube", "lubed", "nipple", "nipples", "penises",
                            "penis", "dicks", "cocks", "boner", "erection", "erect", "boned", "boning", "rockhard",
        "rock hard", "dicked", "dick down", "poundtown", "pound town", "vagina", "slut", "kinky", "kink", "intercourse",
        "sexual", "sexy", "stroke it", "stroke it off", "docking", "nutting", "nice ass", "make me", "fleshlight",
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
        "bottom me", "bottoming", "powerbottom", "power bottom", "bear", "bears", "twinks", "twink", "hunk", "hunks",
        "moaned", "moaning", "moan", "arouse", "arousing", "so hot", "hot af", "hot as fuck", "long and hard", "thrust",
        "thrusting", "i'm hard", "im hard", "so hard", "that ass", "wet dream", "wet dreams", "douched", "douching",
        "to douche"};*/



    public ChatRewind(){
        Chat chat = new Chat("messages/inbox/society_ekt-war5ua/message_1.json");
        for (int i = 2; i < 6; i++)
            chat.add_file("messages/inbox/society_ekt-war5ua/message_" + i + ".json");

        //RYAN TRAN BROKE THE SYSTEM (THANKS CHRIS)
        for (GroupMember ryan : chat.getMembers()){
            if (ryan.getMessages().isEmpty()){
                chat.members.remove(ryan);
                break;
            }
        }

        // preliminary stats gathering thing yeah
        gather_message_counts(chat);
        gather_leave_counts(chat);
        gather_word_counts(chat);
        gather_react_counts(chat);
        gather_longest_messages(chat);
        find_best_messages(chat);
        find_lasting_nicknames(chat);

        //pick stats of interest
        //print_raw_stat("message count");
        calculate_word_frequency(chat);
        most_frequent_words(chat);
        horniness(chat);
        meanness(chat);
        profaneness(chat);

        //FOR NOMINATIONS BEST MESSAGE (UTILITY; DO NOT USE IN FINAL PROGRAM)
        //nominate_messages(chat);
        /*
        for (Message m : chat.getMessages()){
            if (m.timestamp_date.toLowerCase().endsWith("20")){
                System.out.println(m.getTimestampFull());
                System.out.println(m.getSenderName()+ ": " + m.getContent());
                if (m.getReacts() != null) {
                    for (Reaction react : m.getReacts()) {
                        System.out.println("\t" + react.getActorName() + " reacted with a " + react.vibe);
                    }
                }
                System.out.println("\n");
            }
        }*/


        //print stats compilation
        for (GroupMember member : chat.getMembers()){
            System.out.println(generate_member_profile(chat, member) + "\n\n\n");
        }


        //print_raw_stat("message count");
        //print_raw_stat("meanness");
        //print_raw_stat("meanness / message count");

        //print_raw_stat("laugh reacts received");
        //difference(chat, "laugh reacts received", "laugh reacts sent");
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


    public void gather_leave_counts(Chat chat){
        stats.put("leave count", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("leave count").put(member, member.leaveCount);
        }
        get_highest("leave count").assignTitle("most leaves");
    }

    public void find_lasting_nicknames(Chat chat){
        for (GroupMember member : chat.getMembers()){
            member.findLastingNickname();
        }
    }

    public String generate_member_profile(Chat chat, GroupMember member){
        String s = "";
        s += member.getName().toUpperCase() + "\n" + member.descriptor + //"\n" + member.getTitles() +
                "\n----------------------------------------\n\n";

        // NICKNAMES
        if (member.getNicknames().size() > 1) {
            s += member.getFirstname() + " went by " + member.getNicknames().size() + " different nicknames this year.\n";
            s += "...but the name that stuck with them the most this year was " + member.lasting_nickname + ".\n\n";
        } else if (!member.getNicknames().isEmpty()){
            s += member.getFirstname() + " went by the nickname " + member.getNicknames().get(0) + " this year.\n\n";
        }

        // MESSAGE COUNT
        s += member.getFirstname() + " sent " + member.getMessages().size() + " messages. \n";
        s += "(That's %" + String.format("%.2f", (100.0f * member.getMessages().size())/ chat.getMessages().size())
                + " of the chat's messages this year, at an average of "
                + String.format("%.2f", (1.0f * member.getMessages().size() / 365))
                + " messages per day.)\n";
        if (member.getTitles().contains("most messages")){
            s += "This was also the most messages sent by anybody in the chat! Why not find something better to do?\n";
        } else if (member.getTitles().contains("least messages")) {
            s += "This was also the least messages sent by anybody in the chat! Mysterious...\n";
        }
        s+= "\n";

        // WORD FREQUENCY
        s += "Some of " + member.getFirstname() + "'s favourite words this year were: \n\t";
        for (int i = 0; i < 10; i++){
            if ((i+1) % 5 != 0)
                s += member.topWords[i] + ", ";
            else
                s += member.topWords[i] + "\n\t";
        }
        s+= "\n";

        s+= "Let's take a closer look at " + member.getFirstname() + "'s year in the chat:\n\n";

        // HORNINESS
            //MOST
        if (member.getTitles().contains("most horny") && member.getTitles().contains("most horny normalized")){
            s += "PERVERT:\n\tIn terms of horniness, " + member.getFirstname() + " is the undisputed champion, sending up to " +
                    stats.get("horniness").get(member) + " horny messages, at a rate higher than anybody else.\n\t" +
                    "Time to take a nice cold shower!\n\n";
        } else if(member.getTitles().contains("most horny")){
            s += "SEXUALLY ACTIVE:\n\tIn terms of horniness, " + member.getFirstname() + " let loose the most, with " +
                    stats.get("horniness").get(member) + " horny messages!\n\t" +
                    "However, " + get_highest("horniness / message count").getFirstname() + " had a higher " +
                    "concentration of horny messages, with " + stats.get("horniness").get(
                            get_highest("horniness / message count")) + " horny texts over " +
                    get_highest("horniness / message count").getMessages().size() + " messages.\n\n";
        } else if(member.getTitles().contains("most horny normalized")){
            s += "MIND IN THE GUTTER:\n\tIn terms of horniness, " + member.getFirstname() + " had the highest concentration of horniness," +
                    " sending " + stats.get("horniness").get(member) + " horny messages!\n\t" +
                    "However, " + get_highest("horniness").getFirstname() + " outperforms in sheer volume, sending "
                    + stats.get("horniness").get(get_highest("horniness")) + " horny messages.\n\n";
        }
            //LEAST
        if (member.getTitles().contains("least horny") && member.getTitles().contains("least horny normalized")){
            s += "MASTER OF THEIR DOMAIN:\n\t" + member.getFirstname() + " is the least horniest in the chat," +
                    " sending only " + stats.get("horniness").get(member) + " horny messages, at a rate less than" +
                    " anybody else.\n\t" +
                    "Good for him, I guess.\n\n";
        } else if(member.getTitles().contains("least horny")){
            s += "LOW TESTOSTERONE: \n\tIn terms of horniness, " + member.getFirstname() + " let loose the least, with " +
                    stats.get("horniness").get(member) + " horny messages!\n\t" +
                    "However, " + get_lowest("horniness / message count").getFirstname() + " had a lower " +
                    "concentration of horny messages, with " + stats.get("horniness").get(
                    get_lowest("horniness / message count")) + " horny texts over " +
                    get_lowest("horniness / message count").getMessages().size() + " messages.\n\n";
        } else if(member.getTitles().contains("least horny normalized")){
            s += "ASEXUAL:\n\t " + member.getFirstname() + "'s messages had the lowest concentration of horniness, (" +
                    stats.get("horniness").get(member) + " horny messages).\n\n";
        }

        // MEANNESS
        if (member.getTitles().contains("meanest") && member.getTitles().contains("meanest normalized")){
            s += "LASHING OUT: \n\t" + member.getFirstname() + " was hands down the meanest person in the chat," +
                    " making up to " +
                    stats.get("meanness").get(member) + " vicious comments, at a rate higher than anybody else.\n\t" +
                    "(Please go talk to somebody)\n\n";
        } else if(member.getTitles().contains("meanest")){
            s += "BAD VIBES: \n\t" + member.getFirstname() + " sent the most mean messages in the chat (" +
                    stats.get("meanness").get(member) + ").\n\t" +
                    "However, " + get_highest("meanness / message count").getFirstname() + " had a higher " +
                    "concentration of vitriol, with " + stats.get("meanness").get(
                    get_highest("meanness / message count")) + " mean texts over " +
                    get_highest("meanness / message count").getMessages().size() + " messages.\n\n";
        } else if(member.getTitles().contains("meanest normalized")){
            s += "SHORT FUSE: \n\t" +member.getFirstname() + " was the meanest person in the chat," +
                    " making " + stats.get("meanness").get(member) + " mean comments over " +
                    get_highest("meanness / message count").getMessages().size() + " messages.\n\n";
        }

        //PROFANITY
        if (member.getTitles().contains("most vulgar") && member.getTitles().contains("most vulgar normalized")){
            s += "MOTOR MOUTH: \n\t" + member.getFirstname() + " was hands down the most vulgar person in the chat," +
                    " saying " +  stats.get("profaneness").get(member) +
                    " profanities, at a rate higher than anybody else.\n\t" +
                    "(Watch your mouth!)\n\n";
        } else if(member.getTitles().contains("most vulgar")){
            s += "TOO COMFORTABLE: \n\t" +member.getFirstname() + " sent the most vulgar messages in the chat (" +
                    stats.get("profaneness").get(member) + ").\n" +
                    "However, " + get_highest("profaneness / message count").getFirstname() + " had a higher " +
                    "concentration of profanity, with " + stats.get("meanness").get(
                    get_highest("profaneness / message count")) + " swears over " +
                    get_highest("profaneness / message count").getMessages().size() + " messages.\n\n";
        } else if(member.getTitles().contains("most vulgar normalized")){
            s += "COLOURFUL VOCABULARY: \n\t" +member.getFirstname()
                    + " was proportionately the most vulgar person in the chat," +
                    " making " + stats.get("profaneness").get(member) + " mean comments over" +
                    get_highest("profaneness / message count").getMessages().size() + " messages.\n\n";
        }

        //TOTAL REACTS RECEIVED
        if (member.getTitles().contains("most reacts received")) {
            s += "ATTENTION WHORE: \n\t" + member.getFirstname() + " got the most reactions ("
                    + stats.get("reacts received").get(member) + ") to their messages.\n\t"
                    + "Here's their biggest one:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("any")) + "\n\t\t"
                    + member.getMostReacted("any").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("any").getReacts()) {
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }


        //LAUGH REACTS RECEIVED
        if (member.getTitles().contains("most laugh reacts received")){
            s += "SEINFELD: \n\t" + member.getFirstname() + " got the most laughs, with "
                    + stats.get("laugh reacts received").get(member) + " laugh reacts given to their messages.\n\t"
                    + "Here's one of their funniest messages this year:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("laugh")) + "\n\t\t"
                    + member.getMostReacted("laugh").getTimestampFull() + "\n";
                    for (Reaction react : member.getMostReacted("laugh").getReacts()){
                        s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
                    }
                    s += "\n";
            if (member.getTitles().contains("most laugh reacted message")) {
                s += "\t(This message is also one of the most laugh reacted messages this year.)\n\n";
            }
        } else if (member.getTitles().contains("most laugh reacts received normalized")) {
            s += "QUALITY OVER QUANTITY: \n\t" + member.getFirstname() + " was the most consistently funny, with "
                    + stats.get("laugh reacts received").get(member) + " laugh reacts given to their messages.\n\t"
                    + "Here's one of their funniest messages this year:\n\n\t\t"
                    //+ chat.msg_toString(member.getMostReacted("laugh")) + "\n";
                    + chat.msg_toString(member.getMostReacted("laugh")) + "\n\t\t"
                    + member.getMostReacted("laugh").getTimestampFull() + "\n";
                    for (Reaction react : member.getMostReacted("laugh").getReacts()){
                        s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
                    }
            s += "\n";
            if (member.getTitles().contains("most laugh reacted message")) {
                s += "\t(This message is also one of the most laugh reacted messages this year.)\n\n";
            }
        } else if (member.getTitles().contains("most laugh reacted message")) {
            s += "ONE HIT WONDER: \n\t" + member.getFirstname() + " got the most laughs from a single message.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("laugh")) + "\n\t\t"
                    + member.getMostReacted("laugh").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("laugh").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }



        //HEART REACTS SENT/RECEIVED
        if (member.getTitles().contains("most heart reacts sent")){
            s += "LOVEMAKER: \n\t" + member.getFirstname() + " spread the most love, with "
                    + stats.get("heart reacts sent").get(member) + " heart reacts sent.\n\t"
                    + "Here's one of their own messages that got some love this year:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("heart")) + "\n\t\t"
                    + member.getMostReacted("heart").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("heart").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
            if (member.getTitles().contains("most heart reacted message")) {
                s += "\t(This message is also one of the most heart reacted messages this year.)\n\n";
            }
        } else if (member.getTitles().contains("most heart reacted message")) {
            s += "LOVE WINS: \n\t" + member.getFirstname() + " got the most hearts from a single message.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("heart")) + "\n\t\t"
                    + member.getMostReacted("heart").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("heart").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }

        //SAD REACTS
        if (member.getTitles().contains("most sad reacts received")){
            s += "PITY PARTY: \n\t" + member.getFirstname() + " got the most sympathy, with "
                    + stats.get("laugh reacts received").get(member) + " sad reacts given to their messages.\n\t"
                    + "Here's one of their saddest messages this year:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("sad")) + "\n\t\t"
                    + member.getMostReacted("sad").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("sad").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
            if (member.getTitles().contains("most sad reacted message")) {
                s += "\t(This message is also one of the most sad reacted messages this year.)\n\n";
            }
        } else if (member.getTitles().contains("most sad reacted message")) {
            s += "SOB STORY: \n\t" + member.getFirstname() + " got the most sad reacts from a single message.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("sad")) + "\n\t\t"
                    + member.getMostReacted("sad").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("sad").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }

        //MOST ANGRY REACTS
        if (member.getTitles().contains("most angry reacted message")) {
            s += "OUTRAGE CULTURE: \n\t" + member.getFirstname() + " got the most angry reacts from a single message.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("angry")) + "\n\t\t"
                    + member.getMostReacted("angry").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("angry").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }

        //MOST THUMBS DOWN
        if (member.getTitles().contains("most downvoted message")) {
            s += "DISAPPROVAL: \n\t" + member.getFirstname() + " sent the most disliked message in the chat.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("thumbs down")) + "\n\t\t"
                    + member.getMostReacted("thumbs down").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("thumbs down").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }


        //MOST WOW REACTS
        if (member.getTitles().contains("most wow reacted message")) {
            s += "SHOCKING: \n\t" + member.getFirstname() + " sent the most shocking message in the chat.\n\t"
                    + "Here it is:\n\n\t\t"
                    + chat.msg_toString(member.getMostReacted("wow")) + "\n\t\t"
                    + member.getMostReacted("wow").getTimestampFull() + "\n";
            for (Reaction react : member.getMostReacted("wow").getReacts()){
                s += "\t\t\t" + react.getActorName() + " reacted with a " + react.vibe + "\n";
            }
            s += "\n";
        }

        //CHAT LEAVES
        if (member.getTitles().contains("most leaves")) {
            s += "DISGRACED: \n\t" + member.getFirstname() + " left the chat "
                    + stats.get("leave count").get(member) + " times.\n\tShame on him!";
        }


        //TFW NO TITLES
        if (member.getTitles().isEmpty()){
            s += member.getFirstname() + " said penis " + member.countPhrase("penis") + " times. That's all I got.\n" +
                "Better luck next year, I guess.";
        }


        //TODO ADD METHOD TO FIND OUT RELATIONSHIPS BETWEEN MEMBERS (WHO WAS REACTING MOST CONSISTENTLY TO WHOM, ETC.)
        return s;

    }


    public void meanness(Chat chat){
        stats.put("meanness", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("meanness").put(member, 0); //initialize stat
            for (String phrase : attacks)
                stats.get("meanness").put(member, stats.get("meanness").get(member) + member.countPhrase(phrase));
        }
        GroupMember rawtopdog = get_highest("meanness");
        GroupMember rawbottom = get_lowest("meanness");
        normalize(chat, "meanness");
        GroupMember topdog = get_highest("meanness / message count");
        GroupMember bottom = get_lowest("meanness / message count");
        rawtopdog.assignTitle("meanest");
        rawbottom.assignTitle("least mean");
        topdog.assignTitle("meanest normalized");
        bottom.assignTitle("least mean normalized");
        //System.out.println(topdog.getName() + " was the most horny!\n");
        //print_raw_stat("horniness");
    }

    public void profaneness(Chat chat){
        stats.put("profaneness", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("profaneness").put(member, 0); //initialize stat
            for (String phrase : profanities)
                stats.get("profaneness").put(member, stats.get("profaneness").get(member) + member.countPhrase(phrase));
        }
        GroupMember rawtopdog = get_highest("profaneness");
        GroupMember rawbottom = get_lowest("profaneness");
        normalize(chat, "profaneness");
        GroupMember topdog = get_highest("profaneness / message count");
        GroupMember bottom = get_lowest("profaneness / message count");
        rawtopdog.assignTitle("most vulgar");
        rawbottom.assignTitle("least vulgar");
        topdog.assignTitle("most vulgar normalized");
        bottom.assignTitle("least vulgar normalized");
        //System.out.println(topdog.getName() + " was the most horny!\n");
        //print_raw_stat("horniness");

    }

    public void find_best_messages(Chat chat){
        stats.put("most reacts", new HashMap<>());
        stats.put("most laugh reacts", new HashMap<>());
        stats.put("most wow reacts", new HashMap<>());
        stats.put("most angry reacts", new HashMap<>());
        stats.put("most sad reacts", new HashMap<>());
        stats.put("most heart reacts", new HashMap<>());
        stats.put("most thumbs up", new HashMap<>());
        stats.put("most thumbs down", new HashMap<>());

        for (GroupMember member : chat.getMembers()) {
            //calculate stats
            member.findMostReacted("any");
            member.findMostReacted("laugh");
            member.findMostReacted("wow");
            member.findMostReacted("sad");
            member.findMostReacted("angry");
            member.findMostReacted("heart");
            member.findMostReacted("thumbs up");
            member.findMostReacted("thumbs down");

            //gather stats
            stats.get("most reacts").put(member, member.topReactNum);
            stats.get("most laugh reacts").put(member, member.topLaughNum);
            stats.get("most wow reacts").put(member, member.topWowNum);
            stats.get("most sad reacts").put(member, member.topSadNum);
            stats.get("most angry reacts").put(member, member.topAngryNum);
            stats.get("most heart reacts").put(member, member.topHeartNum);
            stats.get("most thumbs up").put(member, member.topUpNum);
            stats.get("most thumbs down").put(member, member.topDownNum);
            /*System.out.println(member.getName() + "'s most reacted message: \n\t"
                    + member.getMostReacted("any").getContent());
            if (member.getMostReacted("any").getReacts() != null) {
                for (Reaction react : member.getMostReacted("any").getReacts()) {
                    System.out.println("\t\t" + react.getActorName() + " reacted with a " + react.vibe);
                }
            }
            System.out.println();*/
        }

        //award titles
        get_highest("most reacts").assignTitle("most reacted message");
        get_highest("most laugh reacts").assignTitle("most laugh reacted message");
        get_highest("most wow reacts").assignTitle("most wow reacted message");
        get_highest("most sad reacts").assignTitle("most sad reacted message");
        get_highest("most angry reacts").assignTitle("most angry reacted message");
        get_highest("most heart reacts").assignTitle("most heart reacted message");
        get_highest("most thumbs up").assignTitle("most upvoted message");
        get_highest("most thumbs down").assignTitle("most downvoted message");


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
        //print_raw_stat(stat + " / message count");

    }

    public void normalize(Chat chat, String stat, String stat2){
        stats.put(stat + " / " + stat2, new HashMap<>());
        for (GroupMember member : chat.getMembers()){
            float normo = 1.0f* get_stat(stat, member) / get_stat(stat2, member);
            stats.get(stat + " / " + stat2).put(member, (int)(normo * 100));
        }
        //print_raw_stat(stat + " / " + stat2);
    }

    /**
     * subtracts a stat value from an opposing stat value
     * @param chat
     * @param stat
     * @param stat2
     */
    public void difference(Chat chat, String stat, String stat2){
        stats.put("net " + stat, new HashMap<>());
        for (GroupMember member : chat.getMembers()){
            stats.get("net " + stat).put(member, get_stat(stat, member) - get_stat(stat2, member));
        }
        print_raw_stat("net " + stat);
    }

    public void horniness(Chat chat){
        stats.put("horniness", new HashMap<>());
        for (GroupMember member : chat.getMembers()) {
            stats.get("horniness").put(member, 0); //initialize stat
            for (String phrase : horny_words)
                stats.get("horniness").put(member, stats.get("horniness").get(member) + member.countPhrase(phrase));
        }
        GroupMember rawtopdog = get_highest("horniness");
        GroupMember rawbottom = get_lowest("horniness");
        normalize(chat, "horniness");
        GroupMember topdog = get_highest("horniness / message count");
        GroupMember bottom = get_lowest("horniness / message count");
        rawtopdog.assignTitle("most horny");
        rawbottom.assignTitle("least horny");
        topdog.assignTitle("most horny normalized");
        bottom.assignTitle("least horny normalized");
        //System.out.println(topdog.getName() + " was the most horny!\n");
        //print_raw_stat("horniness");

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
        /*for (int i = 0; i < 20; i++) {
            System.out.print(topWords[i] + "   ");
        }
        System.out.println("\n");*/

        for (GroupMember member : chat.getMembers()){
            member.findTopTenWords(words_to_ignore);
        }
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

        get_highest("reacts sent").assignTitle("most reacts sent");
        get_highest("reacts received").assignTitle("most reacts received");
        get_highest("laugh reacts sent").assignTitle("most laugh reacts sent");
        get_highest("laugh reacts received").assignTitle("most laugh reacts received");
        normalize(chat, "laugh reacts received");
        get_highest("laugh reacts received / message count").assignTitle("most laugh reacts received normalized");
        get_highest("wow reacts sent").assignTitle("most wow reacts sent");
        get_highest("wow reacts received").assignTitle("most wow reacts received");
        get_highest("heart reacts sent").assignTitle("most heart reacts sent");
        get_highest("heart reacts received").assignTitle("most heart reacts received");
        get_highest("sad reacts sent").assignTitle("most sad reacts sent");
        get_highest("sad reacts received").assignTitle("most sad reacts received");
        get_highest("angry reacts sent").assignTitle("most angry reacts sent");
        get_highest("angry reacts received").assignTitle("most angry reacts received");
        get_highest("thumbs up reacts sent").assignTitle("most thumbs up reacts sent");
        get_highest("thumbs up reacts received").assignTitle("most thumbs up reacts received");
        get_highest("thumbs down reacts sent").assignTitle("most thumbs down reacts sent");
        get_highest("thumbs down reacts received").assignTitle("most thumbs down reacts received");
    }

    public void print_raw_stat(String stat){
        System.out.println("\n" + stat.toUpperCase() + ":");
        for (Map.Entry<GroupMember,Integer> item : stats.get(stat).entrySet()){
            System.out.println(item.getKey().getName() + ": " + item.getValue());
        }
        System.out.println();
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

    public void nominate_messages(Chat chat){
        for (Message m : chat.top_messages){
            System.out.print(m.getSenderName()+ ": ");
            if (m.getContent() != null)
                System.out.println(m.getContent());
            else
                System.out.println(m.getPhoto());
            if (m.getReacts() != null) {
                for (Reaction react : m.getReacts()) {
                    System.out.println("\t" + react.getActorName() + " reacted with a " + react.vibe);
                }
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args){
        new ChatRewind();
        //chat.print_msgs();
    }
}