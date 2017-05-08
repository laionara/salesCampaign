package com.github.campaign.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.campaign.model.Campaign;
import com.github.campaign.model.CampaignRequest;
import com.github.campaign.model.CampaignResponse;
import com.github.campaign.service.CampaignService;


@Controller
@Transactional
@RequestMapping("/campaign")
public class CampaignController {
	
	@Autowired
	private CampaignService campaignService;
	
	/**
	 * buscar por id
	 */
	@RequestMapping(value = "/consult/{id}", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	public @ResponseBody CampaignResponse getCampaign(@PathVariable("id") long id) {
		return campaignService.findById(id);
		
	}
	
	/**
	 * incluir campanha
	 */
	@RequestMapping( method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	public @ResponseBody CampaignResponse include(@RequestBody CampaignRequest campaignRequest) {
		CampaignResponse response = new CampaignResponse();
		response = this.campaignService.includeCampaign(campaignRequest);
				
		return response;
	}
	
	/**
	 * O método delete
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
	public @ResponseBody CampaignResponse delete(@RequestParam Long id) {
		CampaignResponse response = new CampaignResponse();
		response = this.campaignService.delete(id);
		return response; 
	}
	
	/**
	 * O método update
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces={"application/json; charset=UTF-8"})
	public @ResponseBody CampaignResponse updateById(@RequestBody CampaignRequest campaignRequest, @PathVariable long id) {
		CampaignResponse response = new CampaignResponse();
		response = this.campaignService.update(campaignRequest, id);
		return response; 
	}
	
	
	
}
