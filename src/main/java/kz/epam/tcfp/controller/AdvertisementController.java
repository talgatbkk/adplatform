package kz.epam.tcfp.controller;

import kz.epam.tcfp.command.Command;
import kz.epam.tcfp.command.CommandFactory;
import kz.epam.tcfp.command.implementation.FindAds;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class AdvertisementController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandFactory.getCommand("get_ads").execute(req, resp);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("advertisement.jsp");
//        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

