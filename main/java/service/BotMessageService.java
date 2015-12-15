package service;

import org.renaldo.testbot.utilities.BotREST_API;

public class BotMessageService {
	
	private BotREST_API rMessage = new BotREST_API();
	
	public String sendMessageToBot_REST(String _message)
	{
		String response = rMessage.sendMessageToBot(_message);
		return response;
	}
}
