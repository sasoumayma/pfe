package controller;

import bean.Maintenance;
import bean.MaintenanceItem;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.MaintenanceFacade;

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

@Named("maintenanceController")
@SessionScoped
public class MaintenanceController implements Serializable {

    @EJB
    private service.MaintenanceItemFacade maintenanceItemFacade;
    @EJB
    private service.MaintenanceFacade ejbFacade;
    private List<Maintenance> items = null;
    private Maintenance selected;
    private int showMaintenance;
    private int typeMaintenance;

    //
    private MaintenanceItem maintenanceItem;
    private List<MaintenanceItem> maintenanceItems;
    //

    //
    public void save() {
        ejbFacade.save(getSelected(), getMaintenanceItems());
        initAttribute();
    }

    public void add() {
        maintenanceItemFacade.add(getMaintenanceItem(), getMaintenanceItems());
    }

    private void initAttribute() {
        setSelected(null);
        setMaintenanceItem(null);
        setMaintenanceItems(null);
    }

    public MaintenanceItem getMaintenanceItem() {
        if (maintenanceItem == null) {
            maintenanceItem = new MaintenanceItem();
        }
        return maintenanceItem;
    }

    public void setMaintenanceItem(MaintenanceItem maintenanceItem) {
        this.maintenanceItem = maintenanceItem;
    }

    public List<MaintenanceItem> getMaintenanceItems() {
        if (maintenanceItems == null) {
            maintenanceItems = new ArrayList();
        }
        return maintenanceItems;
    }

    public void setMaintenanceItems(List<MaintenanceItem> maintenanceItems) {
        this.maintenanceItems = maintenanceItems;
    }

    public MaintenanceController() {
    }

    public int getTypeMaintenance() {
        return typeMaintenance;
    }

    public void setTypeMaintenance(int typeMaintenance) {
        this.typeMaintenance = typeMaintenance;
    }

    public int getShowMaintenance() {
        return showMaintenance;
    }

    public void setShowMaintenance(int showMaintenance) {
        this.showMaintenance = showMaintenance;
    }

    public Maintenance getSelected() {
        if (selected == null) {
            selected = new Maintenance();
        }
        return selected;
    }

    public void setSelected(Maintenance selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MaintenanceFacade getFacade() {
        return ejbFacade;
    }

    public Maintenance prepareCreate() {
        selected = new Maintenance();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MaintenanceCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MaintenanceUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MaintenanceDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Maintenance> getItems() {
        items = getFacade().findAll();
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

    public Maintenance getMaintenance(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Maintenance> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Maintenance> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Maintenance.class)
    public static class MaintenanceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaintenanceController controller = (MaintenanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "maintenanceController");
            return controller.getMaintenance(getKey(value));
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
            if (object instanceof Maintenance) {
                Maintenance o = (Maintenance) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Maintenance.class.getName()});
                return null;
            }
        }

    }

}
