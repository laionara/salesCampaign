package com.github.campaign.test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.github.campaign.model.Campaign;
import com.github.campaign.model.CampaignRequest;
import com.github.campaign.model.CampaignResponse;
import com.github.campaign.model.Team;
import com.github.campaign.repository.CampaignRepository;
import com.github.campaign.service.CampaignService;
import com.github.campaign.service.TeamService;
import com.google.common.base.Verify;


public class CampaignServiceTest {
	
	@Mock
	private CampaignService campaignService;
	
	@InjectMocks
	private CampaignRepository campaignDAO;
	
	@Mock
	private	Campaign campaing;
	
	@Mock
	private	CampaignRequest request;
	
	@InjectMocks
	private	TeamService teamService;
	
	@Mock
	private CampaignResponse response;
	
	private Query query;

	@Before
	public void setUp(){
		this.campaignService = new CampaignService();
		this.campaignDAO = new CampaignRepository();
		campaignDAO.manager = Mockito.mock(EntityManager.class);
		
	}
	
	@Test
	public void testFindByIdNull(){
		//Arrange
		this.response = new CampaignResponse();
		//Act
		when(campaignDAO.manager.createQuery("queryName")).thenReturn(query);
		this.campaignDAO.findByCampaign(any(long.class));
		//test
		assertEquals("Campanha não encontrada", this.campaing, null);
	}
	
	@Test
	public void testFindByIdWithCampaign(){
		//Arrange
		long id=18;
		//Act
		when(this.campaignDAO.findByCampaign(id)).thenReturn(this.campaing);
		//test
		Verify.verifyNotNull(this.campaignDAO.findByCampaign(id));
		
	}

	@Test
	public void testIncludeCampaignWithoutTeam(){
		verify(campaignService).verifyValidity(any(Date.class), any(Date.class));
		
	}
	
	@Test
	public void testIncludeCampaignWithTeam(){
		Team team = new Team();
		doReturn(team).when(teamService).findById(1);
		
	}
	
	
	
	
	
	
}