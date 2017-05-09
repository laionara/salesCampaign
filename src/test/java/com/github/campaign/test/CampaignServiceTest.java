package com.github.campaign.test;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
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
	
	@Mock
	private CampaignRepository campaignDAO;
	
	@Mock
	private	Campaign campaing;
	
	@Mock
	private	CampaignRequest request;
	
	@InjectMocks
	private	TeamService teamService;
	
	@Mock
	private CampaignResponse response;
	

	@Before
	public void setUp(){
		this.campaignService = Mockito.spy(CampaignService.class);
		this.campaignDAO = Mockito.spy(CampaignRepository.class);
		campaignDAO.manager = Mockito.mock(EntityManager.class);
		
	}
	@Test
	public void testValidityWithoutDate(){
		//Arrange
		List<Campaign> campaignList = campaignService.verifyValidity(null, null);
		verify(campaignDAO, Mockito.never()).findByValidity(any(Date.class), any(Date.class));
		Assert.assertTrue(campaignList.isEmpty());
		
	}
	
	@Test
	public void testFindByIdNull(){
		//Arrange
		
		//Act
		long id = 10;
		CampaignResponse response = campaignService.findById(id);
		when(this.campaignDAO.findByCampaign(id)).thenReturn(null);
		
		//test
		Assert.assertEquals(response.getMessage(), "Campanha não encontrada");
		
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