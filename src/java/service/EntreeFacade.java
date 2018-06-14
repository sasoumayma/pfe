/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Entree;
import bean.Entree;
import bean.EntreeItem;
import controller.util.PdfUtil;
import controller.util.SearchUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author User
 */
@Stateless
public class EntreeFacade extends AbstractFacade<Entree> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    //statistique
 public List<Long> findByCriteria(int annee, int quantite) {
        List<Long> res = new ArrayList();
        for (int i = 1; i <= 12; i++) {
            res.add(findByCriteria(i, annee, quantite));
        }
        return res;
    }    
 public Long findByCriteria(int mois, int annee, int quantite) {
        String moisConversion = mois + "";
        if (mois < 10) {
            moisConversion = "0" + mois;
        }
        String query = "SELECT COUNT(item.id) FROM Entree item WHERE item.dateEntree LIKE '" + annee + "-" + moisConversion + "-%'";
        query += SearchUtil.addConstraint("item", "quantite", "=", quantite);
        System.out.println(query);
        List<Long> res = em.createQuery(query).getResultList();
        if (res == null || res.isEmpty() || res.get(0) == null) {
            return 0L;
        }
        return res.get(0);
    }
 
    //Fin statistique
    
    @EJB
    EntreeItemFacade entreeItemFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     @Override
    public void create(Entree entree) {
        entree.setId(generateId("Entree", "id"));
        super.create(entree);
    }

    public void save(Entree entree, List<EntreeItem> entreeItems) {
        create(entree);
        entreeItemFacade.save(entree, entreeItems);
    }

    @Override
    public void remove(Entree entree) {
        entreeItemFacade.removeByEntree(entree);
        super.remove(entree);
    }


    public EntreeFacade() {
        super(Entree.class);
    }
    
}
