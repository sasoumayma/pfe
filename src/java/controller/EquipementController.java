package controller;

import bean.Equipement;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.MathUtil;
import java.io.IOException;
import service.EquipementFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@Named("equipementController")
@SessionScoped
public class EquipementController implements Serializable {

    @EJB
    private service.EquipementFacade ejbFacade;
    private List<Equipement> items = new ArrayList<>();
    private Equipement selected;
    private String designation;

    // search
    public void fingByCretar() {
        items = ejbFacade.findByCretaria(selected.getType(), selected.getEmplacement(), selected.getMarque());
    }

    //search
    //statistique//
    private int annee;
    private int quantite;

    @EJB
    private EquipementFacade equipementFacade;
    private Long max = new Long(0);

    private BarChartModel barModel;
    private LineChartModel lineModel;

    private int typeChart;
    private LineChartModel chartModel;

    @PostConstruct
    public void init() {
        barModel = new BarChartModel();
        ChartSeries equipement = new ChartSeries();
        ChartSeries entree = new ChartSeries();
        for (int i = 0; i < 12; i++) {
            equipement.set("mois " + (i + 1), 0);
            entree.set("mois " + (i + 1), 0);
        }
        barModel.addSeries(equipement);
        barModel.addSeries(entree);
    }

    public void afficherChartEquipement() {
        createBarModelEquipement();
    }
//     public void afficherChartPlan() {
//        createBarModelPlan();
//    }

    private void createBarModelEquipement() {
        barModel = new BarChartModel();
        initBarModelForEquipement(barModel);
        paramGraphForEquipement(barModel);
    }

    private void paramGraphForEquipement(CartesianChartModel model) {
        model.setTitle("Statistiques de l'annee " + annee);
        model.setLegendPosition("e");
        model.setAnimate(true);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("NOMBRE D'OCCURENCE");
        yAxis.setMin(0);
        yAxis.setMax(max * (1.1));
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setTickAngle(-30);
    }

    private void initBarModelForEquipement(CartesianChartModel model) {
        attachResultatToModelForEquipement(model);
    }
//     private void initBarModelForPlan(CartesianChartModel model) {
//        attachResultatToModelForPlan(model);
//    }

    private void attachResultatToModelForEquipement(CartesianChartModel model) {
        Object[] res = equipementFacade.findIncidentAndTraitementByCriteria(annee, designation, quantite);
        max = MathUtil.calculerMax(res);
        ChartSeries equipement = new ChartSeries();
        equipement.setLabel("Equipement");
        ChartSeries entree = new ChartSeries();
        entree.setLabel("Entree");
        List<Long> equipementStat = (List<Long>) res[0];
        List<Long> entreeStat = (List<Long>) res[0];
        for (int i = 1; i <= 12; i++) {
            equipement.set("" + i, equipementStat.get(i - 1));
            entree.set("" + i, entreeStat.get(i - 1));
        }
        model.addSeries(equipement);
        model.addSeries(entree);
    }

//      private void createBarModelPlan() {
//        barModel = new BarChartModel();
//        initBarModelForPlan(barModel);
//        paramGraphForPlan(barModel);
//    }
//     private void paramGraphForPlan(CartesianChartModel model) {
//        model.setTitle("Statistiques de l'annee " + annee);
//        model.setLegendPosition("e");
//        model.setAnimate(true);
//        Axis yAxis = model.getAxis(AxisType.Y);
//        yAxis.setLabel("NOMBRE D'OCCURENCE");
//        yAxis.setMin(0);
//        yAxis.setMax(max * (1.1));
//        Axis xAxis = model.getAxis(AxisType.X);
//        xAxis.setMin(0);
//        xAxis.setTickAngle(-30);
//    }
//    private void attachResultatToModelForPlan(CartesianChartModel model) {
//        Object[] res = equipementFacade.findPlanAndExecutionByCriteria(annee, employeeDeclarant);
//        max = MathUtil.calculerMax(res);
//        ChartSeries planPreventif = new ChartSeries();
//        planPreventif.setLabel("Plan Preventif");
//        ChartSeries executionPlanPreventif = new ChartSeries();
//        executionPlanPreventif.setLabel("Execution Plan Preventif");
//        List<Long> planStat = (List<Long>) res[0];
//        List<Long> executionStat = (List<Long>) res[0];
//        for (int i = 1; i <= 12; i++) {
//            planPreventif.set("" + i, planStat.get(i - 1));
//            executionPlanPreventif.set("" + i, executionStat.get(i - 1));
//        }
//        model.addSeries(planPreventif);
//        model.addSeries(executionPlanPreventif);
//    }
//      
    public void setTypeChart(int typeChart) {
        this.typeChart = typeChart;
    }

    public BarChartModel getBarModel() {
        if (barModel == null) {
            barModel = new BarChartModel();
        }
        return barModel;
    }

    public LineChartModel getChartModel() {
        if (chartModel == null) {
            chartModel = new LineChartModel();
        }
        return chartModel;
    }

    public void setChartModel(LineChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public int getTypeChart() {
        return typeChart;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public LineChartModel getLineModel() {
        if (lineModel == null) {
            lineModel = new LineChartModel();
        }
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public int getAnnee() {
        if (annee == 0) {
            annee = new Date().getYear() + 1900;
        }
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public EquipementFacade getEquipementFacade() {
        return equipementFacade;
    }

    public void setEquipementFacade(EquipementFacade equipementFacade) {
        this.equipementFacade = equipementFacade;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    //Fin statistique//
    //
    public void save() {
        ejbFacade.create(getSelected());
        items = ejbFacade.findAll();
        initAttribute();
    }

    private void initAttribute() {
        setSelected(null);

    }
    //

    public void generatePdf() throws JRException, IOException {
        ejbFacade.generatePdf();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public EquipementController() {
    }

    public Equipement getSelected() {
        if (selected == null) {
            selected = new Equipement();
        }
        return selected;
    }

    public void setSelected(Equipement selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EquipementFacade getFacade() {
        return ejbFacade;
    }

    public Equipement prepareCreate() {
        selected = new Equipement();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EquipementCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EquipementUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EquipementDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Equipement> getItems() {
        if (items == null) {
            items = new ArrayList();
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

    public Equipement getEquipement(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Equipement> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Equipement> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Equipement.class)
    public static class EquipementControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EquipementController controller = (EquipementController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipementController");
            return controller.getEquipement(getKey(value));
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
            if (object instanceof Equipement) {
                Equipement o = (Equipement) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Equipement.class.getName()});
                return null;
            }
        }

    }

}
