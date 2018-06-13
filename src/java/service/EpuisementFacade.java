/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Epuisement;
import controller.util.PdfUtil;
import java.io.IOException;
import java.util.HashMap;
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
