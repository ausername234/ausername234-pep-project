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
        if (isMessageValid(m)) {
            return this.messageDAO.insertMessage(m);
        }
        return null;
    }

    public List<Message> getMessages() {
        return this.messageDAO.getMessages();
    }

    public Message getMessageById(int id) {
        return this.messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return this.messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(Message m) {
        if(isMessageValid(m)) {
            return this.messageDAO.updateMessage(m);
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int id) {
        return this.messageDAO.getMessagesByAccountId(id);
    }

    private boolean isMessageValid(Message m) {
        String text = m.getMessage_text();
        return !(text.isBlank() || text.length() > 255);
    }

}
