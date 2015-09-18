/**
 * Created by ilkaygunel on 17/09/15.
 */
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.Servlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class CookieBean {
    List<String> people;
    List<String> addedPeople;

    @PostConstruct
    public void init()
    {
        addedPeople=new ArrayList<>();
        people=new ArrayList<>();

        people.add("Akın Kaldıroğlu");
        people.add("Özcan Acar");
        people.add("Çağatay Çivici");
        people.add("Melih Sakarya");
    }

    public void addToList(String person) throws UnsupportedEncodingException
    {
        addedPeople.add(person);

        // create cookie
        Cookie existingCookie=null;

        HttpServletRequest httpServletRequest =
                (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null)
        {
            for(int i=0; i<cookies.length; i++){
                if (cookies[i].getName().equalsIgnoreCase("addedPeopleList")){
                    existingCookie=cookies[i];
                }
            }
        }

        if (existingCookie==null)
        {
            HttpServletResponse httpServletResponse =
                    (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            Cookie cookie = new Cookie("addedPeopleList", URLEncoder.encode(addedPeople.toString().replace("]","").replace("[",""),"UTF-8"));
            cookie.setMaxAge(365);
            cookie.setComment("Kullanıcının Listeye Eklediği Ürünler");
            httpServletResponse.addCookie(cookie);
        }

        else
        {
            HttpServletResponse httpServletResponse =
                    (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            existingCookie.setValue(URLEncoder.encode(addedPeople.toString().replace("]", "").replace("[", ""), "UTF-8"));
            existingCookie.setMaxAge(365);
            existingCookie.setComment("Kullanıcının Listeye Eklediği Ürünler");
            httpServletResponse.addCookie(existingCookie);
        }
    }

    public List<String> getPeople() {
        return people;
    }
}
