package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "lottery_log")
public class LotteryLog extends BaseModel<LotteryLog>{

	private static final long serialVersionUID = 1L;
	public static final LotteryLog dao = new LotteryLog();

}
