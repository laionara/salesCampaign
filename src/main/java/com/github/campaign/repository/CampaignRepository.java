package com.github.campaign.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.github.campaign.model.Campaign;


@Repository
public class CampaignRepository {

	@PersistenceContext
	private EntityManager manager;
	
	public Campaign findByCampaign(Long id) {
		try {
			Query q = manager.createQuery("SELECT p FROM " + Campaign.class.getName() + " p where p.id = :id");
			q.setParameter("id", id);
			Campaign campaign = (Campaign) q.getSingleResult();
			return campaign;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Campaign> findByCampaignInvalid() {
		Query q = manager.createQuery("SELECT c FROM " + Campaign.class.getName() + " c where c.end_date after " + new Date());
		return q.getResultList();
	}
	
	public List<Campaign> findByTeam(long idTeam) {
		Query q = manager.createQuery("SELECT p FROM " + Campaign.class.getName() + " p where p.team.id= :id");
		q.setParameter("id", idTeam);
		
		return q.getResultList();
	}
	
	public Campaign save(Campaign campaign){
		manager.persist(campaign);
		return campaign;
	}

	public List<Campaign> findByValidity(Date startDate, Date endDate) {
		Query q = manager.createQuery("SELECT c FROM Campaign c where c.startDate between :startDate and :endDate or "
				+ "c.endDate between :startDate and :endDate");
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		return q.getResultList();
	}
	
	public List<Campaign> verifyValidity() {
		Query q = manager.createQuery("SELECT c FROM Campaign c where c.endDate in (Select p.endDate FROM Campaign p )");
		return q.getResultList();
	}

	public Campaign update(Campaign campaign) {
		return manager.merge(campaign);
	
	}

	public void delete(Campaign campaign) {
		manager.remove(campaign);
		
	}
	

	
}
