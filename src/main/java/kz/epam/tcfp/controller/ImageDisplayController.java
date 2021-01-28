package kz.epam.tcfp.controller;

import org.apache.log4j.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class ImageDisplayController extends HttpServlet {
    public static final String IMAGE_LOCATION_PARAMETER = "imageLocation";
    public static final String IMAGES_FOLDER = "/images/";
    public static final String IMAGE_JPEG_OR_PNG = "image/jpeg";
    private static final Logger LOGGER = Logger.getLogger(ImageDisplayController.class);

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String URLAfterWebDomain = request.getRequestURI();
        if (Boolean.FALSE.equals(URLAfterWebDomain.startsWith(IMAGES_FOLDER)))
            return;
        String imagesBase = request.getServletContext().getInitParameter(IMAGE_LOCATION_PARAMETER);
        String relativeImagePath = URLAfterWebDomain.substring(IMAGES_FOLDER.length());
        response.setContentType(IMAGE_JPEG_OR_PNG);
        try (ServletOutputStream outStream = response.getOutputStream();
             FileInputStream fin = new FileInputStream(imagesBase + relativeImagePath);
             BufferedInputStream bin = new BufferedInputStream(fin);
             BufferedOutputStream bout = new BufferedOutputStream(outStream)) {
            int ch = 0;
            while ((ch = bin.read()) != -1)
                bout.write(ch);
        } catch (IOException e) {
            LOGGER.warn("Exception while trying to display an image", e);
        }
    }

}