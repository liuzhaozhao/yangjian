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

package com.yametech.yangjian.agent.plugin.reporter;

import com.yametech.yangjian.agent.api.IConfigReader;
import com.yametech.yangjian.agent.api.IReport;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.util.HttpClient;
import com.yametech.yangjian.agent.util.HttpRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HTTP上报
 *
 * 注：agent.properties配置report=http
 *
 * @author dengliming
 * @date 2020/3/5
 */
public class HttpReporter implements IReport, IConfigReader {

    private static final String URL_CONFIG_KEY = "report.http.url";
    /**
     * 上报的URL
     */
    private String url;

    @Override
    public boolean report(String dataType, Long second, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return false;
        }
        params.put("dataType", dataType);
        params.put("second", second);
        return StringUtil.notEmpty(HttpClient.doHttpRequest(new HttpRequest(url, HttpRequest.HttpMethod.POST)
                .setDatas(buildRequestData(params))));
    }

    @Override
    public String type() {
        return "http";
    }

    @Override
    public Set<String> configKey() {
        return new HashSet<>(Arrays.asList(URL_CONFIG_KEY));
    }

    @Override
    public void configKeyValue(Map<String, String> kv) {
        if (kv.containsKey(URL_CONFIG_KEY)) {
            url = kv.get(URL_CONFIG_KEY);
        }
    }

    private String buildRequestData(Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(StringUtil.encode(entry.getKey()));
            result.append("=");
            result.append(StringUtil.encode(entry.getValue().toString()));
        }

        return result.toString();
    }
}
