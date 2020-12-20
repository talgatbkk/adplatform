package kz.epam.tcfp.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public interface Command {


    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
