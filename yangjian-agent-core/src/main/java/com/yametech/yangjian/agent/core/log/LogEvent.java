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

/**
 * @author zcn
 * @date: 2019-10-14
 **/
public class LogEvent {

    private LogLevel level;
    private String message;
    private Throwable throwable;
    private String targetClass;

    public LogEvent(LogLevel level, String message, Throwable throwable, String targetClass){
        this.level = level;
        this.message = message;
        this.throwable = throwable;
        this.targetClass = targetClass;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getTargetClass() {
        return targetClass;
    }
}