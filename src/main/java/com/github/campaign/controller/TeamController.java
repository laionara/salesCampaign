package com.github.campaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.campaign.model.Team;
import com.github.campaign.model.TeamResponse;
import com.github.campaign.service.TeamService;

@Controller
@Transactional
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	public TeamController(){
		
	}
	
	//cadastro
	@RequestMapping(value = "/include", method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	public @ResponseBody TeamResponse include(@RequestBody Team team) {
        TeamResponse response = new TeamResponse();
		response =  this.teamService.includeTeam(team);
		return response;
	}
	
}
