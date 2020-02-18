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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yametech.yangjian.agent.core.log.Converter;
import com.yametech.yangjian.agent.core.log.LogEvent;

/**2019-10-24 22:20:39.707[INFO]-[main]-[c.z.h.HikariDataSource.<init>(80)]: HikariPool-1 - Starting...
 * @author zcn
 * @date: 2019-10-14
 **/
public class TimestampConverter implements Converter {

    @Override
    public String convert(LogEvent event) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
