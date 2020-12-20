package kz.epam.tcfp.command.implementation;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.command.PagePath;
import kz.epam.tcfp.model.Advertisement;
import kz.epam.tcfp.model.Customer;
import kz.epam.tcfp.service.AdvertisementService;
import kz.epam.tcfp.service.CustomerService;
import kz.epam.tcfp.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class FindAds implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdvertisementService service = ServiceFactory.getAdvertisementService();
        List<Advertisement> advertisements = service.getAdvertisement(1);
        request.setAttribute("ads", advertisements);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ADVERTISEMENT);
        dispatcher.forward(request, response);

    }
}
