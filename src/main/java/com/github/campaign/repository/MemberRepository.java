package com.github.campaign.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.github.campaign.model.TeamMember;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager manager;

	public TeamMember findByEmail(String email) {
		try {
			Query q = manager.createQuery("SELECT t FROM TeamMember t where t.email = :email");
			q.setParameter("email", email);
			TeamMember teamMember = (TeamMember) q.getSingleResult();
			return teamMember;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
