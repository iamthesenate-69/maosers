package chatbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Log {

	static Path path;
	static TextChannel telescreen;
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	final static String filename = "pog.txt";
	static int Messagelimit = 20;
	static String guildid;
	static String channelid;
	
	
	public Log(String _guildid, String _channelid, ReadyEvent event) throws IOException {
		guildid = _guildid;
		channelid = _channelid;
		telescreen = event.getJDA().getGuildById(guildid).getTextChannelById(channelid);
		new File(filename).createNewFile(); //creates a file if there is no
		path = Paths.get(filename);
	}
	
	static void logInFile(String msgid, String name, String channelname, String guildname, String message, List<Attachment> attachments) {
		String s = String.format("%s | [guild][channel] (%s; %s) [sent][by] %s: %s",msgid,guildname,channelname,name,message);
		for (Attachment a : attachments) 
			s += String.format(" ATTACHMENT [%s](%s)",a.getFileName(),a.getUrl());
		try {
			Files.writeString(path, formatter.format(System.currentTimeMillis()) + " " + s.replace("\n", " ") + "\n", StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}

	public static void archiveMessage(MessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().equals("w") && event.getAuthor().equals(event.getJDA().getSelfUser()))
			return;
		
		telescreen = event.getJDA().getGuildById(guildid).getTextChannelById(channelid);
		if (event.getChannel() == telescreen) return;
		compile(event.getAuthor(), event.isFromGuild()?event.getGuild():null, event.getChannel(), event.getMessage());
	}

	static void compile(User author, Guild guild, MessageChannel channel, Message message) {
		String limitMessage = Limit(message.getContentRaw());
		String guildName = guild != null?guild.getName():"DM";
		logInFile(message.getId(), author.getName(), channel.getName(), guildName, limitMessage, message.getAttachments());
		createEmbed(channel.getName(), guildName, getDiscordMessageLink(guild, channel, message), author, limitMessage, message.getAttachments(), message.getEmbeds());
	}

	static void createEmbed(String channelName, String guildName, String link, User author, String limitMessage, List<Attachment> attachments, List<MessageEmbed> embeds) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(String.format("sent from %s, %s",channelName,guildName), null)
			.setAuthor(author.getName(), null, author.getAvatarUrl())
			.setFooter("Message Sent Event", telescreen.getJDA().getSelfUser().getAvatarUrl())
			.setDescription(limitMessage)
			.addField("Link", link, false);
		for (Attachment a : attachments) {
			eb.addField(a.getFileName(), a.getUrl(), true)
				.setImage(a.getUrl());
		}
		telescreen.sendMessage(eb.build()).queue();
		for (MessageEmbed e : embeds)
			telescreen.sendMessage(e).queue();
	}

	static String getDiscordMessageLink(Guild g, MessageChannel c, Message m) {
		return g != null?String.format("https://discord.com/channels/%s/%s/%s",g.getId(),c.getId(),m.getId()):String.format("https://discord.com/channels/@me/%s/%s",c.getId(),m.getId());
	}
	
	static String Limit(String s) {
		int newline = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') newline++;
			if (newline == 20) return s.substring(0, i);
		}
		return s;
	}
}
