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

package com.yametech.yangjian.agent.plugin.mongo.context;

import com.mongodb.MongoNamespace;

import com.yametech.yangjian.agent.api.base.IContext;
import com.yametech.yangjian.agent.api.interceptor.IConstructorListener;

/**
 * MongoDb集合操作类增强获取集合名称
 *
 * @author dengliming
 * @date 2019/12/17
 */
public class MongoOperationInterceptor implements IConstructorListener {

    @Override
    public void constructor(Object thisObj, Object[] allArguments) {
        if (allArguments[0] == null) {
            return;
        }
        MongoNamespace namespace = (MongoNamespace) allArguments[0];
        ((IContext) thisObj)._setAgentContext(ContextConstants.MONGO_OPERATOR_COLLECTION, namespace.getCollectionName());
    }
}
