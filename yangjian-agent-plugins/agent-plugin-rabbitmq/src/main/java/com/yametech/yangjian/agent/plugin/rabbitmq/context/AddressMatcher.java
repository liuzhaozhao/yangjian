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

package com.yametech.yangjian.agent.plugin.rabbitmq.context;

import java.util.Arrays;

import com.yametech.yangjian.agent.api.IEnhanceClassMatch;
import com.yametech.yangjian.agent.api.InterceptorMatcher;
import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.configmatch.CombineAndMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineOrMatch;
import com.yametech.yangjian.agent.api.configmatch.InterfaceMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodNameMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodReturnMatch;

/**
 * 添加地址上下文
 * @Description 
 * 
 * @author liuzhao
 * @date 2019年11月8日 下午6:13:04
 */
public class AddressMatcher implements InterceptorMatcher, IEnhanceClassMatch {
	
	@Override
	public IConfigMatch classMatch() {
		return new CombineOrMatch(Arrays.asList(
				new InterfaceMatch("com.rabbitmq.client.Channel")
//				new ClassMatch("com.rabbitmq.client.impl.recovery.AutorecoveringChannel"),
//				new ClassMatch("com.rabbitmq.client.impl.ChannelN")
			));
	}
	
	@Override
	public IConfigMatch match() {
		return new CombineAndMatch(Arrays.asList(
				new InterfaceMatch("com.rabbitmq.client.Connection"),
				new MethodNameMatch("createChannel"),
				new MethodReturnMatch("com.rabbitmq.client.Channel")
			));
	}
	
	@Override
	public LoadClassKey loadClass(MethodType type) {
		return new LoadClassKey("com.yametech.yangjian.agent.plugin.rabbitmq.context.AddressInterceptor");
	}
	
}
