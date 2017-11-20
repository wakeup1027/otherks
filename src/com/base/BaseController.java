package com.base;

import com.config.DateUtils;
import com.jfinal.core.Controller;

public class BaseController extends Controller{
	
	public void renderAuto(String view) {
		String path = getAutoPath(view);
		super.render(path);
	}
	
	protected String getAutoPath(String view) {
		if(getSessionAttr("kuaisan_is_moile")){
			view = "/mobile"+view;
		}else{
			view = "/computer"+view;
		}
		return view;
	}
	
	/**
	 * ʱ�䴦��
	 * @return
	 */
	protected static String getNow() {
		return DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
	}
	
	/**
	 * ��ȡ��ǰ��������YYYYMMDD
	 * @return
	 */
	protected String getYearMd() {
		return DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD);
	}
	
	protected static String getYear() {
		return DateUtils.getNow(DateUtils.YEARONLY);
	}
	
}
