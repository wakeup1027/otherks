package lxq.user.util;

import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.TaskTimerBean;
import com.bean.TimeNumOver;

public class TiemerSecond extends BaseController{

	public static void timer1() {
		//���ö�ʱ����״̬Ϊ����״̬
		final Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	      public void run() {
	    	  TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				if(taskt.getInt("status")==0){
					System.out.println("�ҽ����˼�ʱ����");
					TimeNumOver tasktover = TimeNumOver.dao.findById(1);
					tasktover.set("number", -1);
					tasktover.update();
					timer.cancel();
				}else{
					TimeNumOver nowNum = TimeNumOver.dao.findById(1);
					if(nowNum.getInt("number")<=1){
						nowNum.set("number", 300);
					}else{
						nowNum.set("number", nowNum.getInt("number")-2);
					}
					nowNum.update();
				}
	      }
	      
	    }, 2000, 2000);
	}
	
}
