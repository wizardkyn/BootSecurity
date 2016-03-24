package com.example.web.domain;

import java.io.Serializable;

public class ReCaptchaResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String success;
	private String challenge_ts;
	private String hostname;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getChallenge_ts() {
		return challenge_ts;
	}
	public void setChallenge_ts(String challenge_ts) {
		this.challenge_ts = challenge_ts;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}
