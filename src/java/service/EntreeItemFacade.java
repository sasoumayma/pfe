/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.EntreeItem;
import bean.Entree;
import bean.EntreeItem;
import controller.util.PdfUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author User
 */
@Stateless
public class EntreeItemFacade extends AbstractFacade<EntreeItem> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;
    
   
    public void generatePdf() throws JRException, IOException{
        Map<String,Object> params = new HashMap();
        params.put("responsable", "Mme HAGCHI Faiza");
        PdfUtil.generatePdf(findAll(), params, "entreeItem", "/jasper/entreeItem.jasper");
    }

     public void save(Entree entree, List<EntreeItem> entreeItems) {
        for (EntreeItem entreeItem : entreeItems) {
            entreeItem.setEntree(entree);
            create(entreeItem);
        }
    }

    public List<EntreeItem> findByEntree(Entree entree) {
        return em.createQuery("SELECT item FROM EntreeItem item WHERE item.entree.id='" + entree.getId() + "'").getResultList();
    }

    public void removeByEntree(Entree entree) {
        em.createQuery("DELETE FROM  EntreeItem item WHERE item.entree.id='" + entree.getId() + "'").executeUpdate();
    }

    public void add(EntreeItem entreeItem, List<EntreeItem> entreeItems) {
        entreeItems.add(clone(entreeItem));
    }

    public EntreeItem clone(EntreeItem entreeItem) {
        EntreeItem myCLone = new EntreeItem();
        myCLone.setEquipement(entreeItem.getEquipement());
        myCLone.setMagasin(entreeItem.getMagasin());
        myCLone.setNumeroSerie(entreeItem.getNumeroSerie());
        myCLone.setQuantite(entreeItem.getQuantite());
        myCLone.setQuantiteMinimale(entreeItem.getQuantiteMinimale());
        myCLone.setObservation(entreeItem.getObservation());
        return myCLone;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntreeItemFacade() {
        super(EntreeItem.class);
    }
    
}
