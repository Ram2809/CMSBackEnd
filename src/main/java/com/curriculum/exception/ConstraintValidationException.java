package com.curriculum.exception;

public class ConstraintValidationException extends NotFoundException{
	public ConstraintValidationException(String message)
	{
		super(message);
	}
}
