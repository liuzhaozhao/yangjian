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

package com.yametech.yangjian.agent.plugin.mongo;

import java.util.Arrays;

import com.yametech.yangjian.agent.api.IMetricMatcher;
import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.common.Constants;
import com.yametech.yangjian.agent.api.configmatch.ClassMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineAndMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineOrMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodArgumentIndexMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodNameMatch;

/**
 * mongo集合操作方法拦截
 *
 * 支持版本：3.8.x-3.12.0
 * com.mongodb.client.internal.MongoClientDelegate$DelegateOperationExecutor.execute(...)
 *
 * @author dengliming
 * @date 2019/12/13
 */
public class DelegateOperationExecutorMatcher implements IMetricMatcher {

    // 3.8.x-3.12.0
    private static final String ARGUMENT_TYPE_1 = "com.mongodb.client.ClientSession";
    // 3.7.x
    private static final String ARGUMENT_TYPE_2 = "com.mongodb.session.ClientSession";
    @Override
    public IConfigMatch match() {
        return new CombineAndMatch(Arrays.asList(
                new ClassMatch("com.mongodb.client.internal.MongoClientDelegate$DelegateOperationExecutor"),
                new MethodNameMatch("execute"),

                new CombineOrMatch(Arrays.asList(
                        new MethodArgumentIndexMatch(2, ARGUMENT_TYPE_1),
                        new MethodArgumentIndexMatch(3, ARGUMENT_TYPE_1),
                        new MethodArgumentIndexMatch(1, ARGUMENT_TYPE_2),
                        new MethodArgumentIndexMatch(2, ARGUMENT_TYPE_2)
                ))
        ));
    }

    @Override
    public String type() {
        return Constants.EventType.MONGO;
    }
    
    @Override
    public LoadClassKey loadClass(MethodType type) {
    	return new LoadClassKey("com.yametech.yangjian.agent.plugin.mongo.DelegateOperationExecutorConvert");
    }
}
