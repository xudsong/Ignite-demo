package com.ignite.test;



import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;

public class SimpleCache {
	public static void main(String[] args){
		 
        // 以client的模式启动ignite, 数据存储到前面已经启动的ignite节点上 
        Ignition.setClientMode(true);
 
        try(Ignite ignite = Ignition.start()){
        	
        	// 缓存配置
            CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<Integer, String>();
            cacheCfg.setName("myCache");
        	// 设置缓存过期时间
            cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        	
        	/**
        	 * 缓存系统中的存储和获取是同步操作
        	 * 在ignite集群中创建名称为simple的分布式缓存。第二次启动时，
        	 * 名称为simple的缓存已经存在获取该缓存，把put语句注释掉仍然可以获取到值
        	 */
            // 如果缓存不存在就创建，如果已存在就获取缓存 
           /* IgniteCache<Integer,String> cache = ignite.getOrCreateCache("simple");
            for(int i = 0; i < 10; i++){
                cache.put(i, i+"haha");
            }
            for(int i=0; i< 10; i++){
                System.out.println(cache.get(i));
            }*/
            
            /**
             * 异步操作
             */
            /*IgniteCache<Integer, String> simple =
                    ignite.getOrCreateCache("simple");
 
			// 启动异步操作 
			@SuppressWarnings("deprecation")
			IgniteCache<Integer, String>  asynCache = simple.withAsync();
			 
			// 原子操作 获取旧值 存入新值
			asynCache.getAndPut(33, "3332");
			 
			// 获取上面调用的future
			@SuppressWarnings("deprecation")
			IgniteFuture<Integer> fut = asynCache.future();
			// 监听结果 
			fut.listen(f -> System.out.println("Previous cache value: " + f.get()));*/
        	
        	/**
        	 * 原子操作
        	 */
        	IgniteCache<Integer, String> simple =ignite.getOrCreateCache("simple");
        	
 
			// 插入或更新  返回旧值 
			String oldVal = simple.getAndPut(11, "haha");
			 
			// 如果不存在则插入 返回旧值
			oldVal = simple.getAndPutIfAbsent(11, "11 getAndPutIfAbsent2");
			 
			// 如果存在则替换 返回旧值
			oldVal = simple.getAndReplace(11, "11 getAndReplace");
			 
			// 删除键值对 返回旧值
			oldVal = simple.getAndRemove(11);
			 
			// 如果不存在则插入 成功返回true
			boolean success = simple.putIfAbsent(12, "12 putIfAbsent");
			 
			// 如果存在则替换 成功返回 true
			success = simple.replace(12, "12 replace");
			 
			// 如果值匹配 则替换 成功返回true
			success = simple.replace(12, "12 replace", "12 12 12");
			 
			// 如果值匹配则删除 成功返回true
			success = simple.remove(11, "11");
        	
			//获取缓存数据并输出
			for(int i=0; i< 20; i++){
				if(simple.get(i)==null) {
					continue;
				}else {
					System.out.println(simple.get(i));
				}
            }
        }
    }
}
