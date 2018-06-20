/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Maintenance;
import bean.MaintenanceItem;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class MaintenanceFacade extends AbstractFacade<Maintenance> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    @EJB
    MaintenanceItemFacade maintenanceItemFacade;

    public void save(Maintenance maintenance, List<MaintenanceItem> maintenanceItems) {
        create(maintenance);
        for (MaintenanceItem maintenanceItem : maintenanceItems) {
            maintenanceItem.setMaintenance(maintenance);
            maintenanceItemFacade.create(maintenanceItem);
        }
    }

    @Override
    public void create(Maintenance maintenance) {
        maintenance.setId(generateId("Maintenance", "id"));
        super.create(maintenance);
    }

    @Override
    public void remove(Maintenance maintenance) {
        maintenanceItemFacade.removeByMaintenance(maintenance);
        super.remove(maintenance);
    }
    //

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaintenanceFacade() {
        super(Maintenance.class);
    }

}
