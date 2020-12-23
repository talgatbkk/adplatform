package kz.epam.tcfp.command;

import kz.epam.tcfp.command.implementation.FindAds;
import kz.epam.tcfp.command.implementation.SignIn;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CommandSupplier {

    private final Map<String, Command> commands;



    public CommandSupplier() {
        commands = new HashMap<>();
        commands.put("get_ads", new FindAds());
        commands.put("sign_in", new SignIn());

    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
