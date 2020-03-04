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

package com.yametech.yangjian.agent.core.log.converter;

import com.yametech.yangjian.agent.core.log.IConverter;
import com.yametech.yangjian.agent.core.log.LogEvent;

/**
 * @author zcn
 * @date: 2019-10-14
 **/
public class LevelConverter implements IConverter<LogEvent> {
    @Override
    public String convert(LogEvent event) {
        return event.getLevel().name();
    }
}
