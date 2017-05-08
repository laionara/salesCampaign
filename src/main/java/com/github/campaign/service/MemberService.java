package com.github.campaign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.campaign.model.Campaign;
import com.github.campaign.model.MemberRequest;
import com.github.campaign.model.MemberResponse;
import com.github.campaign.model.Team;
import com.github.campaign.model.TeamMember;
import com.github.campaign.repository.CampaignRepository;
import com.github.campaign.repository.MemberRepository;
import com.github.campaign.repository.TeamRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberDAO = new MemberRepository();
	
	@Autowired
	private TeamRepository teamDAO = new TeamRepository();
	
	@Autowired
	private CampaignRepository campaignDAO = new CampaignRepository();

	// incluir socio por request
	public MemberResponse includeMember(MemberRequest memberRequest) {
		MemberResponse memberResponse = new MemberResponse();
		TeamMember member = memberDAO.findByEmail(memberRequest.getEmail());
		if(member != null){ 
			memberResponse.setMessage("Sócio já cadastrado");
		}else{
			member  = new TeamMember();
			member.setEmail(memberRequest.getEmail());
			member.setName(memberRequest.getName());
			member.setBirthday(memberRequest.getBirthday());
			Team team = teamDAO.findById(memberRequest.getTeamId());
			if(team != null){
				member.setTeam(team);
			}
			member = memberDAO.save(member);
			memberResponse.setMessage("Sócio cadastrado com sucesso");
			//buscar campanhas 
			List<Campaign> campaignList = this.campaignDAO.findValidsByTeam(memberRequest.getTeamId());
			if(campaignList != null){
				memberResponse.setCampaignList(campaignList);
			}else{
				List<Campaign> newCampaignList = this.campaignDAO.findValidsByTeam(memberRequest.getTeamId());
				memberResponse.setCampaignList(newCampaignList);
			}
			
		}
		return memberResponse;
	}

}
