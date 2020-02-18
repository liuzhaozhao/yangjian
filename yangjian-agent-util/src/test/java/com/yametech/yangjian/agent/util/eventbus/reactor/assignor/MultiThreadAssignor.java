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

package com.yametech.yangjian.agent.util.eventbus.reactor.assignor;

public interface MultiThreadAssignor<T> {
    /**
     * 根据消费数据以及线程数返回消费线程标识（同一个标识的数据使用同一个线程消费）
     * @param msg
     * @param totalThreadNum
     * @return
     */
    int threadNum(T msg, int totalThreadNum);
}
