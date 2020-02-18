/**
 * Copyright 2020 yametech.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yametech.yangjian.agent.plugin.method;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yametech.yangjian.agent.api.bean.TimeEvent;
import com.yametech.yangjian.agent.api.common.MethodUtil;
import com.yametech.yangjian.agent.api.convert.IStatisticMethodConvert;

/**
 * 转换静态方法调用RT
 *
 * @author liuzhao
 * @Description
 * @date 2019年10月9日 下午3:43:15
 */
public class StatisticMethodConvert implements IStatisticMethodConvert {

	@Override
	public List<TimeEvent> convert(long startTime, Object[] allArguments, Method method, Object ret,
			Throwable t, Map<Class<?>, Object> globalVar) throws Throwable {
        TimeEvent event = get(startTime);
		event.setIdentify(MethodUtil.getCacheMethodId(method));
		return Arrays.asList(event);
    }
}
