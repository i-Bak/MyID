import nl.jochemkuijpers.telegram.ParseMode;
import nl.jochemkuijpers.telegram.TelegramBot;
import nl.jochemkuijpers.telegram.TelegramException;
import nl.jochemkuijpers.telegram.model.Chat;
import nl.jochemkuijpers.telegram.model.Message;
import nl.jochemkuijpers.telegram.model.Update;
import nl.jochemkuijpers.telegram.model.User;

public class GetMyIDBot extends TelegramBot {

    public GetMyIDBot(String token) throws TelegramException {
        super(token);
    }

    @Override
    public void onUpdate(Update update) {
        Message message = update.getMessage();
        Chat chat = message.getChat();

        if (!message.isCommand()) {
            return;
        }

        switch (message.getCommand().getName()) {
        case "start":
            sendInfo(message);
            break;

        case "help":
            sendHelpText(chat.getId());
            break;

        default:
            api.sendMessage(chat.getId(), "Sorry, I don't know that command. 😐 ");
            break;
        }
    }
    
    private void sendInfo(Message message) {
        StringBuilder sb = new StringBuilder();
        Chat chat = message.getChat();
        User user = message.getFrom();

        // Chat info
        sb.append("*Chat* 💬\n");
        sb.append("ID: `" + chat.getId() + "`\n");
        sb.append("Type: " + chat.getType().toString() + "\n");
        sb.append("Title: " + (chat.hasTitle() ? chat.getTitle() : "_not set_") + "\n");
        sb.append("First Name: " + (chat.hasFirstName() ? chat.getFirstName() : "_not set_") + "\n");
        sb.append("Last Name: " + (chat.hasLastName() ? chat.getLastName() : "_not set_") + "\n");
        sb.append("Username: " + (chat.hasUsername() ? "@" + chat.getEscapedUsername() : "_not set_") + "\n\n");

        // User info
        sb.append("*User* 👤\n");
        sb.append("ID: `" + user.getId() + "`\n");
        sb.append("First Name: " + user.getFirstName() + "\n");
        sb.append("Last Name: " + (user.hasLastName() ? user.getLastName() : "_not set_") + "\n");
        sb.append("Username: " + (user.hasUsername() ? "@" + user.getEscapedUsername() : "_not set_") + "\n");

        api.sendMessage(chat.getId(), sb.toString(), ParseMode.MARKDOWN, false, message.getId(), null);
    }

    private void sendHelpText(long chatId) {
        api.sendMessage(chatId, "Use the /start command to display interesting data.");
    }
}
