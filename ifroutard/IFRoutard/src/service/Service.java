/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.ClientDaoJpa;
import dao.JpaUtil;
import model.Client;

/**
 *
 * @author B3229
 */
public class Service {
    private ClientDao clientDao = new ClientDaoJpa();
    
    public void test(Client client)
    {
        JpaUtil.creerEntityManager();
        try {
            JpaUtil.ouvrirTransaction();
            clientDao.createClient(client);
            JpaUtil.validerTransaction();
            System.out.println("Client " + client.getNom() + " créé avec succès");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally
        {
            JpaUtil.fermerEntityManager();
        }
    }
    
}
