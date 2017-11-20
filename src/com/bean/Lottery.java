package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "lottery")
public class Lottery extends BaseModel<Lottery>{

	private static final long serialVersionUID = 1L;
	public static final Lottery dao = new Lottery();

	private class Invalid{  
        
    }
}
