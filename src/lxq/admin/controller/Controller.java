package lxq.admin.controller;

import java.util.ArrayList;
import java.util.List;

import lxq.user.util.FormString;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.LotteryLog;
import com.bean.TaskTimerBean;
import com.bean.TimeNumOver;
import com.config.ControllerBind;
import com.jfinal.aop.Clear;

import demo.UserInterceptor;

@ControllerBind(controllerKey = "/home")
@Clear(UserInterceptor.class)
public class Controller extends BaseController{
	
	/*public void index(){
		//获取倒计时
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		if(taskt.getInt("status")==1){ //如果开奖器是开启的则获取数据库中的倒计时时间
			TimeNumOver tlong = TimeNumOver.dao.findById(1);
			int timeNum = tlong.getInt("number");
			setAttr("tlong",timeNum*1000);
		}else{
			setAttr("tlong",-1);
		}
		
		//获取开奖记录
		LotteryLog Llog = LotteryLog.dao.findFirst("SELECT * FROM lottery_log ORDER BY creantime DESC");
		FormString fstring = new FormString();
		if(null==Llog){
			Llog = new LotteryLog();
			Llog.put("firstNum", 1);
			Llog.put("secondNum", 1);
			Llog.put("threeNum", 1);
			setAttr("Llog",Llog);
		}else{
			Llog.put("firstNum", fstring.firstNum(Llog.getInt("Num")+""));
			Llog.put("secondNum", fstring.secondNum(Llog.getInt("Num")+""));
			Llog.put("threeNum", fstring.threeNum(Llog.getInt("Num")+""));
			setAttr("Llog",Llog);
		}
		//获取每日开奖的期数
		OpenNum ON = OpenNum.dao.findById(1);
		ON.put("nextTime",getYearMd());//+fstring.formNum(ON.getInt("openNum"),ON.getInt("nowNum"))
		setAttr("ON",ON);
		renderAuto("/home.html");
	}*/
	
	public void index(){
		//获取倒计时
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		if(taskt.getInt("status")==1){ //如果开奖器是开启的则获取数据库中的倒计时时间
			TimeNumOver tlong = TimeNumOver.dao.findById(1);
			int timeNum = tlong.getInt("number");
			setAttr("tlong",timeNum*1000);
		}else{
			setAttr("tlong",-1);
		}
	
		List<LotteryLog> Llog = new ArrayList<LotteryLog>();
		if(getPara("num")==null){
			//Llog = LotteryLog.dao.find("SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT 30");
			Llog = LotteryLog.dao.findCache("LotteryLog", "logdate30", "SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT 30");
			setAttr("numStr", 30);
		}else{
			Llog = LotteryLog.dao.findCache("LotteryLog", "logdate"+getParaToInt("num"), "SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT "+getParaToInt("num"));
			//List<LotteryLog> Llog1 = LotteryLog.dao.findByCache("LotteryLog", "logdate"+getParaToInt("num"), "SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT "+getParaToInt("num"))
			setAttr("numStr", getParaToInt("num"));
		}
		setAttr("dateList",Llog);
		render("/computer/index.html");
	}
	
	public void overres(){
		FormString fstring = new FormString();
		List<LotteryLog> Llog = LotteryLog.dao.find("SELECT * FROM lottery_log ORDER BY creantime DESC LIMIT 950");
		List<LotteryLog> chList = new ArrayList<LotteryLog>();
		for(LotteryLog chLlog : Llog){
			LotteryLog lolog = new LotteryLog();
			lolog.put("creantime", chLlog.getStr("creantime"));
			lolog.put("qiNum", chLlog.getStr("qiNum"));
			lolog.put("firstNum", fstring.firstNum(chLlog.getInt("Num")+""));
			lolog.put("secondNum", fstring.secondNum(chLlog.getInt("Num")+""));
			lolog.put("threeNum", fstring.threeNum(chLlog.getInt("Num")+""));
			lolog.put("bigorsam", fstring.bigorsam(chLlog.getInt("Num")+""));
			lolog.put("onlyAll", fstring.onlyAll(chLlog.getInt("Num")+""));
			lolog.put("allNum", fstring.allNum(chLlog.getInt("Num")+""));
			chList.add(lolog);
		}
		setAttr("dateList",chList);
		renderAuto("/overres.html");
	}
	
	public void findRes(){
		System.out.println(getPara("key"));
		FormString fstring = new FormString();
		List<LotteryLog> Llog = LotteryLog.dao.find("SELECT * FROM lottery_log WHERE qiNum LIKE '%"+getPara("key")+"%' ORDER BY creantime DESC LIMIT 57");
		List<LotteryLog> chList = new ArrayList<LotteryLog>();
		for(LotteryLog chLlog : Llog){
			LotteryLog lolog = new LotteryLog();
			lolog.put("qiNum", chLlog.getStr("qiNum"));
			lolog.put("firstNum", fstring.firstNum(chLlog.getInt("Num")+""));
			lolog.put("secondNum", fstring.secondNum(chLlog.getInt("Num")+""));
			lolog.put("threeNum", fstring.threeNum(chLlog.getInt("Num")+""));
			lolog.put("bigorsam", fstring.bigorsam(chLlog.getInt("Num")+""));
			lolog.put("onlyAll", fstring.onlyAll(chLlog.getInt("Num")+""));
			lolog.put("allNum", fstring.allNum(chLlog.getInt("Num")+""));
			chList.add(lolog);
		}
		setAttr("dateList",chList);
		render("/computer/findDate.html");
	}
	
	//=========用户登陆===============
	public void login(){
		setAttr("mes", "");
		render("/computer/login.html");
	}
	
	public void checlogin(){
		if(new FormString().userLogin(getPara("userName"), getPara("password"))){
			setSessionAttr("loginUser", getPara("userName"));
			setAttr("mes", "");
			redirect("/admin.html");
		}else{
			setAttr("mes", "用户名或者密码错误，请联系管理员获取授权！");
			render("/computer/login.html");
		}
	}
	
	//用户查找期数
	public void findqiNum(){
		JSONObject json = new JSONObject();
		LotteryLog lolog = LotteryLog.dao.findFirst("SELECT * FROM lottery_log WHERE qiNum = '"+getPara("qiNum")+"'");
		if(null==lolog){
			json.put("resdate", lolog);
			json.put("msg", 0);
		}else{
			json.put("resdate", lolog);
			json.put("msg", 1);
		}
		renderJson(json);
	}
	
	//同步后台时间
	/*public void getHeaTime(){
		JSONObject json = new JSONObject();
		TaskTimerBean tast = TaskTimerBean.dao.findById(1);
		if(tast.getInt("status")==0){
			json.put("val", -1);
		}else{
			TimeNumOver tlong = TimeNumOver.dao.findById(1);
			int timeNum = tlong.getInt("number");
			json.put("val", timeNum*1000);
		}
		renderJson(json.toJSONString());
	}*/
	
	public void loginout(){
		removeSessionAttr("loginUser");
		setAttr("mes", "");
		render("/computer/login.html");
	}
	
}
