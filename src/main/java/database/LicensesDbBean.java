package database;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = LicensesDbBean.BEAN_NAME)
public class LicensesDbBean {

    public final static String BEAN_NAME = "LicensesDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;


}
