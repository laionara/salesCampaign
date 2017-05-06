package com.github.campaign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.campaign.model.Campaign;
import com.github.campaign.model.MemberRequest;
import com.github.campaign.model.MemberResponse;
import com.github.campaign.model.TeamMember;
import com.github.campaign.repository.CampaignRepository;
import com.github.campaign.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberDAO = new MemberRepository();
	
	@Autowired
	private CampaignRepository campaignDAO = new CampaignRepository();

	// incluir socio por request
	public MemberResponse includeMember(MemberRequest memberRequest) {
		MemberResponse memberResponse = new MemberResponse();
		TeamMember member = memberDAO.findByEmail(memberRequest.getEmail());
		if(member != null){ 
			//buscar campanhas 
			List<Campaign> campaignList = this.campaignDAO.findByTeam(member.getTeam().getId());
			memberResponse = this.showCampaign(member);
		}
		return memberResponse;
	}

	//buscar campanhas para socio já cadastrado
	private MemberResponse showCampaign(TeamMember member) {
		MemberResponse memberResponse = new MemberResponse();
		if(member.getTeam()!= null){
			List<Campaign> campaignList = campaignDAO.findByTeam(member.getTeam().getId());
			if(campaignList.isEmpty() && campaignList != null){
				memberResponse.setMessage("Sócio Já cadastrado. Conheça as campanhas");
				memberResponse.setCampaignList(campaignList);
			}else{
				memberResponse.setMessage("Sócio Já cadastrado. Não existem campanhas para esse time");
			}
		}
		return memberResponse;
	}
	

}
