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

package com.yametech.yangjian.agent.util.eventbus.regist;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.yametech.yangjian.agent.util.ClassUtil;
import com.yametech.yangjian.agent.util.eventbus.consume.BaseConfigConsume;

/**
 * 使用该注册器需保证BaseConsume的实例都有无参构造方法，否则不能自动创建实例
 */
public class AutoSearchRegist implements IConsumeRegist {
    private Class<?> parentCls = BaseConfigConsume.class;
    private String packagePath;
    private Class<?> genericCls;// 类或者父类相同

    public AutoSearchRegist(String packagePath) {
        this.packagePath = packagePath;
    }

    public AutoSearchRegist setParentCls(Class<?> parentCls) {
        this.parentCls = parentCls;
        return this;
    }

    public AutoSearchRegist setGenericCls(Class<?> genericCls) {
        this.genericCls = genericCls;
        return this;
    }

    @SuppressWarnings({ "rawtypes" })
	@Override
    public List<BaseConfigConsume<?>> regist() {
        List<BaseConfigConsume<?>> consumes = new ArrayList<>();
        Set<Class<?>> consumeCls =  ClassUtil.scanPackageBySuper(packagePath, false, parentCls);
        for(Class<?> cls : consumeCls) {
            if(genericCls != null) {
            	Type type = ClassUtil.getGenericCls(cls);
                if(type instanceof Class && !genericCls.isAssignableFrom((Class<?>)type)) {// 泛型类不匹配则跳过
                    continue;
                }
            }
            try {
                consumes.add((BaseConfigConsume)cls.newInstance());
                //log.info("加载消费类{}", cls.getName());
            } catch (InstantiationException | IllegalAccessException e) {
                //log.error("自动加载BaseConsume异常", e);
                throw new RuntimeException(e);
            }
        }
        return consumes;
    }
}
