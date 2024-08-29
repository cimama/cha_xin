package cn.cha_xin_center.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
 
/**
 * 统一社会信用代码有效性校验
 *
 */
public class TyshxydmVerificationUtil {
	public final static String ISTYSHXYDM = "true";
	public final static String ERROR_TYSHXYDM = "统一社会信用代码有误，请核对后再输入！";
	public final static String ERROR_TYSHXYDM_MIN = "统一社会信用代码不足18位，请核对后再输入！";
	public final static String ERROR_TYSHXYDM_MAX = "统一社会信用代码大于18位，请核对后再输入！";
	public final static String ERROR_TYSHXYDM_EMPTY ="统一社会信用代码不能为空！";
	private static Map<String,Integer> datas = null;
	static int[] power = {1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28};
	// 社会统一信用代码不含（I、O、S、V、Z） 等字母
	static char[] code = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','T','U','W','X','Y'};
	static {
		datas = new HashMap<>();
		for(int i=0;i<code.length;i++){
			datas.put(code[i]+"",i);
		}
	}
	
	/**
	 * 判断是否是一个有效的统一社会信用代码
	 * @param tyshxydm
	 * @return
	 */
	public static String isTyshxydm(String tyshxydm){
		if(StringUtils.isEmpty(tyshxydm) || StringUtils.isEmpty(tyshxydm.trim())){
			return ERROR_TYSHXYDM_EMPTY;
		}else if(tyshxydm.trim().length()<18){
			return ERROR_TYSHXYDM_MIN;
		}else if(tyshxydm.trim().length()>18){
			return ERROR_TYSHXYDM_MAX;
		}else if(tyshxydm.equals("000000000000000000")){
			return ERROR_TYSHXYDM;
		}else if(tyshxydm.startsWith("2")){
			return ERROR_TYSHXYDM;
		}else{
			tyshxydm = tyshxydm.trim();
			String checkCharResult = checkChar(tyshxydm); // 校验字符合法性
			if (!StringUtils.isEmpty(checkCharResult)) {
				return checkCharResult;
			}
			int sum = sum(tyshxydm.substring(0,17).toCharArray());
			int temp = sum%31;
			temp = temp==0?31:temp;
			return tyshxydm.substring(17,18).equals(code[31-temp]+"")?ISTYSHXYDM:ERROR_TYSHXYDM;
		}
	}
 
	private static String checkChar(String tyshxydm){
		String result = StringUtils.EMPTY;
		char[] chars = tyshxydm.toCharArray();
		for(int i = 0; i < chars.length; i++){
			if (Character.isSpaceChar(chars[i]) || !datas.containsKey(chars[i]+"")) {
				result = ERROR_TYSHXYDM;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 加权计算
	 * @param chars
	 * @return
	 */
	private static int sum(char[] chars){
		int sum = 0;
		for(int i=0;i<chars.length;i++){
			int code = datas.get(chars[i]+"");
			sum += power[i]*code;
		}
		return sum;
 
	}
	

}