package com.mlab.xiaocao.service.time;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mlab.xiaocao.util.DataUtil;

@Service("timeService")
public class TimeService {
	
	@Resource(name="dataUtil")
	public DataUtil Util;

	public String getTime() {
		return System.currentTimeMillis() + "";
	}
}
