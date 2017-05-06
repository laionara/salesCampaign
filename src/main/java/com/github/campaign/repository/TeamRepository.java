package com.github.campaign.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.github.campaign.model.Team;

@Repository
public class TeamRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	public Team findById(Long id) {
		try {
			Query q = manager.createQuery("SELECT p FROM " + Team.class.getName() + " p where p.id = :id");
			q.setParameter("id", id);
			Team team = (Team) q.getSingleResult();
			return team;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Team save(Team team){
		manager.persist(team);
		return team;
	}

}
