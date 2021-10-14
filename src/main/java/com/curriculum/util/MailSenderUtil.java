package com.curriculum.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



public class MailSenderUtil {
	private static final Logger logger = LogManager.getLogger(MailSenderUtil.class.getName());

	private MailSenderUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final int NO_OF_QUICK_SERVICE_THREADS = 20;

	/**
	 * this statement create a thread pool of twenty threads here we are assigning
	 * send mail task using ScheduledExecutorService.submit();
	 */
	private static ScheduledExecutorService quickService = Executors
			.newScheduledThreadPool(NO_OF_QUICK_SERVICE_THREADS); // Creates a thread pool that reuses fixed number of
// threads(as specified by noOfThreads in this
// case).

	public static void sendMail(JavaMailSender javaMailSender, String toReceiver, String subject, String message)
			throws MailException {
		logger.trace("entering sendMail method");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(toReceiver);
		msg.setSubject(subject);
		msg.setText(message);
		quickService.submit(() -> javaMailSender.send(msg));

	}

}
