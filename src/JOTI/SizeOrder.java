/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JOTI;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.persister.collection.QueryableCollection;
import org.hibernate.persister.entity.Loadable;
import org.hibernate.sql.ConditionFragment;

/**
 * Class to handle ordering a collection based upon the size of a sub-collection
 * @author Andrew Whitelaw
 */
public class SizeOrder extends Order {

    private String propertyName;
    private boolean ascending;

    /**
     * Constructor for SizeOrder
     * @param propertyName Strange representing the property name
     * @param ascending =true, descending = false
     */
    protected SizeOrder(String propertyName, boolean ascending) {
        super(propertyName, ascending);
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    /**
     * Creates an SQL string for use as a criteria
     * @param criteria Hibernate criteria
     * @param criteriaQuery CriteriaQuery objects
     * @return String value representing the query
     * @throws HibernateException general hibernate error
     */
    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        String role = criteriaQuery.getEntityName(criteria, propertyName) + '.' + criteriaQuery.getPropertyName(propertyName);
        QueryableCollection cp = (QueryableCollection) criteriaQuery.getFactory().getCollectionPersister(role);

        String[] fk = cp.getKeyColumnNames();
        String[] pk = ((Loadable) cp.getOwnerEntityPersister())
                .getIdentifierColumnNames();
        return " (select count(*) from " + cp.getTableName() + " where "
                + new ConditionFragment()
                        .setTableAlias(
                                criteriaQuery.getSQLAlias(criteria, propertyName)
                        ).setCondition(pk, fk)
                        .toFragmentString() + ") "
                + (ascending ? "asc" : "desc");
    }

    /**
     *  creates a SizeOrder object with ascending set
     * @param propertyName String representing the property name
     * @return SizeOrder object with ascending set
     */
    public static SizeOrder asc(String propertyName) {
        return new SizeOrder(propertyName, true);
    }

   /**
    * creates a SizeOrder object with descending set
    * @param propertyName String representing the property name
    * @return SizeOrder object with descending set
    */
    public static SizeOrder desc(String propertyName) {
        return new SizeOrder(propertyName, false);
    }
}
