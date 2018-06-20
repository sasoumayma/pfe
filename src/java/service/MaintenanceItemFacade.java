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
public class MaintenanceItemFacade extends AbstractFacade<MaintenanceItem> {

    //
    public void removeByMaintenance(Maintenance maintenance) {
        em.createQuery("DELETE FROM  MaintenanceItem item WHERE item.maintenance.id='" + maintenance.getId() + "'").executeUpdate();
    }
  

    public void add(MaintenanceItem maintenanceItem, List<MaintenanceItem> maintenanceItems) {
        maintenanceItems.add(clone(maintenanceItem));
    }

    public MaintenanceItem clone(MaintenanceItem maintenanceItem) {
        MaintenanceItem myCLone = new MaintenanceItem();
        myCLone.setDateMaintenance(maintenanceItem.getDateMaintenance());
        myCLone.setEquipement(maintenanceItem.getEquipement());
        myCLone.setSalle(maintenanceItem.getSalle());
        
        return myCLone;
    }
    //

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaintenanceItemFacade() {
        super(MaintenanceItem.class);
    }

}
