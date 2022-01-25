package chatbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Sudo extends Commands{


	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "sudo";
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "shid";
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return "shid";
	}

	@Override
	public int cooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (!event.getAuthor().getId().equals("372123318167273502"))
			return;
		String s = "";
		for (int i = 1; i < args.length; i++) {
			s += args[i] + " ";
		}
		event.getChannel().sendMessage(s.trim()).queue();
		
	}



}
