package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO m) {
        this.messageDAO = m;
    }

    public Message createMessage(Message m) {
        String text = m.getMessage_text();
        if(text.isBlank() || text.length() > 255) {
            return null;
        }
        return this.messageDAO.insertMessage(m);
    }

    public List<Message> getMessages() {
        return this.messageDAO.getMessages();
    }

}
