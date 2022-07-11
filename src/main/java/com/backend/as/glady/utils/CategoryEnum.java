package com.backend.as.glady.utils;

import org.springframework.validation.annotation.Validated;

/**
 * @author asoilihi 
 * 
 * Differents categories of deposit (gift or meal)
 *
 */
@Validated
public enum CategoryEnum {

	GIFT, MEAL;

}
