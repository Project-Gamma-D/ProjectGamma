package com.projectgamma.ProjectGamma.api;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import java.util.function.Consumer;

/**
 * Objects implementing this interface can be passed to some objects in order to register commands to the bot.
 */
public interface CommandProvider {

    /**
     * This utility method returns an instance of a {@link LiteralArgumentBuilder} usable in
     * {@link CommandProvider#registerCommands(Consumer)}. For more details, see the
     * <a href="https://github.com/Mojang/brigadier#registering-a-new-command">Brigadier Documentation</a>
     *
     * @param name The name of the command
     * @return An argument builder
     */
    static LiteralArgumentBuilder<DiscordCommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * This method will be called to register all your commands with the command dispatcher of the server.
     *
     * @param commandRegisterer Use {@link Consumer#accept(Object)} on this object to provide your commands to the
     * command dispatcher
     */
    void registerCommands(Consumer<LiteralArgumentBuilder<DiscordCommandSource>> commandRegisterer);
}
