package lxq.user.util;

import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.bean.TimeNumOver;

public class TaskNumber extends BaseController{
	final Timer timer = new Timer();
	public void startTask() {
		Task uTask = new Task();
		timer.schedule(uTask, 1000, 1000);
	}
	
	public boolean stopTimer(){ //�رյ�ʱ��ѵ���ʱ����Ϊ0
		System.out.println("�ҽ�������ʱ�Ĺ���");
		timer.cancel();
		TimeNumOver taskt = TimeNumOver.dao.findById(1);
		taskt.set("number", 0);
		taskt.update();
		return true;
	}
	
	private class Task extends TimerTask {
		public Task() {
			System.out.println("����ʱ��ʱ����ʼ");
			TimeLong tl = TimeLong.dao.findById(4);
			TimeNumOver taskt = new TimeNumOver();
			taskt.set("id", 1);
			taskt.set("number", tl.getInt("timelong"));
			taskt.update();
		}

		@Override
		public void run() {
			//OpenNum on = OpenNum.dao.findById(1);//����һ�쿪��������
			//if(on.getInt("nowNum")<=on.getInt("openNum")){
				//�жϼ�ʱ���Ƿ�����������ҽ����ķ�������д��ִ��run�������֮��
				TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				if(taskt.getInt("status")==0){
					TimeNumOver tasktover = TimeNumOver.dao.findById(1);
					tasktover.set("number", -1);
					tasktover.update();
					timer.cancel();
				}else{
					TimeNumOver nowNum = TimeNumOver.dao.findById(1);
					if(nowNum.getInt("number")<=1){
						TimeLong taskTimer = TimeLong.dao.findById(4);
						nowNum.set("number", taskTimer.getInt("timelong"));
					}else{
						nowNum.set("number", nowNum.getInt("number")-1);
					}
					nowNum.update();
				}
			/*}else{ //����ѿ����������ڹ涨��������Ӧ���ǿ�����
				System.out.println("��������涨����������������������õļ�ʱ��Ҳ�ý�����");
				TimeNumOver tasktover = TimeNumOver.dao.findById(1);
				tasktover.set("number", -1);
				tasktover.update();
				timer.cancel();
			}*/
			
		}
		
	}
	
}
