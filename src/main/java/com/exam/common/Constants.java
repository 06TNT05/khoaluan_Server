/**
 * Time creation: Dec 1, 2022, 10:28:00 PM
 * 
 * Package name: com.exam.common
 */
package com.exam.common;

/**
 * @author Tien Tran
 *
 * class Constants
 */
public class Constants {

	// Status of object
	public static final Byte NOT_DELETED = 0;
	public static final Byte DELETED = 1;
	
	public static final String AVATARS_DIRECTORY = "/resources/imgs/avatars/";
	public static final String FILES_DIRECTORY = "/resources/files/";
	public static final String FILE_ATTACH_QUESTION_DIRECTORY = "/resources/imgs/question_attachs/";
	
	// symbol
	public static final String SYMBOL_HASH = "@";
	public static final String SYMBOL_DOUBLE_BACKSLASH = "\\\\";
	public static final String SYMBOL_DOUBLE_SLASH = "//";
	public static final String SYMBOL_CLOSING_BRACKETS = ")";
	public static final String SYMBOL_UNDERSCORE = "_";
	public static final String SYMBOL_COMMA = ",";
	public static final String SYMBOL_COLON = ":";
	public static final String SYMBOL_OPENING_SQUARE_BRACKETS = "[";
	public static final String SYMBOL_CLOSING_SQUARE_BRACKETS = "]";
	public static final String SYMBOL_PERCENT = "%";
	public static final String SYMBOL_VERTICAL_LINE = "\\|";
	public static final String SYMBOL_DASH = "\\-";
	public static final String SYMBOL_SPACE = " ";
	
	public static final String STRING_EMPTY = "";
	
	public static final Byte SHEET_0 = 0;
	
	public static final long MAX_SIZE_IMAGE = 2097152;// 2MB
	
	public static final Byte ZERO = 0;
	public static final Byte MAX_RESULT = 5;
	public static final Byte BATCH_SIZE = 20;
	
	// message
	public static final String MESSAGE_LOGIN_FAILED = "Email hoặc mật khẩu không đúng";
	
	// token
	public static final String BEARER = "Bearer ";
	
	// questionType
	public static final String ID_MULTI_CHOICE_OPTION = "TN";
	public static final String ID_ESSAY = "TL";
	
	// option
	public static final String OPTION_A = "A";
	public static final String OPTION_B = "B";
	public static final String OPTION_C = "C";
	public static final String OPTION_D = "D";
	
	// role
	public static final String ROLE_ADMIN = "Admin";
}