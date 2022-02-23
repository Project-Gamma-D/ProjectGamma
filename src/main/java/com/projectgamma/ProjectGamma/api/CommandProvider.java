package com.projectgamma.ProjectGamma.api;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import java.util.function.Consumer;

public interface CommandProvider {
    static LiteralArgumentBuilder<DiscordCommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    void registerCommands(Consumer<LiteralArgumentBuilder<DiscordCommandSource>> commandRegisterer);
}
