package kz.epam.tcfp.controller;

import kz.epam.tcfp.dao.ImageDAO;
import kz.epam.tcfp.dao.exception.DAOException;
import kz.epam.tcfp.dao.factory.DAOFactory;
import kz.epam.tcfp.model.Image;
import kz.epam.tcfp.service.PagePath;
import org.apache.catalina.core.ApplicationPart;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */

@MultipartConfig(fileSizeThreshold=1024*1024*2,
        maxFileSize=1024*1024*10,
        maxRequestSize=1024*1024*50)
public class ImageUploadController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ImageUploadController.class);
    public static final String ADVERTISEMENT_ID = "ad_id";
    public static final char QUESTION_MARK = '?';
    public static final char EQUAL_SIGN = '=';
    public static final String ADVERTISEMENT_VIEW_SERVICE = "/advertisement/view";
    public static final String IMAGE_LOCATION_PARAMETER = "imageLocation";
    public static final String REGEX_DOT = "\\.";
    public static final String DOT = ".";


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String savePath = request.getServletContext().getInitParameter(IMAGE_LOCATION_PARAMETER);
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String advertisementIdInput = request.getParameter(ADVERTISEMENT_ID);
        Long advertisementId = null;
        if (advertisementIdInput == null || advertisementIdInput.isEmpty()) {
            response.sendRedirect(PagePath.ERROR_JSP);
            return;
        } else {
            advertisementId = Long.parseLong(advertisementIdInput);
        }

        if (request.getParts().size() > 1 || advertisementIdInput == null
            || advertisementIdInput.isEmpty()) {
            response.sendRedirect(PagePath.ERROR_JSP);
        }
        String partName = null;
        String[] partNameAndExtension = null;
        String imageExtension = null;
        for (Part part : request.getParts()) {
            partName = ((ApplicationPart) part).getSubmittedFileName();
            partNameAndExtension = partName.split(REGEX_DOT);
            imageExtension = partNameAndExtension[partNameAndExtension.length - 1];
            part.write(savePath + File.separator + advertisementIdInput + DOT + imageExtension);
        }
        Image image = new Image();
        image.setAdvertisementId(advertisementId);
        image.setPath(advertisementIdInput + DOT + imageExtension);
        ImageDAO imageDAO = DAOFactory.getImageDAO();
        try {
            if (!imageDAO.postImage(image)) {
                response.sendRedirect(PagePath.ERROR_JSP);
                return;
            }
        } catch (DAOException e) {
            LOGGER.warn("Error while getting image", e);
        }
        response.sendRedirect(ADVERTISEMENT_VIEW_SERVICE + QUESTION_MARK
                            + ADVERTISEMENT_ID + EQUAL_SIGN + advertisementIdInput);
    }
}
