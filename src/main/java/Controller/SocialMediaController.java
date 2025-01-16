package Controller;


import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);

        app.post("/login", this::loginHandler);

        app.post("/messages", this::postMessage);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();

        Account a = this.accountService.createAccount(m.readValue(ctx.body(), Account.class));
        if (a == null) {
            ctx.status(400);
        } else {
            ctx.json(a);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();

        Account a = this.accountService.login(m.readValue(ctx.body(), Account.class));
        if (a == null) {
            ctx.status(401);
        } else {
            ctx.json(a);
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper o = new ObjectMapper();

        Message m = this.messageService.createMessage(o.readValue(ctx.body(), Message.class));
        if (m == null) {
            ctx.status(400);
        } else {
            ctx.json(m);
        }
    }

    private void getMessages(Context ctx) {
        List<Message> msgs = this.messageService.getMessages();
        ctx.json(msgs);
    }


}