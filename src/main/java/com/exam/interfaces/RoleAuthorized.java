/**
 * Time creation: Feb 10, 2023, 12:25:32 PM
 *
 * Pakage name: com.exam.interfaces
 */
package com.exam.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Tien Tran
 *
 * class RoleAuthorized
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAuthorized {
	String[] value() default "Giảng viên";
}