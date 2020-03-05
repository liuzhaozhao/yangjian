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

package com.yametech.yangjian.agent.api.log;

/**
 * @author zcn
 * @date: 2019-10-14
 **/
public interface ILogger {

    boolean isDebugEnable();

    boolean isInfoEnable();

    boolean isWarnEnable();

    boolean isErrorEnable();

    void debug(String format);

    void debug(String format, Object... arguments);

    void info(String format);

    void info(String format, Object... arguments);

    void warn(String format);

    void warn(String format, Object... arguments);

    void warn(Throwable e, String format, Object... arguments);

    void error(String format);

    void error(String format, Object... arguments);

    void error(Throwable e, String format, Object... arguments);
}
