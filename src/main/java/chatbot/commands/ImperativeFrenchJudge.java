package chatbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class ImperativeFrenchJudge extends Commands {



    static final HashMap<String, String> starget = new HashMap<>();
    static final HashMap<String, String> sdirect = new HashMap<>();
    static final HashMap<String, String> sindirect = new HashMap<>();
    static final HashMap<String, String> splace = new HashMap<>();
    static final HashMap<String, String> ssome = new HashMap<>();

    @Override
    public String name() {
        return "imperatif";
    }

    @Override
    public String description() {
        return "https://dmoj.ca/problem/frimp is bad problem dont do not free 15pp";
    }

    @Override
    public String usage() {
        return "<infinitive> <tu/vous/nous><pronoun(s)><verb>";
    }

    @Override
    public int cooldown() {
        return 0;
    }

    public ImperativeFrenchJudge() {
        initialize();
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        String text = "";

        for (int i = 1; i < args.length; i++) {
            text += args[i] + " ";
        }

        String clean = text.replace(":", " ").replace(".", " ").toLowerCase().trim(); //removes punctuation and converts to lowercase alphabet (promettre tu me le promets)

        String infinitive = isolateFirst(clean);
        clean = removeFirst(clean); //removes infinitive in front (tu me le promets)

        String pronoun = isolateFirst(clean);
//        System.out.println(clean);
        clean = removeFirst(clean); //removes subject in front (me le promets)

//        System.out.println(clean);

        String[] element = clean.split(" ");
        String verb = element[element.length-1]; //me le -promets-
        verb = verb.substring(0, 1).toUpperCase() + verb.substring(1); //capitalize first letter Promets





        String target = "", direct = "", indirect = "", place = "", some = "";

        for (int i = 0; i < element.length; i++) {
            String s = element[i];
            if (starget.containsKey(s) && target.equals("")) {
                target = starget.get(s); continue;
            }
            else if (sdirect.containsKey(s) && direct.equals("")) {
                direct = sdirect.get(s); continue;
            }
            else if (sindirect.containsKey(s) && indirect.equals("")) {
                indirect = sindirect.get(s); continue;
            }
            else if (splace.containsKey(s) && place.equals("")) {
                place = splace.get(s); continue;
            }
            else if (ssome.containsKey(s) && some.equals("")) {
                some = ssome.get(s); break;
            }
        }

        if (pronoun.equals("tu") && infinitive.endsWith("er") && verb.endsWith("s") && !(target.equals("") && direct.equals("") && indirect.equals("") && (!place.equals("") || !some.equals("")))) { //remove 's' if its 'er' verb with 's' and tu as the subject and some other shi d
            verb = verb.substring(0,verb.length()-1);
        }



        String hlfrench = verb+direct+target+indirect+place+some+" !";

        hlfrench = hlfrench.replace("oi-e","'e").replace("oi-y","'y");  //funi way to turn Vas-toi-y! -> Vas-t'y!


//        System.out.println(place);
        event.getChannel().sendMessage(hlfrench).queue();

//        promettre: Tu me le promets.
//        aller: Tu y vas.

    }


    static void initialize() {
        starget.put("m", "-moi");
        starget.put("me", "-moi");
        starget.put("t", "-toi");
        starget.put("te", "-toi");
        starget.put("nous", "-nous");
        starget.put("vous", "-vous");

        sdirect.put("l","-le");
        sdirect.put("le","-le");
        sdirect.put("les","-les");
        sdirect.put("la","-la");

        sindirect.put("lui","-lui");
        sindirect.put("leur","-leur");

        splace.put("y","-y");
        ssome.put("en","-en");
    }

    static String isolateFirst(String s) {
        return s.substring(0,s.indexOf(" "));
    }

    static String removeFirst(String s) {
        return s.substring(s.indexOf(" ")).trim();
    }




    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
