package survey.util;

import java.util.Map;

public class Util {

	public static boolean isContains(Map<String, String[]> paramsMap ,String questionId,String optionId){
		
		if(paramsMap != null && paramsMap.size() > 0){
			String [] values =  paramsMap.get(questionId);
			if (values != null && values.length > 0) {
				if (isContainsValue(values, optionId)) {
					return true;
				} 
			}
		}
		return false;
	}
	
	
	public static boolean isContainsValue (String[] values ,String optionId){
		for (String string : values) {
			if (string.equals(optionId)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getText(Map<String, String[]> paramsMap ,String questionId){
		if(paramsMap != null && paramsMap.size() > 0){
			String[] values = paramsMap.get(questionId);
			if (values != null && values.length > 0) {
				return values[values.length - 1] ;
			}
		}
		
		return "";
	}
}
