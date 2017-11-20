package lxq.admin.controller;


import com.base.BaseController;
import com.config.ControllerBind;
import com.jfinal.aop.Before;

import demo.UserInterceptor;

/*@Before(UserInterceptor.class)*/
@ControllerBind(controllerKey = "/akjsdhkhasjd")
public class AdminController extends BaseController{
	private static final String PATH = "/adminTwo";
	
	public void index(){
		render(PATH+"/index.html");
	}
	
	
}
