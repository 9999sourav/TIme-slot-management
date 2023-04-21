package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingObjectDTO;
import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingSettingsDTO;
import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingsListResponseDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Key;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class VideoChatService implements IVideoChatService {

	String zoomUserId = "me";
	String yourEmail = "taimusurotto@gmail.com";
//	String yourPass = "Pro@123";
	
	String zoomApiSecret = "KawXI3AHsTAOl2YCNRvs664w8FLqKENNBfKz";
	String zoomApiKey = "FfkuPYETQYG8_4Sj8Q9f1w";

	public ZoomMeetingObjectDTO createMeeting(ZoomMeetingObjectDTO zoomMeetingObjectDTO) {

		log.debug("Request to create a Zoom meeting");
		// replace zoomUserId with your user ID
		String apiUrl = "https://api.zoom.us/v2/users/" + zoomUserId + "/meetings";

		// replace with your password or method
//		zoomMeetingObjectDTO.setPassword(yourPass);
		// replace email with your email
		zoomMeetingObjectDTO.setHost_email(yourEmail);

		// Optional Settings for host and participant related options
		ZoomMeetingSettingsDTO settingsDTO = new ZoomMeetingSettingsDTO();
		settingsDTO.setJoin_before_host(false);
		settingsDTO.setParticipant_video(true);
		settingsDTO.setHost_video(false);
		settingsDTO.setAuto_recording("cloud");
		settingsDTO.setMute_upon_entry(true);
		zoomMeetingObjectDTO.setSettings(settingsDTO);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
		headers.add("content-type", "application/json");
		HttpEntity<ZoomMeetingObjectDTO> httpEntity = new HttpEntity<ZoomMeetingObjectDTO>(zoomMeetingObjectDTO,
				headers);
		ResponseEntity<ZoomMeetingObjectDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
				ZoomMeetingObjectDTO.class);
		if (zEntity.getStatusCodeValue() == 201) {
			log.debug("Zooom meeeting response {}", zEntity);
			return zEntity.getBody();
		} else {
			log.debug("Error while creating zoom meeting {}", zEntity.getStatusCode());
		}
		return zoomMeetingObjectDTO;
	}

	/**
	 * Generate JWT token for Zoom using api credentials
	 * 
	 * @return JWT Token String
	 */
	public String generateZoomJWTTOken() {
		String id = UUID.randomUUID().toString().replace("-", "");
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date creation = new Date(System.currentTimeMillis());
		Date tokenExpiry = new Date(System.currentTimeMillis() + (1000 * 60));

		Key key = Keys.hmacShaKeyFor(zoomApiSecret.getBytes());
		return Jwts.builder().setId(id).setIssuer(zoomApiKey).setIssuedAt(creation).setSubject("")
				.setExpiration(tokenExpiry).signWith(signatureAlgorithm ,key).compact();
	}

	/**
	 * Request to list all meetings by userId/email of the user
	 * 
	 * @param userIdOrEmail optional userId/email value
	 * 
	 * @param meetingType   scheduled/live/upcoming
	 * 
	 * @return zoomMeetingsListResponseDTO the dto containing list of meetings
	 */
	@Override
	public ZoomMeetingsListResponseDTO listMeetings(Optional<String> userIdOrEmail, Optional<String> meetingType) {
		log.debug("Request to list all Zoom meetings by User id or email {}", userIdOrEmail);
		// replace me with user id in case, listing meetings for a different user than
		// admin
		String listMeetingUrl = "https://api.zoom.us/v2/users/me/meetings";
		// replace either user Id or email here with your user id/email
		if (userIdOrEmail.isPresent()) {
			listMeetingUrl = "https://api.zoom.us/v2/users/" + userIdOrEmail.get() + "/meetings";
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
		headers.add("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(listMeetingUrl);
		if (meetingType.isPresent()) {
			urlBuilder.queryParam("type", meetingType.get());
		}
		ResponseEntity<ZoomMeetingsListResponseDTO> response = restTemplate.exchange(urlBuilder.toUriString(),
				HttpMethod.GET, requestEntity, ZoomMeetingsListResponseDTO.class);
		if (response.getStatusCodeValue() == 200) {
			return response.getBody();
		} else if (response.getStatusCodeValue() == 404) {
			// throw new Exception("User id or email not found for supplied value");
			log.error("User id or email not found for supplied value");
		}
		return null;
	}

	/**
	 * Get ZoomMeeting by Meeting id
	 * 
	 * @param meetingId the id of meetting from Zoom
	 * @return the meetingObjectDTO with meeting details
	 */
	@Override
	public ZoomMeetingObjectDTO getZoomMeetingById(String meetingId) {
		log.debug("Request to get single meeting by id {}", meetingId);
		String getMeetingUrl = "https://api.zoom.us/v2/meetings/" + meetingId;
		log.debug("Meeting Url {}", getMeetingUrl);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
		headers.add("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ZoomMeetingObjectDTO> zoomEntityRes = restTemplate.exchange(getMeetingUrl, HttpMethod.GET,
				requestEntity, ZoomMeetingObjectDTO.class);
		if (zoomEntityRes.getStatusCodeValue() == 200) {
			return zoomEntityRes.getBody();
		} else if (zoomEntityRes.getStatusCodeValue() == 404) {
			// throw new InternalServerException("User id or email not found for supplied
			// value");

			log.error("User id or email not found for supplied value");
		}
		return null;
	}

}
