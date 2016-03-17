package com.bbva.paas.gdd.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbva.paas.gdd.domain.Heartbeat;

@RestController
public class HeartbeatController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
    
	@RequestMapping(value = "/heartbeat")
	public Heartbeat greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Heartbeat(counter.incrementAndGet(), String.format(template, name));
	}
}