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

package com.yametech.yangjian.agent.api;

import com.yametech.yangjian.agent.api.common.Constants;

public interface IMetricMatcher extends InterceptorMatcher {
	
	/**
     * 须指定事件类型，如：dubbo-client、dubbo-server、http-server、redis、mysql、kafka、rabbitmq、mongo、http-client
     * @return	事件类型
     */
    default String type() {
        return Constants.EventType.METHOD;
    }
    
    /**
     * 
     * @return	返回对应convert需要的配置数据
     */
    default Object convertConfig() {
    	return null;
    }
}
