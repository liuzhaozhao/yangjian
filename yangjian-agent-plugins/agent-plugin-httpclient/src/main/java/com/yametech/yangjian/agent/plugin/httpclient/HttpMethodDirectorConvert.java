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

package com.yametech.yangjian.agent.plugin.httpclient;

import com.yametech.yangjian.agent.api.bean.TimeEvent;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.api.convert.IMethodConvert;
import org.apache.commons.httpclient.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 转换httpclient调用事件
 * 支持版本：httpclient-3、httpclient-3.1
 *
 * @author dengliming
 * @date 2019/11/21
 */
public class HttpMethodDirectorConvert implements IMethodConvert {
    
    @Override
    public List<TimeEvent> convert( Object thisObj, long startTime, Object[] allArguments, Method method, Object ret,
    		Throwable t, Map<Class<?>, Object> globalVar) throws Throwable {
    	HttpMethod httpMethod = (HttpMethod) allArguments[0];
    	String requestUrl = StringUtil.filterUrlParams(httpMethod.getURI().toString());
    	if (StringUtil.isEmpty(requestUrl)) {
    	    return null;
        }
        // 过滤url上的查询参数
        TimeEvent event = get(startTime);
		event.setIdentify(requestUrl);
		return Arrays.asList(event);
    }
    
}
