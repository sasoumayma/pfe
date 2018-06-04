/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Entree;
import bean.Entree;
import bean.EntreeItem;
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
public class EntreeFacade extends AbstractFacade<Entree> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    
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
