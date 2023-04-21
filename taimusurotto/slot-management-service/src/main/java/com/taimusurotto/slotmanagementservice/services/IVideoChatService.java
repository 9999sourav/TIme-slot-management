package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingObjectDTO;
import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingsListResponseDTO;

import java.util.Optional;

public interface IVideoChatService {

	public ZoomMeetingsListResponseDTO listMeetings(Optional<String> userIdOrEmail, Optional<String> meetingType);

	public ZoomMeetingObjectDTO getZoomMeetingById(String meetingId);

	public ZoomMeetingObjectDTO createMeeting(ZoomMeetingObjectDTO zoomMeetingObjectDTO);

	public String generateZoomJWTTOken();
}
