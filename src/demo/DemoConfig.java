package demo;

//import gxTest.gxFunc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lxq.user.util.FormString;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.config.HtmlHandler;
import com.config.BasePathHandler;
import com.config.AutoBindModels;
import com.config.AutoBindRoutes;
import com.config.SessionAttrInterceptor;

public class DemoConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		//���ý�����Ⱦhtml�Ĺ���
		me.setViewType(ViewType.JSP); // ������ͼ����ΪJsp������Ĭ��ΪFreeMarker
		JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
		rf.config();
		me.setRenderFactory(rf);
		
		// ��ȡGroupTemplate ,�������ù�������Ȳ���  ����������Ⱦ�����ʱ��������ֽ����еķ��� Ҳ����ִ��
		GroupTemplate groupTemplate = rf.groupTemplate;
		groupTemplate.registerFunctionPackage("strutil", FormString.class);
	}

	@Override
	public void configRoute(Routes me) {
		//����·�ɣ����Ƿ��ʵ�ַ��
		//me.add("/hello",HelloController.class);
		me.add(new AutoBindRoutes());
	}

	@Override
	public void configEngine(Engine me) {
		//��ӹ�������ģ���ļ�
	}

	@Override
	public void configHandler(Handlers me) {
		//����·��htmlα·��
		me.add(new HtmlHandler());
		// ȫ·����ȡ
		me.add(new BasePathHandler("BASE_PATH"));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//����������
		me.add(new SessionAttrInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		InputStream in=null;
        Properties props=new Properties();
        try {
        	in=getClass().getResourceAsStream("dbconfig.properties" );
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String jdbcUrl=props.getProperty("jdbcUrl").trim();
        String url=props.getProperty("url").trim();
        String user=props.getProperty("user").trim();
        String passw=props.getProperty("passw").trim();
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, user, passw, url);
		me.add(c3p0Plugin);

		// ����ActiveRecord���
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.setShowSql(true);

		new AutoBindModels(arp);
	}
	
}
