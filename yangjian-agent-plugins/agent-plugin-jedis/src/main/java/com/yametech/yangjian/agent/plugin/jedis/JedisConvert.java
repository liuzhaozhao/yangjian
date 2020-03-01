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

package com.yametech.yangjian.agent.plugin.jedis;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.yametech.yangjian.agent.api.bean.TimeEvent;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.api.convert.IMethodAsyncConvert;
import com.yametech.yangjian.agent.plugin.jedis.bean.RedisKeyBean;

/**
 * 转换jedis调用事件
 *
 * @author dengliming
 * @date 2019/12/4
 */
public class JedisConvert implements IMethodAsyncConvert {
	private List<String> keyRules = new CopyOnWriteArrayList<>();
    
    @SuppressWarnings("unchecked")
	@Override
    public void setConvertConfig(Object config) {
    	keyRules = (List<String>) config;
    }
	
    @Override
    public List<Object> convert(Object thisObj, long startTime, Object[] allArguments, Method method, Object ret,
    		Throwable t, Map<Class<?>, Object> globalVar) throws Throwable {
        if (allArguments == null || allArguments.length == 0) {
            return null;
        }
        String key = null;
        if (allArguments[0] instanceof String) {
            key = (String) allArguments[0];
        } else if (allArguments[0] instanceof byte[]) {
            key = StringUtil.encode((byte[]) allArguments[0]);
        }
        long now = System.currentTimeMillis();
        return Arrays.asList(new RedisKeyBean(Arrays.asList(key), now, now - startTime));
    }
    
    @Override
    public List<TimeEvent> convert(Object eventBean) {
    	RedisKeyBean redisKeyBean = (RedisKeyBean) eventBean;
        return getMatchKeyTimeEvents(redisKeyBean);
    }
    
    public List<TimeEvent> getMatchKeyTimeEvents(RedisKeyBean redisKeyBean) {
        Map<String, Integer> matchKeyNums = new HashMap<>();
        for (String key : redisKeyBean.getKeys()) {
            Set<String> matchKeyRules = getMatchKeyRules(key);
            if (matchKeyRules == null) {
                continue;
            }
            for (String keyRule : matchKeyRules) {
                Integer num = matchKeyNums.getOrDefault(keyRule, 0);
                matchKeyNums.put(key, num + 1);
            }
        }
        return matchKeyNums.entrySet()
                .stream()
                .map(o -> {
                    TimeEvent timeEvent = new TimeEvent();
                    timeEvent.setEventTime(redisKeyBean.getEventTime());
                    timeEvent.setUseTime(redisKeyBean.getUseTime());
                    timeEvent.setIdentify(o.getKey());
                    timeEvent.setNumber(o.getValue());
                    return timeEvent;
                })
                .collect(Collectors.toList());
    }

    public Set<String> getMatchKeyRules(String key) {
        if (StringUtil.isEmpty(key) || keyRules == null) {
            return null;
        }
        return keyRules.stream()
                .filter(r -> key.indexOf(r) != -1)
                .collect(Collectors.toSet());
    }
    
}
