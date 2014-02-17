/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Client;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author B3229
 */
public class ClientDaoJpa implements ClientDao {


    @Override
    public void createClient(Client client) {
        JpaUtil.obtenirEntityManager().persist(client);
    }

    @Override
    public Client updateClient(Client client) {
        return (Client)JpaUtil.obtenirEntityManager().merge(client);
    }

    @Override
    public void deleteClient(Client client) {
        JpaUtil.obtenirEntityManager().remove(client);
    }

    @Override
    public Client findClientByNum(int numClient) {
        Query q = JpaUtil.obtenirEntityManager().createQuery("select c from Client c where c.numClient = :numClient");
        q.setParameter("numClient", numClient);
        return (Client)q.getSingleResult();
    }
    
}
