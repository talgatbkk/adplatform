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
        commands.put("view_profile", new OpenProfile());
        commands.put("view_ad", new ViewAdvertisement());
        commands.put("post_comment", new PostComment());
        commands.put("post_ad", new PostAdvertisement());
        commands.put("input_ad", new InputAdvertisement());
        commands.put("search", new SearchAdvertisement());
        commands.put("delete_ad", new DeleteAdvertisement());
        commands.put("logout", new LogOut());

    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
