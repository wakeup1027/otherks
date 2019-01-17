package demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor{

	public void intercept(Invocation ai) {
		Controller controller = ai.getController();

		HttpServletRequest request = controller.getRequest();
		/*String sdsd = request.getHeader("x-forwarded-for");
		String sdsd1 = request.getHeader("Proxy-Client-IP");
		String sdsd2 = request.getHeader("WL-Proxy-Client-IP");
		String sdsd3 = request.getRemoteAddr();
		System.out.println(sdsd);
		System.out.println(sdsd1);
		System.out.println(sdsd2);
		System.out.println(sdsd3);*/
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("loginUser");
		if (obj == null) {
			System.out.println("该用户还没有登陆");
			controller.redirect("/Adfkasdminwlks.html");
			return;
		}
		ai.invoke();
	}

}
