package zhaohe.test.filter;

import java.util.Map;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.model.ConsumerModel;

public class TestExceptionFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String namespace = null;
		try {
			Map<Object, Object> attributes = invocation.getAttributes();
			if (attributes != null) {
				org.apache.dubbo.rpc.model.ConsumerModel consumerModel = (ConsumerModel) attributes
						.get("consumerModel");
				if (consumerModel != null) {
					RegistryConfig registryConfig = consumerModel.getReferenceConfig().getRegistry();
					if (registryConfig != null) {
						if (/* RegistryProtocol.NACOS_PROTOCOL */"nacos"
								.equalsIgnoreCase(registryConfig.getProtocol())) {
							Map<String, String> parameters = registryConfig.getParameters();
							if (parameters != null) {
								namespace = parameters.get(com.alibaba.nacos.api.PropertyKeyConst.NAMESPACE);
							}
						} else {
							namespace = registryConfig.getGroup();
						}

					}
				}
			}
		} catch (Exception e) {
			System.out.println("ExceptionFilter获取namespace失败");
		}
		try {
			return invoker.invoke(invocation);
		} catch (RpcException e) {
			RpcException wrape = new RpcException("[" + namespace + "] " + e.getMessage(), e.getCause());
			wrape.setCode(e.getCode());
			wrape.setStackTrace(e.getStackTrace());
			throw wrape;
		}
	}

}
