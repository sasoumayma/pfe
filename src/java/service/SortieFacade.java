/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Sortie;
import bean.SortieItem;
import controller.util.SearchUtil;
import java.util.ArrayList;
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
public class SortieFacade extends AbstractFacade<Sortie> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    @EJB
    SortieItemFacade sortieItemFacade;

//    public List<Long> findByCriteria(int annee, Employee responsable) {
//        List<Long> res = new ArrayList();
//        for (int i = 1; i <= 12; i++) {
//            res.add(findByCriteria(i, annee, responsable));
//        }
//        return res;
//    }
//    public Long findByCriteria(int mois, int annee, Employee responsable) {
//        String moisConversion = mois + "";
//        if (mois < 10) {
//            moisConversion = "0" + mois;
//        }
//        String query = "SELECT COUNT(item.id) FROM Sortie item WHERE item.dateDepart LIKE '" + annee + "-" + moisConversion + "-%'";
//        if (responsable != null) {
//            query += SearchUtil.addConstraint("item", "responsable.id", "=", responsable.getId());
//        }
//        System.out.println(query);
//        List<Long> res = em.createQuery(query).getResultList();
//        if (res == null || res.isEmpty() || res.get(0) == null) {
//            return 0L;
//        }
//        return res.get(0);
//    }
    @Override
    public void create(Sortie sortie) {
        sortie.setId(generateId("Sortie", "id"));
        super.create(sortie);
    }

    public void save(Sortie sortie, List<SortieItem> sortieItems) {
        create(sortie);
        sortieItemFacade.save(sortie, sortieItems);
    }

    @Override
    public void remove(Sortie sortie) {
        sortieItemFacade.removeBySortie(sortie);
        super.remove(sortie);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SortieFacade() {
        super(Sortie.class);
    }

}
