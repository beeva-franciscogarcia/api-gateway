package com.bbva.paas.gdd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="services")
public class ServiceURIs {

	private String cards;
	private String accounts;
	private String transactions;
	
	public String getCards() {
		return cards;
	}
	
	public void setCards(String cards) {
		this.cards = cards;
	}
	
	public String getAccounts() {
		return accounts;
	}
	
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	
	public String getTransactions() {
		return transactions;
	}
	
	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}	
}
