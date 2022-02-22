package com.projectgamma.ProjectGamma;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Collection;
import java.util.EnumSet;

public class Bot extends ListenerAdapter {

    private final CommandDispatcher<MessageReceivedEvent> commandDispatcher;

    private Bot() {
        this.commandDispatcher = new CommandDispatcher<>();

        // Test command
        this.commandDispatcher.register(
                LiteralArgumentBuilder.<MessageReceivedEvent>literal("hello").executes(c -> {
                    MessageReceivedEvent event = c.getSource();
                    Message msg = event.getMessage();
                    User user = event.getAuthor();

                    msg.replyFormat("Hello, %s!", user.getAsMention()).queue();

                    return 0;
                })
        );

        this.commandDispatcher.register(
                LiteralArgumentBuilder.<MessageReceivedEvent>literal("commands").executes(c -> {
                    StringBuilder usage = new StringBuilder();
                    for (String s : commandDispatcher.getSmartUsage(commandDispatcher.getRoot(), c.getSource()).values()) {
                        usage.append("!");
                        usage.append(s);
                        usage.append('\n');
                    }
                    MessageEmbed helpMessage = new EmbedBuilder()
                            .setTitle("Gamma project command list")
                            .setDescription(usage)
                            .build();

                    c.getSource().getChannel().sendMessageEmbeds(helpMessage).queue();
                    return 0;
                })
        );
    }

    public static void start(String api_token) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createLight(api_token, get_required_intents())
                .addEventListeners(new Bot())
                .setActivity(Activity.playing("Type !commands"))
                .build();

        jda.awaitReady();
    }

    public static Collection<GatewayIntent> get_required_intents() {
        return EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().startsWith("!")) {
            String cmd = msg.getContentRaw().substring(1);
            try {
                this.commandDispatcher.execute(cmd, event);
            } catch (CommandSyntaxException e) {
                msg.reply("Invalid command syntax! Use !commands to see a list of commands").queue();
            }
        }
    }
}
