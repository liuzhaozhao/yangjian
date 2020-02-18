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

package com.yametech.yangjian.agent.plugin.jedis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.yametech.yangjian.agent.api.IConfigReader;
import com.yametech.yangjian.agent.api.IMetricMatcher;
import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.bean.TimeEvent;
import com.yametech.yangjian.agent.api.common.Constants;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.api.configmatch.ClassMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineAndMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineOrMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodRegexMatch;
import com.yametech.yangjian.agent.plugin.jedis.bean.RedisKeyBean;

/**
 * 转换jedis调用事件
 *
 * @author dengliming
 * @date 2019/12/4
 */
public class JedisMatcher implements IMetricMatcher, IConfigReader {

    @Override
    public IConfigMatch match() {
        /**
         * redis.clients.jedis.Jedis
         * redis.clients.jedis.BinaryJedis
         * redis.clients.jedis.ShardedJedis
         * redis.clients.jedis.BinaryShardedJedis
         */
        return new CombineAndMatch(Arrays.asList(
                new CombineOrMatch(Arrays.asList(
                        new ClassMatch("redis.clients.jedis.Jedis"),
                        new ClassMatch("redis.clients.jedis.BinaryJedis"))),
                new MethodRegexMatch(".*redis\\.clients\\.jedis.*(zcount|sunionstore|zunionstore|del|zinterstore|echo|hscan|psubscribe|type|sinterstore|setex|zlexcount|brpoplpush|bitcount|llen|zscan|lpushx|bitpos|setnx|hvals|evalsha|substr|geodist|zrangeByLex|geoadd|expire|bitop|zrangeByScore|smove|lset|decrBy|pttl|scan|zrank|blpop|rpoplpush|zremrangeByLex|get|lpop|persist|scriptExists|georadius|set|srandmember|incr|setbit|hexists|expireAt|pexpire|zcard|bitfield|zrevrangeByLex|sinter|srem|getrange|rename|zrevrank|exists|setrange|zremrangeByRank|sadd|sdiff|zrevrange|getbit|scard|sdiffstore|zrevrangeByScore|zincrby|rpushx|psetex|zrevrangeWithScores|strlen|hdel|zremrangeByScore|geohash|brpop|lrem|hlen|decr|scriptLoad|lpush|lindex|zrange|incrBy|getSet|ltrim|incrByFloat|rpop|sort|zrevrangeByScoreWithScores|pfadd|linsert|pfcount|keys|hsetnx|hincrBy|hgetAll|hset|spop|zrangeWithScores|hincrByFloat|hmset|renamenx|zrem|msetnx|hmget|sunion|hget|zadd|move|subscribe|geopos|mset|zrangeByScoreWithScores|zscore|pexpireAt|georadiusByMember|ttl|lrange|smembers|pfmerge|rpush|publish|mget|sscan|append|sismember)\\(.*")
        ));
    }

    @Override
    public String type() {
        return Constants.EventType.REDIS;
    }
    
    @Override
    public LoadClassKey loadClass(MethodType type) {
    	return new LoadClassKey("com.yametech.yangjian.agent.plugin.jedis.JedisConvert");
    }
    
    private Set<String> keyRules = new HashSet<>();

    @Override
    public Set<String> configKey() {
        return new HashSet<>(Arrays.asList("redis.key.rule", "redis.key.rule\\..*"));
    }

    /**
     * 覆盖更新
     *
     * @param kv 配置数据
     */
    @Override
    public void configKeyValue(Map<String, String> kv) {
        if (kv == null) {
            return;
        }
        keyRules = kv.values().stream()
                .collect(Collectors.toCollection(HashSet::new));
    }

    public List<TimeEvent> getMatchKeyTimeEvents(RedisKeyBean redisKeyBean) {
        Map<String, Integer> matchKeyNums = new HashMap<>();
        for (String key : redisKeyBean.getKeys()) {
            Set<String> matchKeyRules = getMatchKeyRules(key);
            if (matchKeyRules == null) {
                continue;
            }
            for (String keyRule : matchKeyRules) {
                Integer num = matchKeyNums.getOrDefault(keyRule, 0);
                matchKeyNums.put(key, num + 1);
            }
        }
        return matchKeyNums.entrySet()
                .stream()
                .map(o -> {
                    TimeEvent timeEvent = new TimeEvent();
                    timeEvent.setEventTime(redisKeyBean.getEventTime());
                    timeEvent.setUseTime(redisKeyBean.getUseTime());
                    timeEvent.setIdentify(o.getKey());
                    timeEvent.setNumber(o.getValue());
                    return timeEvent;
                })
                .collect(Collectors.toList());
    }

    public Set<String> getMatchKeyRules(String key) {
        if (StringUtil.isEmpty(key) || keyRules == null) {
            return null;
        }
        return keyRules.stream()
                .filter(r -> key.indexOf(r) != -1)
                .collect(Collectors.toSet());
    }
}