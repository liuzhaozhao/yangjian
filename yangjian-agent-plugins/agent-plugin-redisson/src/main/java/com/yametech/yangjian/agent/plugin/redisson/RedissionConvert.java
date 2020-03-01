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

package com.yametech.yangjian.agent.plugin.redisson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.redisson.client.protocol.CommandData;
import org.redisson.client.protocol.CommandsData;

import com.yametech.yangjian.agent.api.bean.TimeEvent;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.api.convert.IMethodAsyncConvert;
import com.yametech.yangjian.agent.plugin.redisson.bean.RedisKeyBean;

import io.netty.buffer.ByteBuf;

/**
 * 转换redission事件
 *
 * @author dengliming
 * @date 2019/12/5
 */
public class RedissionConvert implements IMethodAsyncConvert {
    private List<String> keyRules = new CopyOnWriteArrayList<>();
    
    @SuppressWarnings("unchecked")
	@Override
    public void setConvertConfig(Object config) {
    	keyRules = (List<String>) config;
    }
    
    @Override
    public List<Object> convert(Object thisObj, long startTime, Object[] allArguments, Method method, Object ret,
    		Throwable t, Map<Class<?>, Object> globalVar) throws Throwable {
        if (allArguments == null || allArguments[0] == null) {
            return null;
        }
        List<CommandData> commandDatas = new ArrayList<>();
        if (allArguments[0] instanceof CommandsData) {
            CommandsData commands = (CommandsData) allArguments[0];
            if (commands.getCommands() != null) {
                commandDatas.addAll(commands.getCommands());
            }
        } else if (allArguments[0] instanceof CommandData) {
            commandDatas.add((CommandData) allArguments[0]);
        }

        List<String> keys = new ArrayList<>();
        for (CommandData commandData : commandDatas) {
            String command = commandData.getCommand().getName();
            // 过滤执行lua脚本命令
            if (isEvalScript(command)) {
                continue;
            }
            String key = getCommandKey(commandData);
            if (StringUtil.notEmpty(key)) {
                keys.add(key);
            }
        }
        long now = System.currentTimeMillis();
        return Arrays.asList(new RedisKeyBean(keys, now, now - startTime));
    }
    
    @Override
    public List<TimeEvent> convert(Object eventBean) {
        RedisKeyBean redisKeyBean = (RedisKeyBean) eventBean;
        return getMatchKeyTimeEvents(redisKeyBean);
    }
    
    private List<TimeEvent> getMatchKeyTimeEvents(RedisKeyBean redisKeyBean) {
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

    private Set<String> getMatchKeyRules(String key) {
        if (StringUtil.isEmpty(key) || keyRules == null) {
            return null;
        }
        return keyRules.stream()
                .filter(r -> key.indexOf(r) != -1)
                .collect(Collectors.toSet());
    }
    
    /**
     * 获取redis执行命令的key
     *
     * @param commandData
     * @return
     */
    private String getCommandKey(CommandData commandData) {
        Object[] params = commandData.getParams();
        if (params != null && params.length > 0) {
            return params[0] instanceof ByteBuf ? "?" : String.valueOf(params[0].toString());
        }
        return null;
    }

    /**
     * 判断是否是执行lua脚本的命令
     *
     * @param command
     * @return
     */
    public boolean isEvalScript(String command) {
        return command.equalsIgnoreCase("EVAL");
    }
}
