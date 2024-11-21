package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.EventDao;
import com.ssafy.cafe.model.dto.Event;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	EventDao eDao;

	@Override
	public List<Event> getEvents() {
		return eDao.getEvents();
	}

}
