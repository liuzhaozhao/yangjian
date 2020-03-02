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
package com.yametech.yangjian.agent.core.log;

import com.yametech.yangjian.agent.api.log.ILoggerFactory;
import com.yametech.yangjian.agent.api.log.ILoggerServiceProvider;

/**
 * 自定义日志实现（提供获取loggerFactory方法）
 *
 * @author dengliming
 * @date 2020/3/1
 */
public class AgentLoggerProvider implements ILoggerServiceProvider {

    private ILoggerFactory loggerFactory;

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public void initialize() {
        loggerFactory = new LoggerFactory();
    }
}
