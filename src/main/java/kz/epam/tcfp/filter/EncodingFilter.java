package kz.epam.tcfp.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class EncodingFilter implements Filter {

    private static final String CODE_ENCODING = "UTF_ENCODE";
    private static final String CONFIG_CONTEXT_TYPE = "UTF_ENCODE";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
