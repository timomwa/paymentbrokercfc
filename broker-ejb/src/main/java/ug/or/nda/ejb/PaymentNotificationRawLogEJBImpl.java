package ug.or.nda.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.InvoiceValidationRawLog;

@Stateless
public class PaymentNotificationRawLogEJBImpl implements PaymentNotificationRawLogEJBI {
	
private Logger logger = Logger.getLogger(getClass());
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	@Override
	public InvoiceValidationRawLog findLastRawLogByInvoiceNo(String invoiceNo) {
		InvoiceValidationRawLog rec = null;
		
		try{
			
			Query qry = em.createQuery("from PaymentNotificationRawLog pnrl WHERE pnrl.invoiceNo = :invoiceNo order by timeStamp desc");
			qry.setParameter("invoiceNo", invoiceNo);
			qry.setFirstResult(0);
			qry.setMaxResults(1);
			rec = (InvoiceValidationRawLog) qry.getSingleResult();
			
		}catch(NoResultException e){
			logger.warn("no payment notification");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return rec;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceValidationRawLog> listPaymentNotificationLogs(String invoiceNo) {
		
		List<InvoiceValidationRawLog> rec = null;
		try{
			
			Query qry = em.createQuery("from PaymentNotificationRawLog pnrl WHERE pnrl.invoiceNo = :invoiceNo order by timeStamp desc");
			qry.setParameter("invoiceNo", invoiceNo);
			rec =  qry.getResultList();
			
		}catch(NoResultException e){
			logger.warn("no payment notification");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return rec;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceValidationRawLog> query(QueryDTO queryDTO) {

		List<InvoiceValidationRawLog> payments = new ArrayList<InvoiceValidationRawLog>();
		
		try{
			
			String hql = "from PaymentNotificationRawLog WHERE id > 0";
			
			if(queryDTO.getQuery()!=null && !queryDTO.getQuery().isEmpty()){
				hql = hql + " AND ( lower(invoiceNo) like lower(:invQuery)  "
						+ "			OR "
						+ "        lower(systemID) like lower(:invQuery) ) ";
			}
			
			if(queryDTO.getDateFrom()!=null && queryDTO.getDateTo()!=null){
				hql = hql+" AND date(timeStamp) between date(:fromTime) and date(:toTime)";
			}else if(queryDTO.getDateFrom()!=null && queryDTO.getDateTo()==null){
				hql = hql+" AND date(timeStamp) >= date(:thisTS)";
				queryDTO.setDateTo(new Date());
			}else if(queryDTO.getDateTo()!=null && queryDTO.getDateFrom()==null){
				hql = hql+" AND date(timeStamp) <= date(:thisTS) ";
			}
			hql = hql + " ORDER BY timeStamp desc";
			
			Query preparedQuery = em.createQuery(hql);
			if(queryDTO.getQuery()!=null && !queryDTO.getQuery().isEmpty()){
				preparedQuery.setParameter("invQuery", "%"+queryDTO.getQuery()+"%");
			}
			
			if(queryDTO.getDateFrom()!=null && queryDTO.getDateTo()!=null){
				preparedQuery.setParameter("fromTime", queryDTO.getDateFrom());
				preparedQuery.setParameter("toTime", queryDTO.getDateTo());
			}else if(queryDTO.getDateFrom()!=null && queryDTO.getDateTo()==null){
				preparedQuery.setParameter("thisTS", queryDTO.getDateFrom());
			}else if(queryDTO.getDateTo()!=null && queryDTO.getDateFrom()==null){
				preparedQuery.setParameter("thisTS", queryDTO.getDateTo());
			} 
			
			if(queryDTO.getStart()!=null && queryDTO.getStart().compareTo(0)>0)
				preparedQuery.setMaxResults(queryDTO.getStart());
			if(queryDTO.getLimit()!=null && queryDTO.getLimit().compareTo(0)>0)
				preparedQuery.setMaxResults(queryDTO.getLimit());
			payments = preparedQuery.getResultList();
			
		}catch(NoResultException re){
		
			logger.warn("no pamyments");
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
		
		}
		
		return payments;
	}

}
