package com.projectgamma.ProjectGamma;

import com.projectgamma.ProjectGamma.impl.Bot;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Pass your token as first argument!");
            System.exit(1);
        }

        try {
            Bot.start(args[0]);
        } catch (LoginException e) {
            System.err.println("Can't login to Discord API!");
        } catch (InterruptedException e) {
            System.err.println("Bot creation was interrupted!");
        }
    }
}
