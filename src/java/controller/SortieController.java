package controller;

import bean.Sortie;
import bean.SortieItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.SortieFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import service.SortieItemFacade;

@Named("sortieController")
@SessionScoped
public class SortieController implements Serializable {

    @EJB
    private service.SortieItemFacade sortieItemFacade;
    @EJB
    private service.SortieFacade ejbFacade;
    private List<Sortie> items = null;
    private Sortie selected;
    
    private SortieItem sortieItem;
    private List<SortieItem> sortieItems;
    
    public void generatePdf() throws JRException, IOException {
        sortieItemFacade.generatePdf();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void findBySortie(Sortie sortie) {
        sortieItems = (sortieItemFacade.findBySortie(sortie));
    }

    public void remove(Sortie sortie) {
        ejbFacade.remove(sortie);
        sortieItems = null;
        int index = getItems().indexOf(sortie);
        if (index != -1) {
            getItems().remove(index);
        }
    }

    public void add() {
        sortieItemFacade.add(getSortieItem(), getSortieItems());
    }

    public void save() {
        ejbFacade.save(getSelected(), getSortieItems());
        initAttribute();
    }

    public void reset() {
        initAttribute();
    }

    private void initAttribute() {
        setSelected(null);
        setSortieItem(null);
        setSortieItems(null);
    }

    public SortieController() {
    }

    public Sortie getSelected() {
        if (selected == null) {
            selected = new Sortie();
        }
        return selected;
    }

    public SortieFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SortieFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public SortieItemFacade getSortieItemFacade() {
        return sortieItemFacade;
    }

    public void setSortieItemFacade(SortieItemFacade sortieItemFacade) {
        this.sortieItemFacade = sortieItemFacade;
    }

    public SortieItem getSortieItem() {
        if (sortieItem == null) {
            sortieItem = new SortieItem();
        }
        return sortieItem;
    }

    public void setSortieItem(SortieItem sortieItem) {
        this.sortieItem = sortieItem;
    }

    public List<SortieItem> getSortieItems() {
        if (sortieItems == null) {
            sortieItems = new ArrayList();
        }
        return sortieItems;
    }

    public void setSortieItems(List<SortieItem> sortieItems) {
        this.sortieItems = sortieItems;
    }

    public void setSelected(Sortie selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SortieFacade getFacade() {
        return ejbFacade;
    }

    public Sortie prepareCreate() {
        selected = new Sortie();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SortieCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SortieUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SortieDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Sortie> getItems() {
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

    public Sortie getSortie(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Sortie> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Sortie> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Sortie.class)
    public static class SortieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SortieController controller = (SortieController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sortieController");
            return controller.getSortie(getKey(value));
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
            if (object instanceof Sortie) {
                Sortie o = (Sortie) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Sortie.class.getName()});
                return null;
            }
        }

    }

}
