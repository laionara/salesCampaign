package com.github.campaign.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.campaign.controller.CampaignController;
import com.github.campaign.controller.TeamController;
import com.github.campaign.model.Campaign;
import com.github.campaign.model.CampaignRequest;
import com.github.campaign.model.CampaignResponse;
import com.github.campaign.model.Team;
import com.github.campaign.model.TeamMember;
import com.github.campaign.repository.CampaignRepository;
import com.github.campaign.repository.TeamRepository;
import com.github.campaign.service.CampaignService;
import com.github.campaign.service.TeamService;


@EnableWebMvc
@ComponentScan(basePackageClasses={Campaign.class, CampaignController.class,
		CampaignService.class, CampaignRepository.class,
		Team.class, CampaignRequest.class, CampaignResponse.class, 
		TeamRepository.class, TeamService.class, TeamController.class,
		TeamMember.class})
public class AppWebConfiguration {

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("/WEB-INF/messages.properties");
		bundle.setDefaultEncoding("UTF-8");
		bundle.setCacheSeconds(1);
		return bundle;
	}
}

