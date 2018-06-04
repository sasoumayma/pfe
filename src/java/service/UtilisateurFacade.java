/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Utilisateur;
import controller.util.HashageUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    @PersistenceContext(unitName = "gmaov3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }
    /*connecte Utilisateur*/
    @Override
    public void create(Utilisateur utilisateur) {
        utilisateur.setPassword(HashageUtil.sha256(utilisateur.getPassword()));
        super.create(utilisateur);
    }

    public int seConnnecter(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getId() == null) {
            return -5; // please type login
        } else {
            Utilisateur loadedUtilisateur = find(utilisateur.getId());
            if (loadedUtilisateur == null) {
                return -4; // makaynch had utilisateur
            } else if (!loadedUtilisateur.getPassword().equals((HashageUtil.sha256(utilisateur.getPassword())))) {
                if (loadedUtilisateur.getNbrCnx() < 3) {
                    loadedUtilisateur.setNbrCnx(loadedUtilisateur.getNbrCnx() + 1);
                } else if (loadedUtilisateur.getNbrCnx() >= 3) {
                    loadedUtilisateur.setBlocked(1);
                    // edit(loadedUtilisateur);
                }
                return -3; // Wrong Password
            } else if (loadedUtilisateur.getBlocked() == 1) {
                return -2; // this utilisateur is blocked
            } else {
//                loadedUtilisateur.setNbrCnx(0);
//                edit(loadedUtilisateur);
//                utilisateur = clone(loadedUtilisateur);
//                utilisateur.setMdpChanged(loadedUtilisateur.isMdpChanged());
//                edit(utilisateur);
                // SessionUtil.attachUtilisateurToCommune(utilisateur);
                return 1; // this utilisateur is exist
            }

        }
    }

    public Utilisateur clone(Utilisateur utilisateur) {
        Utilisateur clone = new Utilisateur();
        clone.setId(utilisateur.getId());
        clone.setBlocked(utilisateur.getBlocked());
        clone.setMdpChanged(utilisateur.isMdpChanged());
        clone.setNbrCnx(utilisateur.getNbrCnx());
        clone.setPassword(utilisateur.getPassword());
        return clone;
    }

    public void inscrir(Utilisateur selected) {
        Utilisateur utilisateur = clone(selected);
        utilisateur.setPassword(controller.util.HashageUtil.sha256(utilisateur.getPassword()));
        create(utilisateur);
    }
    /* Connecte Utilisateur*/
}

    

