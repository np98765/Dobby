package net.infinitenight.Dobby;

import java.util.Random;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

public class Dobby extends ListenerAdapter implements Listener {
	
	public static void main(String[] args) throws Exception {
		 PircBotX bot = new PircBotX();
		 bot.setName("Dobby");
		 bot.setVersion("HarryPotterSpells bot");
		 
		 bot.connect("irc.esper.net");
		 bot.identify("NOPE");
		 
		 bot.joinChannel("#dobby");
		 bot.joinChannel("#harrypotterspells");
		 
		 bot.getListenerManager().addListener(new Dobby());
	}
	
	@Override
	public void onInvite(InviteEvent event) {
		event.getBot().joinChannel(event.getChannel());
		event.getBot().sendMessage(event.getChannel(), "What a beautiful place... to be with friends");
	}
	
	@Override
	public void onJoin(JoinEvent event) {
		if (event.getUser().getNick().equalsIgnoreCase("Dobby")) {
			event.getBot().sendMessage(event.getChannel(), "Dobby the house-elf is here!");
		}
	}
	
	@Override
	public void onMessage(MessageEvent event) throws Exception {
		String delims = "[ ]+";
		String[] args = event.getMessage().split(delims);
		String message = event.getMessage().toLowerCase();
		String sender = event.getUser().getNick();
		Channel channel = event.getChannel();
		
		//Join command
		if (message.startsWith("!join")) {
			if (args.length == 1) {
				event.getBot().sendAction(channel, "doesn't know where to go");
			}
			
			if (args.length == 2) {
				if (args[1].contains(",")) {
					
					int i = 1;
					String[] channels = args[1].split(",");
					for (String chanToJoin : channels) {
						if (channels.length != i) {
							String channelToJoin = chanToJoin;
							event.getBot().joinChannel(channelToJoin);
							i++;
						}
					}
					
					for (int j=1; j < channels.length; j++) {
						event.getBot().joinChannel(channels[j]);
					}
					
				} else {
					event.getBot().joinChannel(args[1]);
				}
			}
		}
		
		//Part command
		if (message.startsWith("!part")) {
			if (sender.equals("np98765")) {
				event.getBot().partChannel(channel);
			} else {
				event.getBot().sendMessage(channel, "Dobby is a free house-elf and can do as he pleases!");
			}
		}
		
		//Leave command
		if (message.startsWith("!leave")) {
			if (sender.equals("np98765")) {
				event.getBot().disconnect();
			} else {
				event.getBot().sendMessage(channel, "Dobby is a free house-elf and can do as he pleases!");
			}
		}
		
		if (message.startsWith("!help")) {
			event.getBot().sendMessage(sender, "Available commands:");
			event.getBot().sendMessage(sender, "!cookie [user(s)] [reason] - Gives a cookie");
			event.getBot().sendMessage(sender, "!whip [user(s)] [reason] - Whips");
			event.getBot().sendMessage(sender, "!curse [user] - Curses a user (randomly selects curse)");
			event.getBot().sendMessage(sender, "!join <channel> - Joins a channel");
		}
		
		//Cookie command
		if (message.startsWith("!cookie")) {
			if (args.length == 1) {
				event.getBot().sendAction(channel, "gives everyone a cookie");
			}
			
			if (args.length == 2) {
				if (args[1].contains(",")) {
					String[] usernames = args[1].split(",");
					
					int i = 1;
					String names = "";
					for (String user : usernames) {
						if (usernames.length != i) {
							names += ", " + user;
						} else {
							names += "and " + user;
						}
						i++;
					}
					event.getBot().sendAction(channel, "gives " + names + " a cookie");
				} else {
					event.getBot().sendAction(channel, "gives " + args[1] + " a cookie");
				}
			}
				
			if (args.length > 2) {
				if (args[1].contains(",")) {
					String[] usernames = args[1].split(",");
					
					int i = 1;
					String names = "";
					for (String user : usernames) {
						if (usernames.length != i) {
							names += user + ", ";
						} else {
							names += "and " + user;
						}
						i++;
					}
					String reason = "";
					for (int j = 2; j < args.length; j++) {
						reason += args[j] + " ";
					}
					event.getBot().sendAction(channel, "gives " + names + " a cookie for " + reason);					
				} else {
					String reason = "";
					for (int j = 2; j < args.length; j++) {
						reason += args[j] + " ";
					}
					event.getBot().sendAction(channel, "gives " + args[1] + " a cookie for " + reason);
				}
			}
		}
		
		//Whip command
		if (message.startsWith("!whip")) {
			if (args.length == 1) {
				event.getBot().sendAction(channel, "whips everyone");
			}
			
			if (args.length == 2) {
				if (args[1].contains(",")) {
					String[] usernames = args[1].split(",");
					
					int i = 1;
					String names = "";
					for (String user : usernames) {
						if (usernames.length != i) {
							names += ", " + user;
						} else {
							names += "and " + user;
						}
						i++;
					}
					event.getBot().sendAction(channel, "whips " + names);
				} else {
					event.getBot().sendAction(channel, "whips " + args[1]);
				}
			}
				
			if (args.length > 2) {
				if (args[1].contains(",")) {
					String[] usernames = args[1].split(",");
					
					int i = 1;
					String names = "";
					for (String user : usernames) {
						if (usernames.length != i) {
							names += user + ", ";
						} else {
							names += "and " + user;
						}
						i++;
					}
					String reason = "";
					for (int j = 2; j < args.length; j++) {
						reason += args[j] + " ";
					}
					event.getBot().sendAction(channel, "whips " + names + " for " + reason);					
				} else {
					String reason = "";
					for (int j = 2; j < args.length; j++) {
						reason += args[j] + " ";
					}
					event.getBot().sendAction(channel, "whips " + args[1] + " for " + reason);
				}
			}
		}
		
		//Curse command
		if (message.startsWith("!curse")) {
			
			String[] curses = 	{
					"Babbling",
					"Blasting",
					"Conjunctivitis",
					"Ear-shrivelling",
					"Expulso",
					"Flagrante",
					"Full Body-Bind",
					"Hair Loss",
					"Imperius",
					"Jelly-Legs",
					"Killing",
					"Leg-Locker",
					"Sectumsempra",
					"Tongue-Tying",
					"Cruciatus"
					};
			int j = new Random().nextInt(15 - 0 + 1);
			
			if (args.length == 1) {
				event.getBot().sendAction(channel, "performs the " + curses[j] + " Curse on everyone");
			}
			
			if (args.length > 1) {
				if (args[1].contains(",")) {
					String[] usernames = args[1].split(",");
					
					int i = 1;
					String names = "";
					for (String user : usernames) {
						if (usernames.length != i) {
							names += user + ", ";
						} else {
							names += "and " + user;
						}
						i++;
					}
					event.getBot().sendAction(channel, "performs the " + curses[j] + " Curse on " + names);
				} else {
					event.getBot().sendAction(channel, "performs the " + curses[j] + " Curse on " + args[1]);
				}
			}
		}
	}
}

