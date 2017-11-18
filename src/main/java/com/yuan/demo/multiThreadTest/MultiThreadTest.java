package com.yuan.demo.multiThreadTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多线程测试框架，可用来测试json接口，webservice接口，或一般性的httpclient客户端等
 * 支持对线程传入参数以及取得线程返回值（此例子是list<string>，并且对返回值进行重复性的校验），
 *
 * @author yuan
 * @version 1.0.0
 */
public class MultiThreadTest {
    /**
     * 程序总入口
     */
//    public static void main(String[] args) {
//        MultiThreadTest multiThreadTest = new MultiThreadTest();
//        multiThreadTest.test();
//
//    }

    public void test() {

        long millis_start = System.currentTimeMillis();
        int threadNum = 5;
        List<String> list = new ArrayList<String>();

        //创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        List<String> tenantIdList = new ArrayList<String>();
        tenantIdList.add("000001");
        tenantIdList.add("000011");
        List<String> keyList = new ArrayList<String>();
        keyList.add("STOCKID");
        keyList.add("STOCKNAME");
        for (int i = 0; i < threadNum; i++) {
            Callable<List<String>> myThread = new CallThread(i, keyList, tenantIdList);
            Future<List<String>> future = pool.submit(myThread);
            try {
                list.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
        Set<String> listSet = new HashSet<String>(list);
        System.out.println("list_size:" + list.size() + " list_set_size:" + listSet.size());
        if (list.size() != listSet.size()) {
            System.out.println("数据有重复");
            System.out.println("list:" + list);
            System.out.println("listSet:" + listSet);
        } else {
            System.out.println("数据无重复");
        }

        long millis_end = System.currentTimeMillis();
        System.out.println("总耗时(s):" + (millis_end - millis_start));

    }


}

/**
 * 可返回list<String>数据的线程，需要自己实现其中的call函数
 */
class CallThread implements Callable<List<String>> {
    private int i; // 第几个线程
    List<String> tenantIdList;
    List<String> keyList;
    List<String> genIDs = new ArrayList<String>();

    /**
     * 构造函数，可将任务依赖的变量传入
     *
     * @param keyList,任务依赖的变量
     * @param tenantIdList,任务依赖的变量
     * @return list<String>
     */
    public CallThread(int i, List<String> keyList, List<String> tenantIdList) {
        this.i = i;
        this.keyList = keyList;
        this.tenantIdList = tenantIdList;
    }

    public List<String> call() throws Exception {
        for (int j = 0; j < this.keyList.size(); j++) {
            for (int k = 0; k < this.tenantIdList.size(); k++) {
                /*这里的yourdata实际可能为webservice接口的调用，或client的调用*/
                String yourData = this.i + this.keyList.get(j) + this.tenantIdList.get(k);
                genIDs.add(yourData);
            }
        }
        return genIDs;
    }

}
