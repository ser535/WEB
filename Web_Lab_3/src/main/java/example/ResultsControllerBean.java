package example;

import example.db.DAOFactory;
import example.entity.ResultEntity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ResultsControllerBean implements Serializable {
    private String y = "0.0";
    private String r;
    private String x = "0.0";

    private String hiddenY;
    private  String hiddenX;


    private PointCheckerBean pointCheckerBean;

    public PointCheckerBean getPointCheckerBean() {
        return pointCheckerBean;
    }

    public void setPointCheckerBean(PointCheckerBean pointCheckerBean) {
        this.pointCheckerBean = pointCheckerBean;
    }


    public ResultsControllerBean() {
    }


    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getHiddenX() {
        return hiddenX;
    }

    public void setHiddenX(String x) {
        this.hiddenX = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHiddenY() {
        return hiddenY;
    }

    public void setHiddenY(String y) {
        this.hiddenY = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }


    public List<ResultEntity> getResults() throws IOException {
        try {
            return new ArrayList<>(DAOFactory.getInstance().getResultDAO().getAllResults());
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        }
        return new ArrayList<>();
    }

    private double toDouble(double n) {
        BigDecimal bd = new BigDecimal(n).setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void checkPoint(String xStr, String yStr, String rStr) throws IOException {

        try {
            double x = toDouble(Double.parseDouble(xStr));
            double y = toDouble(Double.parseDouble(yStr));
            double r = toDouble(Double.parseDouble(rStr));
            System.out.println("x: " + x + " y: " + y + " r: " + r);
            boolean isValid = pointCheckerBean.validation(x, y, r);
            if (isValid) {
                boolean checkPoint = pointCheckerBean.checkPoint(x, y, r);
                DAOFactory.getInstance().getResultDAO().addNewResult(new ResultEntity(x, y, r, checkPoint));
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid point data");
                facesContext.responseComplete();
            }
        } catch (NumberFormatException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid point data");
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            facesContext.responseComplete();
        }
    }
}