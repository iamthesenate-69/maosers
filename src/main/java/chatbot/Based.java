package chatbot;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Based {

	public static void main(String[] args) throws IOException, LoginException {

		BasicConfigurator.configure();

		API mao = new API("NzMwMTA3MTc5MzUyMTk1MTM0.XwSrTA.hhuf42HfXaNa56YWC-qX_V338oE", 
				Activity.streaming("french ecoute", "https://www.youtube.com/watch?v=PTp4MWBLcZY"), OnlineStatus.INVISIBLE);
		
		API[] bot = {mao};
		new Bot(bot, "~ \\ ilikedtheguywhokilledhitler");
	
	}



}
