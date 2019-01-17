package lxq.user.util;

import java.util.Random;

/**
 * ��ʽ�������
 * @author Administrator
 *
 */
public class FormString {
	private static final String[] StrNum= {"1","2","3","4","5","6"};//�������
	
	//��ȡ��һ����
	public String firstNum(String Num){
		String StrNum = "";
		switch (Num.substring(0,1)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//��ȡ�ڶ�λ��
	public String secondNum(String Num){
		String StrNum = "";
		switch (Num.substring(1,2)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//��ȡ����λ��
	public String threeNum(String Num){
		String StrNum = "";
		switch (Num.substring(2,3)) {
		case "1":
			StrNum = "0";
			break;
		case "2":
			StrNum = "-42";
			break;
		case "3":
			StrNum = "-84";
			break;
		case "4":
			StrNum = "-126";
			break;
		case "5":
			StrNum = "-168";
			break;
		case "6":
			StrNum = "-210";
			break;
		default:
			StrNum = "0";
			break;
		}
		return StrNum;
	}
	
	//���С
	public String bigorsam(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)<=10){
			return "С";
		}else{
			return "��";
		}
	}
	
	//��˫
	public String onlyAll(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)%2==0){
			return "˫";
		}else{
			return "��";
		}
	}
	
	//���
	public int allNum(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		return (f+s+t);
	}
	
	//�����ȡ1~6����λ��
	public String getThreeNum(){
		Random random = new Random();
		int[] arr1 = new int[3];
		for(int i=0; i<3; i++){
			int index = random.nextInt(6);
			arr1[i]=Integer.parseInt(StrNum[index]);
		}
		return Paixu(arr1);
	}
	
	public String formNumTwo(int num){
		int[] arr1 = new int[3];
		String numStr = num+"";
		arr1[0] = Integer.parseInt(numStr.substring(0,1));
		arr1[1] = Integer.parseInt(numStr.substring(1,2));
		arr1[2] = Integer.parseInt(numStr.substring(2,3));
		return Paixu(arr1);
	}
	
	public String Paixu(int[] num){
		int temp = 0;
        int size = num.length;
        for(int i = 0 ; i < size-1; i ++){
        	for(int j = 0 ;j < size-1-i ; j++){
        		if(num[j] > num[j+1]){  //��������λ��
        			temp = num[j];
        			num[j] = num[j+1];
        			num[j+1] = temp;
        		}
        	}
        }
        return String.valueOf(num[0])+String.valueOf(num[1])+String.valueOf(num[2]);
	}
	
	//��λ�����λ�������ʱ����0������
	public String formNum(int nowday){
		String strnowday = String.valueOf(nowday);
		String zero = "";
		if(5-strnowday.length()<=0){
			zero = "0";
		}else{
			for(int i=0; i<(5-strnowday.length()); i++){
				zero+="0";
			}
		}
		return zero+strnowday;
	}
	
	//�ж��û���½��֤
	public boolean userLogin(String user, String password){
		if("admin".equals(user)&&"123456".equals(password)){
			return true;
		}else{
			return false;
		}
	}
	
	public int subStr(String num){
		String numStr = num.substring(4, num.length());
		int numInt = Integer.parseInt(numStr);
		return numInt;
	}
	
	public String subStrTwo(String num,int star){
		return num.substring(star, num.length());
	}
	
	public static void main(String[] args) { 
		  System.out.println(new FormString().formNumTwo(452));
	}
	
}
