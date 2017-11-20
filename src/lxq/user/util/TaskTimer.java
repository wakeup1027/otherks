package lxq.user.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.base.BaseController;
import com.bean.IsAutoStart;
import com.bean.Lottery;
import com.bean.LotteryLog;
import com.bean.OpenNum;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.bean.TimeNumOver;

public class TaskTimer extends BaseController{
	private FormString fs = new FormString();
	final Timer timer = new Timer();
	public void startTask(long time) {
		Task uTask = new Task();
		timer.schedule(uTask, time*1000, time*1000);
	}
	
	public boolean stopTimer(){
		System.out.println("ֹͣ������");
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		taskt.set("status", 0);
		taskt.set("person", 1);
		taskt.update();
		return true;
	}
	
	private class Task extends TimerTask {
		public Task() {
			System.out.println("��ʼ������ʱ��");
			//���ö�ʱ����״̬Ϊ����״̬
			TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
			taskt.set("status", 1);
			taskt.set("person", 0);
			taskt.update();
			
			//�������ÿ�������
			/*OpenNum on = OpenNum.dao.findById(1);//����һ�쿪��������
			on.set("nowNum", 1);
			on.set("spareNum", on.getInt("openNum"));
			on.update();*/
		}

		@Override
		public void run() {
			System.out.println("������ʼ");
			Lottery nowNum = Lottery.dao.findFirst("SELECT * FROM lottery ORDER BY creantime ASC");
			OpenNum on = OpenNum.dao.findById(1);//����һ�쿪��������
			//if(on.getInt("nowNum")<=on.getInt("openNum")){ //����ѿ����������ڹ涨��������Ӧ���ǿ�����
				LotteryLog llog = new LotteryLog();
				if(null==nowNum){//���ڿյ�ʱ���˵������������û�к�����
					IsAutoStart ias = IsAutoStart.dao.findById(1);
					if(ias.getInt("status")==1){ //�ж��Ƿ��������Զ�����
						llog.put("qiNum",getYear()+fs.formNum(on.getInt("nowNum")));
						llog.put("Num",fs.getThreeNum());
						llog.put("creantime",getNow());
						List<LotteryLog> ltt = LotteryLog.dao.find("SELECT * FROM lottery_log WHERE qiNum = '"+llog.getStr("qiNum")+"'");
						if(ltt.size()>0){
							for(LotteryLog lol : ltt){
								lol.delete();
							}
						}
						llog.save();
						//���¿�ʼ��ʱ
						TimeNumOver tlong = TimeNumOver.dao.findById(1);
						tlong.set("number", (TimeLong.dao.findById(4)).getInt("timelong"));
						tlong.update();
						//���¿�ʼ��������
						on.set("nowNum", on.getInt("nowNum")+1);
						//on.set("spareNum", on.getInt("spareNum")-1);
						on.update();
					}else{ //�����������
						System.out.println("��������");
						TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
						taskt.set("status", 0);
						taskt.set("person", 2);
						taskt.update();
						
						TimeNumOver tlong = TimeNumOver.dao.findById(1);
						tlong.set("number", 0);
						tlong.update();
						timer.cancel();
						return;
					}
					
				}else{ //ʹ�ý�������ĺ���
					llog.put("qiNum",getYear()+fs.formNum(on.getInt("nowNum")));
					llog.put("Num",nowNum.getInt("Num"));
					llog.put("creantime",getNow());
					List<LotteryLog> ltt = LotteryLog.dao.find("SELECT * FROM lottery_log WHERE qiNum = '"+llog.getStr("qiNum")+"'");
					if(ltt.size()>0){
						for(LotteryLog lol : ltt){
							lol.delete();
						}
					}
					if(llog.save()){
						nowNum.delete();
						on.set("nowNum", on.getInt("nowNum")+1);
						//on.set("spareNum", on.getInt("spareNum")-1);
						on.update();
					}
				}
			/*}else{
				System.out.println("��������涨����������Ҫ��������");
				TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
				taskt.set("status", 0);
				taskt.set("person", 3);
				taskt.update();
				
				timer.cancel();
				return;
			}*/

			//�жϼ�ʱ���Ƿ�����������ҽ����ķ�������д��ִ��run�������֮��
			TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
			if(taskt.getInt("status")==0){
				timer.cancel();
			}
		}
		
	}
	
}
