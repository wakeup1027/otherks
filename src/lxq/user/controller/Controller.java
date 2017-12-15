package lxq.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lxq.user.util.DateUtil;
import lxq.user.util.FormString;
import lxq.user.util.Tiemer;
import lxq.user.util.TiemerSecond;
import lxq.user.util.cache.CacheUntil;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.IsAutoStart;
import com.bean.Lottery;
import com.bean.LotteryLog;
import com.bean.OpenNum;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.bean.TimeNumOver;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

import demo.UserInterceptor;

@Before(UserInterceptor.class)
@ControllerBind(controllerKey = "/admin")
public class Controller extends BaseController {
	private static final String PATH = "/adminTwo";
	
	//=======主页========
	public void index(){
		render(PATH+"/index.html");
	}
	
	//=======设置时长=======
	public void LongTime(){
		JSONObject json = new JSONObject();
		TaskTimerBean tast = TaskTimerBean.dao.findById(1);
		if(tast.getInt("status")==1){
			json.put("state", "error");
			json.put("msg", "修改失败！请停止开奖器再修改开奖时间！");
			renderJson(json.toJSONString());
			return;
		}
		TimeLong tlong = TimeLong.dao.findById(4);
		tlong.set("timelong", getParaToInt("time"));
		boolean tl = tlong.update();
		if(tl){
			json.put("state", "success");
			json.put("msg", "修改成功！");
			json.put("val", getParaToInt("time"));
		}else{
			json.put("state", "error");
			json.put("msg", "修改失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	public void getNowTime(){
		TimeLong tlong = TimeLong.dao.findById(4);
		setAttr("tlong",tlong);
		render(PATH+"/timelong.html");
	}
	
	//===============未开奖的号码============================
	public void inpNum(){
		render(PATH+"/weikaiguo.html");
	}
	
	public void getweidate(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Lottery> orders = Lottery.dao.findByPage(getParaToInt("page"), getParaToInt("rows"),"creantime");
		Long total = Lottery.dao.count("SELECT * FROM lottery ORDER BY creantime DESC");
		map.put("rows", orders);
	    map.put("total", total); 
	    renderJson(map);
	}
	
	public void saveNum(){
		JSONObject json = new JSONObject();
		Lottery ltt = new Lottery();
		ltt.set("Num",Integer.parseInt(getPara("firstNum")+""+getPara("secondNum")+""+getPara("threeNum")));
		ltt.set("creantime",getNow());
		if(ltt.save()){
			json.put("msg", 1);
		}else{
			json.put("msg", 0);
		}
		renderJson(json.toJSONString());
	}
	
	public void delet(){
		JSONObject json = new JSONObject();
		String idStr = getPara("idStr");
		String[] chStr = idStr.split(",");
		for(String idch : chStr){
			Lottery ltt = new Lottery();
			ltt.set("id", idch);
			ltt.delete();
		}
		json.put("msg", 1);
		renderJson(json.toJSONString());
	}
	
	public void saveOldNum(){
		JSONObject json = new JSONObject();
		LotteryLog ltt = new LotteryLog();
		ltt.set("qiNum",getPara("qiNum"));
		ltt.set("Num",Integer.parseInt(getPara("firstNum")+""+getPara("secondNum")+""+getPara("threeNum")));
		ltt.set("creantime",getPara("creantime"));
		if(ltt.save()){
			json.put("msg", 1);
		}else{
			json.put("msg", 0);
		}
		renderJson(json.toJSONString());
	}
	
	public void delOldNum(){
		JSONObject json = new JSONObject();
		String idStr = getPara("idStr");
		String[] chStr = idStr.split(",");
		for(String idch : chStr){
			LotteryLog ltt = new LotteryLog();
			ltt.set("id", idch);
			ltt.delete();
		}
		json.put("msg", 1);
		renderJson(json.toJSONString());
	}
	
	public void delNum(){
		JSONObject json = new JSONObject();
		int numid = getParaToInt();
		Lottery ltt = Lottery.dao.findById(numid);
		ltt.delete();
		json.put("state", "success");
		json.put("msg", "号码删除成功！");
		renderJson(json.toJSONString());
	}
	
	//===============已开奖的号码===============
	public void getoverList(){
		render(PATH+"/yikaiguo.html");
	}
	
	public void getoverdate(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<LotteryLog> orders = LotteryLog.dao.findByPage(getParaToInt("page"), getParaToInt("rows"),"creantime");
		Long total = LotteryLog.dao.count("SELECT * FROM lottery_log ORDER BY creantime DESC");
		map.put("rows", orders);
	    map.put("total", total); 
	    renderJson(map);
	}
	
	public void searchDate(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<LotteryLog> orders = LotteryLog.dao.findByPage("WHERE qiNum LIKE '%"+getPara("qiNum")+"%'",getParaToInt("page"), getParaToInt("rows"),"creantime");
		Long total = LotteryLog.dao.count("SELECT * FROM lottery_log WHERE qiNum LIKE '%"+getPara("qiNum")+"%' ORDER BY creantime DESC");
		map.put("rows", orders);
	    map.put("total", total); 
	    renderJson(map);
	}
	
	//========启动开奖器========
	public void start(){
		//TimeLong tlong = TimeLong.dao.findById(4);
		//new TaskTimer().startTask(tlong.getInt("timelong"));//这个定时器是开奖定时器
		Tiemer.timer1();
		TiemerSecond.timer1();//这个定时器是同步前后端的倒计时定时器
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());
	}
	
	public void stop(){
		System.out.println("我执行结束开奖器的功能");
		JSONObject json = new JSONObject();
		json.put("state", Tiemer.StopTiemer());
		Tiemer.StopTiemer(); //把倒计时也重置一下
		TimeNumOver tlong = TimeNumOver.dao.findById(1);
		tlong.set("number", 0);
		tlong.update();
		renderJson(json.toJSONString());
	}
	
	//获取定时状态
	public void getTaskStautus(){
		TaskTimerBean tkb = TaskTimerBean.dao.findById(1);
		setAttr("tkb",tkb);
		render(PATH+"/kaijiangqi.html");
	}
	
	//设置奖池的号码为空的时候是否自动开奖
	public void setAutoStautus(){
		IsAutoStart tkb = IsAutoStart.dao.findById(1);
		setAttr("tkb",tkb);
		render(PATH+"/autonum.html");
	}
	
	//关闭自动开奖的设置
	public void stopAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 0);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	/**
	 * 开启自动开奖的设置
	 */
	public void starAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 1);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//清空开奖记录
	public void cleatLogNum(){
		Db.update("TRUNCATE TABLE lottery_log");
		OpenNum nup = OpenNum.dao.findById(1);
		nup.set("nowNum", 1);
		nup.update();
		
		JSONObject json = new JSONObject();
		json.put("state", "success");
		renderJson(json.toJSONString());
	}
	
	/**
	 * 设置一天开几期的界面
	 */
	public void openHtml(){
		OpenNum tkb = OpenNum.dao.findById(1);
		setAttr("tkb",tkb);
		render(PATH+"/openNum.html");
	}
	
	/**
	 * 设置一天开几期
	 */
	public void SetOpenNum(){
		OpenNum tkb = OpenNum.dao.findById(1);
		tkb.set("openNum", getParaToInt("time"));
		tkb.set("spareNum", getParaToInt("time"));
		tkb.set("nowNum", 1);
		JSONObject json = new JSONObject();
		if(tkb.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	//一键录入号码
	public void iptAutoNum(){
		DateUtil DU = new DateUtil();
		FormString fs = new FormString();
		String dateStr = getPara("dateStr");
		int timeNum = getParaToInt("timeNum");
		int forNum = getParaToInt("forNum");
		for(int i=1; i<=forNum; i++){
			OpenNum openn = OpenNum.dao.findById(1);
			String creantime = DU.getTime(dateStr, timeNum);
			LotteryLog lott = new LotteryLog();
			lott.set("creantime", creantime);
			lott.set("qiNum","6"+new FormString().formNum(openn.getInt("nowNum")));
			lott.set("Num", fs.getThreeNum());
			lott.save();
			openn.set("nowNum", openn.getInt("nowNum")+1);
			openn.update();
			dateStr = creantime;
		}
		JSONObject json = new JSONObject();
		json.put("msg", "1");
		renderJson(json.toJSONString());
	}
	
	//修改创建时间
	public void createUpNum(){
		LotteryLog lo = LotteryLog.dao.findById(getParaToInt("id"));
		lo.set("creantime", getPara("creantime"));
		lo.update();
		JSONObject json = new JSONObject();
		json.put("state", "success");
		renderJson(json.toJSONString());
	}
	
	//==========清理缓存=================
	public void cacheHtml(){
		render(PATH+"/cache.html");
	}
	
	public void clearCache(){
		JSONObject json = new JSONObject();
		json.put("state", "success");
		CacheUntil.get("LotteryLog").clear(); //清理缓存
		renderJson(json.toJSONString());
	}
	
}
