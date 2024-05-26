package com.xdu.activity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@MapperScan("com.xdu.activity.mapper")
@EnableFeignClients(basePackages = "com.xdu.client")
@EnableDiscoveryClient
public class ServiceActivityApplication {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		System.out.println("main begin....");
		CompletableFuture<Integer> completableFuture =
				CompletableFuture.supplyAsync(()->{
					System.out.println("当前线程："+Thread.currentThread().getId());
					int result = 1024;
					System.out.println("result:"+result);
					return result;
				},executorService);
		//获取返回结果
		Integer value = completableFuture.get();
		System.out.println("main over...."+value);
		SpringApplication.run(ServiceActivityApplication.class, args);
	}
}