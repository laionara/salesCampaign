package com.github.campaign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.campaign.model.Team;
import com.github.campaign.model.TeamResponse;
import com.github.campaign.repository.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamDAO = new TeamRepository();
	
	public Team findById(long id){
		return this.teamDAO.findById(id);
	}

	public Team includeTeam(String teamName){
		Team team = new Team();
		team.setName(teamName);
		return this.teamDAO.save(team);
	}

	public TeamResponse includeTeam(Team team) {
		TeamResponse response = new TeamResponse();
		team.setName(team.getName());
		team = this.teamDAO.save(team);
		response.setTeam(team);
		response.setMessage("Time incluido com sucesso");
		return response;
	}
}
