package com.example.web.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.domain.ReCaptchaResponse;
import com.example.web.domain.SignUpForm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SignupController {
	
	@RequestMapping(value="/signup.do")
	public String signup(ModelMap model) {
		return "/login/signup.jsp";	
	}
	
	@RequestMapping(value="/signupProc.do")
	public String signupProc(ModelMap model,HttpServletRequest request
			,@ModelAttribute("form") SignUpForm form) {
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("secret", "6LdolBsTAAAAANu_lMRjwmgXnNA2sHihCkx3yCwW"));
		formParams.add(new BasicNameValuePair("response", gRecaptchaResponse));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
		httpPost.setEntity(entity); 
		
		ResponseHandler<String> responseHandlerRaw = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		
		String returnUrl = "redirect:/signup.do";
        CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		try {
			responseBody = httpclient.execute(httpPost, responseHandlerRaw);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ReCaptchaResponse reCaptchaResponse = mapper.readValue(responseBody, ReCaptchaResponse.class);
			if (reCaptchaResponse != null && "true".equals(reCaptchaResponse.getSuccess())) {
				returnUrl = "redirect:/login.do";
				// TODO : user info add into database
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnUrl;	
	}
}
