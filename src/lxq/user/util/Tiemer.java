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
import com.bean.TimeNumOver;

public class Tiemer extends BaseController{
	private static FormString fs = new FormString();

	public static void timer1() {
		System.out.println("��ʼ������ʱ��");
		//���ö�ʱ����״̬Ϊ����״̬
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		taskt.set("status", 1);
		taskt.set("person", 0);
		taskt.update();
		final Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	      public void run() {
			//�жϼ�ʱ���Ƿ�����������ҽ����ķ�������д��ִ��run�������֮��
			TaskTimerBean tasktFirst = TaskTimerBean.dao.findById(1);
			if(tasktFirst.getInt("status")==0){
				timer.cancel();
			}else{
				System.out.println("������ʼ");
				  Lottery nowNum = Lottery.dao.findFirst("SELECT * FROM lottery ORDER BY creantime ASC");
				  OpenNum on = OpenNum.dao.findById(1);//����һ�쿪��������
				  LotteryLog llog = new LotteryLog();
				  if(null==nowNum){//���ڿյ�ʱ���˵������������û�к�����
					  IsAutoStart ias = IsAutoStart.dao.findById(1);
					  if(ias.getInt("status")==1){ //�ж��Ƿ��������Զ�����
						  llog.put("qiNum",getYear().substring(2, 4)+fs.formNum(on.getInt("nowNum")));
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
						  tlong.set("number", 600);
						  tlong.update();
						  
						  //���¿�ʼ��������
						  on.set("nowNum", on.getInt("nowNum")+1);
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
					 }
				  }else{
					  llog.put("qiNum",getYear().substring(2, 4)+fs.formNum(on.getInt("nowNum")));
					  llog.put("Num",fs.formNumTwo(nowNum.getInt("Num")));
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
							on.update();
							
							//���¿�ʼ��ʱ
							TimeNumOver tlong = TimeNumOver.dao.findById(1);
							tlong.set("number", 600);
							tlong.update();
					  }
				  }
			}

	      }
	      
	    }, 600000, 600000);
	}
	
	public static boolean StopTiemer(){
		TaskTimerBean taskt = TaskTimerBean.dao.findById(1);
		taskt.set("status", 0);
		taskt.set("person", 2);
		taskt.update();
		return true;
	}
	
}
