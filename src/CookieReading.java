import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ilkaygunel on 17/09/15.
 */

@ManagedBean
@SessionScoped
public class CookieReading {
    public String getCookieValue() throws UnsupportedEncodingException
    {
        String cookieValue="";
        HttpServletRequest httpServletRequest =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null)
        {
            for(int i=0; i<cookies.length; i++){
                if (cookies[i].getName().equalsIgnoreCase("addedPeopleList")){
                    cookieValue = cookies[i].getValue();
                }
            }
        }
        return URLDecoder.decode(cookieValue,"UTF-8");
    }
}
