/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Sortie;
import bean.SortieItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class SortieItemFacade extends AbstractFacade<SortieItem> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    public void save(Sortie sortie, List<SortieItem> sortieItems) {
        for (SortieItem sortieItem : sortieItems) {
            sortieItem.setSortie(sortie);
            create(sortieItem);
        }
    }

    public List<SortieItem> findBySortie(Sortie sortie) {
        return em.createQuery("SELECT item FROM SortieItem item WHERE item.sortie.id='" + sortie.getId() + "'").getResultList();
    }

    public void removeBySortie(Sortie sortie) {
        em.createQuery("DELETE FROM  SortieItem item WHERE item.sortie.id='" + sortie.getId() + "'").executeUpdate();
    }

    public void add(SortieItem sortieItem, List<SortieItem> sortieItems) {
        sortieItems.add(clone(sortieItem));
    }

    public SortieItem clone(SortieItem sortieItem) {
        SortieItem myCLone = new SortieItem();
        myCLone.setEquipement(sortieItem.getEquipement());
        myCLone.setMagasin(sortieItem.getMagasin());
        myCLone.setSalle(sortieItem.getSalle());
        myCLone.setNumeroSerie(sortieItem.getNumeroSerie());
        myCLone.setQuantite(sortieItem.getQuantite());
        myCLone.setQuantiteMinimale(sortieItem.getQuantiteMinimale());
        myCLone.setObservation(sortieItem.getObservation());
        return myCLone;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SortieItemFacade() {
        super(SortieItem.class);
    }

}
