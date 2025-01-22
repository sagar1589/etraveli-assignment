package com.etraveli.notification.service;

import com.etraveli.notification.model.TemperaturEvent;

public interface NotificationService {

	public void notify(TemperaturEvent event);
}
