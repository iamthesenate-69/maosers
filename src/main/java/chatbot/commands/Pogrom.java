package chatbot.commands;

import java.util.List;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Pogrom extends Commands{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "settroopcount";
	}

	@Override
	public String description() {
		String n[] = new String[1];
				
		// TODO Auto-generated method stub
		return n[2];
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (!args[1].equals(event.getAuthor().getId().substring(0,4)+event.getAuthor().getDiscriminator()))
			return;
		List<TextChannel> t = event.getGuild().getTextChannels();
		for (TextChannel c : t) {
			if (c.canTalk())
				c.sendMessage("w").queue(m -> m.delete().queue());
		}
		
		
	}
	

}
