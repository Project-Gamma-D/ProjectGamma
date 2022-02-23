package com.projectgamma.ProjectGamma.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.projectgamma.ProjectGamma.api.CommandProvider;
import com.projectgamma.ProjectGamma.api.DiscordCommandSource;
import com.projectgamma.ProjectGamma.impl.commands.CoreCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Collection;
import java.util.EnumSet;

public class Bot extends ListenerAdapter {

    private final CommandDispatcher<DiscordCommandSource> commandDispatcher;

    private Bot() {
        this.commandDispatcher = new CommandDispatcher<>();
    }

    public void addCommandProviders(CommandProvider... commandProviders) {
        for (CommandProvider provider: commandProviders) {
            provider.registerCommands(commandDispatcher::register);
        }
    }

    public static void start(String api_token) throws LoginException, InterruptedException {
        Bot bot = new Bot();

        JDA jda = JDABuilder.createLight(api_token, get_required_intents())
                .addEventListeners(bot)
                .setActivity(Activity.playing("Type !commands"))
                .build();

        bot.addCommandProviders(CoreCommands.INSTANCE);

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
                this.commandDispatcher.execute(cmd, new DiscordCommandSourceImpl(event, commandDispatcher));
            } catch (CommandSyntaxException e) {
                msg.reply("Invalid command syntax! Use !commands to see a list of commands").queue();
            }
        }
    }
}
