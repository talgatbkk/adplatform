package kz.epam.tcfp.command;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class CommandFactory {

    private static CommandFactory instance = new CommandFactory();
    private CommandSupplier commandSupplier = new CommandSupplier();

    public CommandFactory() {
    }


    public static Command getCommand(String commandName) {
        return getInstance().commandSupplier.getCommand(commandName);
    }


    private static synchronized CommandFactory getInstance() {
        if (instance == null){
            instance = new CommandFactory();
        }
        return instance;
    }
}
