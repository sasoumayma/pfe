/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Equipement;
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
public class EquipementFacade extends AbstractFacade<Equipement> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;
    
    public void generatePdf() throws JRException, IOException{
        Map<String,Object> params = new HashMap();
        params.put("responsable", "Mme HAGCHI Faiza");
        PdfUtil.generatePdf(findAll(), params, "equipement", "/jasper/equipement.jasper");
    }

    
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
