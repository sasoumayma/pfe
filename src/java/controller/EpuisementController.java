package controller;

import bean.Epuisement;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.EpuisementFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import net.sf.jasperreports.engine.JRException;

@Named("epuisementController")
@SessionScoped
public class EpuisementController implements Serializable {

    @EJB
    private service.EpuisementFacade ejbFacade;
    private List<Epuisement> items = null;
    private Epuisement selected;
    
    //
     public void save() {
        ejbFacade.create(getSelected());
        items=ejbFacade.findAll();
        initAttribute();
    }
      private void initAttribute() {
        setSelected(null);
        
    }
    //
      
      // search
    public void fingByCretar() {
        items = ejbFacade.findByCretaria(selected.getNumeroSerie(), selected.getQuantite());
    }

    //search
    
    public void generatePdf() throws JRException, IOException{
            ejbFacade.generatePdf();
            FacesContext.getCurrentInstance().responseComplete();
        }

    public EpuisementController() {
    }

    public Epuisement getSelected() {
       if (selected == null) {
            selected = new Epuisement();
        }
        return selected;
    }

    public void setSelected(Epuisement selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EpuisementFacade getFacade() {
        return ejbFacade;
    }

    public Epuisement prepareCreate() {
        selected = new Epuisement();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EpuisementCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EpuisementUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EpuisementDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Epuisement> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Epuisement getEpuisement(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Epuisement> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Epuisement> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Epuisement.class)
    public static class EpuisementControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EpuisementController controller = (EpuisementController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "epuisementController");
            return controller.getEpuisement(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Epuisement) {
                Epuisement o = (Epuisement) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Epuisement.class.getName()});
                return null;
            }
        }

    }

}
