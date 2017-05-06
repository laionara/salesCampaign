package com.github.campaign.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CampaignResponse {

	private String nameCampaign;
	private Long idTeam;
	private String validity;
	private String message;
	private List<Campaign> changedCampaignList;
	
	public CampaignResponse(){
		
	}

	public String getNameCampaign() {
		return nameCampaign;
	}

	public void setNameCampaign(String nameCampaign) {
		this.nameCampaign = nameCampaign;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(Long idTeam) {
		this.idTeam = idTeam;
	}

	public List<Campaign> getChangedCampaignList() {
		return changedCampaignList;
	}

	public void setChangedCampaignList(List<Campaign> changedCampaignList) {
		this.changedCampaignList = changedCampaignList;
	}
		
	
	
}
