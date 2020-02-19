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

package com.yametech.yangjian.agent.api.configmatch;

import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.bean.MethodDefined;

/**
 * 方法参数个数匹配
 * 
 * @author liuzhao
 */
public class MethodArgumentNumMatch implements IConfigMatch {
	private int argumentNum;
	
	public MethodArgumentNumMatch(int argumentNum) {
		this.argumentNum = argumentNum;
	}
	
	@Override
	public boolean isMatch(MethodDefined methodDefined) {
		int num = methodDefined.getParams() == null ? 0 : methodDefined.getParams().length;
		return num == argumentNum;
	}
	
	@Override
	public String toString() {
		return "argument num is " + argumentNum;
	}
	
}
