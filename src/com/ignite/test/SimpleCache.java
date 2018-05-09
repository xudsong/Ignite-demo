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
		 
        // ��client��ģʽ����ignite, ���ݴ洢��ǰ���Ѿ�������ignite�ڵ��� 
        Ignition.setClientMode(true);
 
        try(Ignite ignite = Ignition.start()){
        	
        	// ��������
            CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<Integer, String>();
            cacheCfg.setName("myCache");
        	// ���û������ʱ��
            cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        	
        	/**
        	 * ����ϵͳ�еĴ洢�ͻ�ȡ��ͬ������
        	 * ��ignite��Ⱥ�д�������Ϊsimple�ķֲ�ʽ���档�ڶ�������ʱ��
        	 * ����Ϊsimple�Ļ����Ѿ����ڻ�ȡ�û��棬��put���ע�͵���Ȼ���Ի�ȡ��ֵ
        	 */
            // ������治���ھʹ���������Ѵ��ھͻ�ȡ���� 
           /* IgniteCache<Integer,String> cache = ignite.getOrCreateCache("simple");
            for(int i = 0; i < 10; i++){
                cache.put(i, i+"haha");
            }
            for(int i=0; i< 10; i++){
                System.out.println(cache.get(i));
            }*/
            
            /**
             * �첽����
             */
            /*IgniteCache<Integer, String> simple =
                    ignite.getOrCreateCache("simple");
 
			// �����첽���� 
			@SuppressWarnings("deprecation")
			IgniteCache<Integer, String>  asynCache = simple.withAsync();
			 
			// ԭ�Ӳ��� ��ȡ��ֵ ������ֵ
			asynCache.getAndPut(33, "3332");
			 
			// ��ȡ������õ�future
			@SuppressWarnings("deprecation")
			IgniteFuture<Integer> fut = asynCache.future();
			// ������� 
			fut.listen(f -> System.out.println("Previous cache value: " + f.get()));*/
        	
        	/**
        	 * ԭ�Ӳ���
        	 */
        	IgniteCache<Integer, String> simple =ignite.getOrCreateCache("simple");
        	
 
			// ��������  ���ؾ�ֵ 
			String oldVal = simple.getAndPut(11, "haha");
			 
			// �������������� ���ؾ�ֵ
			oldVal = simple.getAndPutIfAbsent(11, "11 getAndPutIfAbsent2");
			 
			// ����������滻 ���ؾ�ֵ
			oldVal = simple.getAndReplace(11, "11 getAndReplace");
			 
			// ɾ����ֵ�� ���ؾ�ֵ
			oldVal = simple.getAndRemove(11);
			 
			// �������������� �ɹ�����true
			boolean success = simple.putIfAbsent(12, "12 putIfAbsent");
			 
			// ����������滻 �ɹ����� true
			success = simple.replace(12, "12 replace");
			 
			// ���ֵƥ�� ���滻 �ɹ�����true
			success = simple.replace(12, "12 replace", "12 12 12");
			 
			// ���ֵƥ����ɾ�� �ɹ�����true
			success = simple.remove(11, "11");
        	
			//��ȡ�������ݲ����
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
