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

package com.yametech.yangjian.agent.core.jvm.metrics;

/**
 * GC度量指标
 *
 * @author dengliming
 * @date 2019/12/27
 */
public class JVMGcMetrics implements IMetrics {

    /**
     * 一个时间片内累计 YoungGC 次数
     */
    private final long youngGcCount;
    /**
     * 一个时间片内累计 YoungGC 时间
     */
    private final long youngGcTime;
    /**
     * 一个时间片内 YoungGC 平均时间
     */
    private final double avgYoungGcTime;
    /**
     * 一个时间片内累计 OldGC 次数
     */
    private final long fullGcCount;
    /**
     * 一个时间片内累计 OldGC 时间
     */
    private final long fullGcTime;

    public JVMGcMetrics(long youngGcCount, long youngGcTime, long fullGcCount, long fullGcTime) {
        this.youngGcCount = youngGcCount;
        this.youngGcTime = youngGcTime;
        this.fullGcCount = fullGcCount;
        this.fullGcTime = fullGcTime;
        this.avgYoungGcTime = youngGcCount > 0L ? ((double) youngGcTime) / youngGcCount : 0d;
    }

    public long getYoungGcCount() {
        return youngGcCount;
    }

    public long getYoungGcTime() {
        return youngGcTime;
    }

    public double getAvgYoungGcTime() {
        return avgYoungGcTime;
    }

    public long getFullGcCount() {
        return fullGcCount;
    }

    public long getFullGcTime() {
        return fullGcTime;
    }
}
