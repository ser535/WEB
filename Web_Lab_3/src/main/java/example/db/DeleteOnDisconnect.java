package example.db;

import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.io.Serializable;

public class DeleteOnDisconnect implements Serializable {
    private String sesId;

    public DeleteOnDisconnect() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        sesId = session.getId();
    }

    @PreDestroy
    public void cleanup() {
        DAOFactory.getInstance().getResultDAO().deleteResult(sesId);
    }

    public String getSessionId() {
        return sesId;
    }
}