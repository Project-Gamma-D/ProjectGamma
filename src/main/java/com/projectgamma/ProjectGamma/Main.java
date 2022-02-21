package com.projectgamma.ProjectGamma;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.requests.Route;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("Pass your token as first argument!");
            System.exit(1);
        }

        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new Main())
                .setActivity(Activity.playing("Type !hello"))
                .build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!hello")) {
            String response;
            if (msg.getMember() == null) {
                response = "Hello, Discord!";
            } else {
                response = "Hello, " + msg.getMember().getAsMention() + "!";
            }
            event.getChannel().sendMessage(response).queue();
        } else if (msg.getContentRaw().equals("!stop")) { // Extremely dangerous, but useful for now
            msg.addReaction("✅").queue();
            event.getJDA().shutdown();
        }
    }
}
