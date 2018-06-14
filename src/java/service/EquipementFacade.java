/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Equipement;
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
public class EquipementFacade extends AbstractFacade<Equipement> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;
    
     @EJB
    private EquipementFacade equipementFacade;
    @EJB
    private EntreeFacade entreeFacade;
    
    
    
    public void generatePdf() throws JRException, IOException{
        Map<String,Object> params = new HashMap();
        params.put("responsable", "Mme HAGCHI Faiza");
        PdfUtil.generatePdf(findAll(), params, "equipement", "/jasper/equipement.jasper");
    }

    //search
    public List<Equipement> findByCretaria(String designation, String type, String emplacement, String marque ){
        String qry = "SELECT e FROM Equipement e WHERE 1=1";
        if(!designation.equals("")){
            qry += SearchUtil.addConstraint("e", "designation", "=", designation);
        }
        if(!type.equals("")){
            qry += SearchUtil.addConstraint("e", "type", "=", type);
        }
        if(!emplacement.equals("")){
            qry += SearchUtil.addConstraint("e", "emplacement", "=", emplacement);
        }
       if(!marque.equals("")){
            qry += SearchUtil.addConstraint("e", "marque", "=", marque);
        }
        return em.createQuery(qry).getResultList();
    }
    //search
    
    
    //statistique//
    public Object[] findIncidentAndTraitementByCriteria(int annee, String designation, int quantite) {
        Object[] res = new Object[2];
        res[0] = equipementFacade.findByCriteria(annee, designation);
        res[1] = entreeFacade.findByCriteria(annee, quantite);
        return res;
    }
   
     public List<Long> findByCriteria(int annee, String designation) {
        List<Long> res = new ArrayList();
        for (int i = 1; i <= 12; i++) {
            res.add(findByCriteria(i, annee, designation));
        }
        return res;
    }
      public Long findByCriteria(int mois, int annee, String designation) {
        String moisConversion = mois + "";
        if (mois < 10) {
            moisConversion = "0" + mois;
        }
        String query = "SELECT COUNT(item.id) FROM Equipement item WHERE item.dateEquipement LIKE '" + annee + "-" + moisConversion + "-%'";
        //query += SearchUtil.addConstraint("item", "etat", "=", etat);
       
        if (designation != null) {
            query += SearchUtil.addConstraint("item", "designation", "=", designation);
        }
        System.out.println(query);
        List<Long> res = em.createQuery(query).getResultList();
        if (res == null || res.isEmpty() || res.get(0) == null) {
            return 0L;
        }
        return res.get(0);
    }
     
    //fin statistique//
    
    @Override
    public void create(Equipement equipement) {
        equipement.setId(generateId("Equipement", "id"));
        super.create(equipement);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipementFacade() {
        super(Equipement.class);
    }
    
}
