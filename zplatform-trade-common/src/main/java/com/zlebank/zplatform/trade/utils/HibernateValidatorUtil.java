package com.zlebank.zplatform.trade.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * HibernateValidator
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午3:27:28
 * @since
 */
public class HibernateValidatorUtil{

	private static Validator validator;
    private static final Log logger = LogFactory.getLog(HibernateValidatorUtil.class);
    private static final String SPLIT = "：";
    private static final String FIELD_SPLIT = "|";
	public static<T> String validateBeans(T data){
	    StringBuffer errorMsg=new StringBuffer();
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			 validator = factory.getValidator();
			 Set<ConstraintViolation<T>> constraintViolations =
			         validator.validate(data);
			 for (ConstraintViolation<T> constraintViolation : constraintViolations) { 
				 errorMsg.append(constraintViolation.getPropertyPath());
				 errorMsg.append(SPLIT);
				 errorMsg.append(constraintViolation.getMessage());
				 errorMsg.append(FIELD_SPLIT);
			 }
		} catch (Exception e) {
		    logger.error("HibernateValidator throws error", e);
		} 
	     return errorMsg.length()>0?errorMsg.toString():null;
	}
}
