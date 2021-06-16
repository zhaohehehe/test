package zhaohe.test.loadbalance;

import java.util.List;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;

public class TestLoadblance extends RandomLoadBalance {

	public TestLoadblance() {
	}

	protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
		System.out.println("============TestLoadblance===================");
		return super.doSelect(invokers, url, invocation);
	}

}