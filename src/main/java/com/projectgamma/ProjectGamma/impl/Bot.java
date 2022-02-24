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

/**
 * The bot class that orchestrates the whole system.
 */
public class Bot extends ListenerAdapter {

    private final CommandDispatcher<DiscordCommandSource> commandDispatcher;

    private Bot() {
        this.commandDispatcher = new CommandDispatcher<>();
    }

    /**
     * Adds a command provider to the bot.
     *
     * @param commandProviders The command provider to add to the bot
     * @see CommandProvider
     */
    public void addCommandProviders(CommandProvider... commandProviders) {
        for (CommandProvider provider: commandProviders) {
            provider.registerCommands(commandDispatcher::register);
        }
    }

    /**
     * Creates a Bot instance and a handle to the Discord API using the JDA library. This is the entrypoint to the app.
     *
     * @param api_token The Discord API token of the bot user
     * @throws LoginException Thrown when the API token can't be used to access the Discord API
     * @throws InterruptedException Thrown if the JDA library is interrupted during its initialization
     */
    public static void start(String api_token) throws LoginException, InterruptedException {
        Bot bot = new Bot();

        JDA jda = JDABuilder.createLight(api_token, get_required_intents()) // Create a lightweight bot
                .addEventListeners(bot)                                     // Listen to events
                .setActivity(Activity.playing("Type !commands"))            // Set the activity shown in the profile
                .build();

        bot.addCommandProviders(CoreCommands.INSTANCE); // Add the core commands of the bot

        jda.awaitReady(); // Don't return until the bot is fully initialized
    }

    /**
     * Returns a list with the Discord API intents required by the bot.
     * @return The required intent list
     */
    public static Collection<GatewayIntent> get_required_intents() {
        return EnumSet.of(
                GatewayIntent.GUILD_MESSAGES, // Provides access to the messages in Guild channels
                GatewayIntent.DIRECT_MESSAGES // Provides access to DM messages
        );
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().startsWith("!")) { // Filter commands using the '!' prefix
            String cmd = msg.getContentRaw().substring(1); // Remove the prefix from the command string
            try {
                this.commandDispatcher.execute(cmd, new DiscordCommandSourceImpl(event, commandDispatcher)); // Dispatch the command
            } catch (CommandSyntaxException e) {
                msg.reply("Invalid command syntax! Use !commands to see a list of commands").queue();
            }
        }
    }
}
