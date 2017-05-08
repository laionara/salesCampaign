package com.github.campaign.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.campaign.model.Campaign;
import com.github.campaign.model.CampaignRequest;
import com.github.campaign.model.CampaignResponse;
import com.github.campaign.model.Team;
import com.github.campaign.repository.CampaignRepository;

@Service
public class CampaignService {

	@Autowired
	private CampaignRepository campaignDAO = new CampaignRepository();
	
	@Autowired
	private TeamService teamService = new TeamService();
	
	private CampaignResponse response = new CampaignResponse();
	
	/**buscar campanha por id
	 * 
	 */
	public CampaignResponse findById(long id) {
		Campaign campaign = campaignDAO.findByCampaign(id);
		if(campaign != null){
			this.response.setNameCampaign(campaign.getName());
		}else{
			this.response.setMessage("Campanha não encontrada");
		}
		return response;
	}
	
	/**
	 * Antes de incluir uma nova campanha, é verificado se a data de vigência dessa campanha coincide com as datas das outras já cadastradas
	 * e se existe um time pré-cadastrado para associar a campanha
	 */
	public CampaignResponse includeCampaign(CampaignRequest campaignRequest){
		List<Campaign> campaignList;
		campaignList = this.verifyValidity(campaignRequest.getStartDate(), campaignRequest.getEndDate());
		response.setChangedCampaignList(campaignList);
		Campaign campaign = new Campaign();
		campaign.setName(campaignRequest.getName());
		campaign.setStartDate(campaignRequest.getStartDate());
		campaign.setEndDate(campaignRequest.getEndDate());
		
		Team team = this.teamService.findById(campaignRequest.getTeamId());
		if(team!=null){
			campaign.setTeam(team);
			campaign = this.campaignDAO.save(campaign);
			response.setMessage("Campanha adicionada com sucesso");
			response = this.formatReturn(campaign, response);
		}
		else{
			response.setMessage("Time não encontrado");
		}
		
		return response;

	}
	
	/**
	 * Verifica se a data de vigência da nova campanha coincide com as datas das outras já cadastradas
	 * Caso as datas coincidam, essas campanhas são armazenadas em uma lista e enviadas como parametro para o método alterValidityCampaign()
	 * que irá acrescentar um dia a mais ao fim da vigência de cada campanha
	 */
	public List<Campaign> verifyValidity(Date startDate, Date endDate) {
		List<Campaign> campaignList = new ArrayList<Campaign>();
		if(startDate != null && endDate!=null){
			campaignList = this.campaignDAO.findByValidity(startDate, endDate);
			if(!campaignList.isEmpty()){
				this.alterValidityCampaign(campaignList);
			}
		}
		return campaignList;
	}
	
	//TODO 
	public void verifyAllCampaign(List<Campaign> campaignList) {
		
	}
	
	/**
	 *  alterar a validade de campanhas que estejam com datas iguais
	 * 
	 */
	public void alterValidityCampaign(List<Campaign> campaignList) {
		for(Campaign campaign: campaignList){
			Calendar cal = Calendar.getInstance();
			cal.setTime(campaign.getEndDate()); // Objeto Date() do usuário
			cal.add(cal.DAY_OF_MONTH, +1);
			campaign.setEndDate(cal.getTime());
			this.campaignDAO.update(campaign);
		}
		
	}

	/**formatar a resposta para o usuário
	 * 
	 */
	public CampaignResponse formatReturn(Campaign campaign, CampaignResponse response2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = dateFormat.format(campaign.getStartDate());
		String endDate = dateFormat.format(campaign.getEndDate());
		this.response.setNameCampaign(campaign.getName());
		this.response.setValidity( startDate + " a " + endDate);
		return response;
	}

	/** excluir campanha
	 * 
	 */
	public CampaignResponse delete(Long id) {
		CampaignResponse campaignResponse = new CampaignResponse();
		Campaign campaing = this.campaignDAO.findByCampaign(id);
		if(campaing!=null){
			this.campaignDAO.delete(campaing);
			campaignResponse.setMessage("Campanha deletada com sucesso");
		}else{
			campaignResponse.setMessage("Campanha não encontrada");
		}
		return campaignResponse;
	}

	
	/** atualizar campanha
	 * 
	 */
	public CampaignResponse update(CampaignRequest campaignRequest, long id) {
		CampaignResponse campaignResponse = new CampaignResponse();
		Campaign campaing = this.campaignDAO.findByCampaign(id);
		if(campaing!= null){
			campaing.setName(campaignRequest.getName());
			campaing.setStartDate(campaignRequest.getStartDate());
			campaing.setEndDate(campaignRequest.getEndDate());
			Team team = this.teamService.findById(campaignRequest.getTeamId());
			if(team != null){
				campaing.setTeam(team);
			}
			this.campaignDAO.update(campaing);
			campaignResponse.setMessage("Campanha alterada com sucesso");
		}
		return campaignResponse;
	}

	

	
	
}
