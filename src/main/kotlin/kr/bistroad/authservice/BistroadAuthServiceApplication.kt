package kr.bistroad.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.ribbon.RibbonClient

@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "auth-service")
class BistroadAuthServiceApplication

fun main(args: Array<String>) {
    runApplication<BistroadAuthServiceApplication>(*args)
}
