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

package com.yametech.yangjian.agent.core.aop;

import com.yametech.yangjian.agent.api.IMetricMatcher;
import com.yametech.yangjian.agent.core.aop.base.MetricEventBus;

public class BaseConvertAOP {
	protected Object convert;
	protected MetricEventBus metricEventBus;
	protected IMetricMatcher metricMatcher;
	
	void init(Object convert, MetricEventBus metricEventBus, IMetricMatcher metricMatcher) {
		this.convert = convert;
		this.metricEventBus = metricEventBus;
		this.metricMatcher = metricMatcher;
	}
}