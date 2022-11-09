package io.qifan.microservice.order;

import io.qifan.microservice.api.product.GoodsFeignClient;
import io.qifan.microservice.api.product.response.GoodsResponse;
import io.qifan.microservice.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class GoodsFeignClientTest {
    @Autowired
    GoodsFeignClient goodsFeignClient;

    /**
     * 发起http请求去访问商品模块
     * 1. SpringCloudOpenFeign在启动时根据@EnableFeignClients(basePackages = "io.qifan.microservice.api")
     * 扫描到@FeignClient，替我们生成代理类。
     * 2. 我们调用 goodsFeignClient.findAllById() 调用代理类
     * 3. 代理类根据 @FeignClient上的url确定服务ip+端口。
     * 3.1 因为http://microservice-product:9000 这个url里面没有ip，所以发起请求时会先从本机的hosts找到有没有
     * microservice-product对应的ip，找到有127.0.0.1，替换结果变为 http://127.0.0.1:9000
     * 4. 代理类根据@GetMapping（也可以是PostMapping等其他Mapping）确定要调用哪个路径
     * 5. 代理类根据@RequestBody，@RequestParam，@PathVariable等来确定参数格式
     * 6. 最后拼接上面的信息发起一个http请求，就像我们之前测试的时候发起http请求一样。
     */
    @Test
    public void findById() {
        R<List<GoodsResponse>> allById = goodsFeignClient.findAllById(List.of(46L, 49L));
        allById.getResult().forEach(goodsResponse -> {
            log.info(goodsResponse.toString());
        });
    }

}