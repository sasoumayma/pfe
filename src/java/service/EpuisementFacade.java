/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Epuisement;
import controller.util.PdfUtil;
import controller.util.SearchUtil;
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
public class EpuisementFacade extends AbstractFacade<Epuisement> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    //search
    public List<Epuisement> findByCretaria(String numeroSerie, int quantite ){
        String qry = "SELECT e FROM Equipement e WHERE 1=1";
            qry += SearchUtil.addConstraint("e", "numeroSerie", "=", numeroSerie);
            qry += SearchUtil.addConstraint("e", "quantite", "=", quantite);
         //   qry += SearchUtil.addConstraint("e", "designation", "=", designation);
            System.out.println("qryqryqryqry "+qry);
        return em.createQuery(qry).getResultList();
    }
    //search
    
    
    @Override
    public void create(Epuisement epuisement) {
        epuisement.setId(generateId("Epuisement", "id"));
        super.create(epuisement);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void generatePdf() throws JRException, IOException{
        Map<String,Object> params = new HashMap();
        params.put("responsable", "Sas");
        PdfUtil.generatePdf(findAll(), params, "epuisement", "/jasper/epuisement.jasper");
    }

    public EpuisementFacade() {
        super(Epuisement.class);
    }
    
}
