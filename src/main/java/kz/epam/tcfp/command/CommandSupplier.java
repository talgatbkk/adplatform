package kz.epam.tcfp.command;

import kz.epam.tcfp.command.implementation.*;

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
        commands.put("sign_up", new SignUp());
        commands.put("customer_profile", new OpenProfile());
        commands.put("view_ad", new ViewAdvertisement());
        commands.put("post_comment", new PostComment());

    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
