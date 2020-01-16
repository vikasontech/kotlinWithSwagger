package com.example.kotlinWithSwagger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import reactor.core.publisher.Mono
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootApplication
class KotlinWithSwaggerApplication

fun main(args: Array<String>) {
    runApplication<KotlinWithSwaggerApplication>(*args)
}


@Configuration
@EnableSwagger2WebFlux
class SwaggerConfig {

    @Bean
    fun api(): Docket =
            Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.example.kotlinWithSwagger"))
                    .paths(PathSelectors.any())
                    .build()

}

data class LoanDetail(var _id: String,
					  var loanAmount: BigDecimal,
					  var loanCommencedDate: LocalDate,
					  var interestRate: Float,
					  var numberOfEmi: Int,
					  var emiAmount: BigDecimal)

@RestController
@RequestMapping("/api/v1/loan")
class LoanDetailController () {

	@GetMapping("/detail", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
	fun getData():Mono<LoanDetail> {
		return Mono.justOrEmpty(
				LoanDetail(_id = "dummy",
						loanAmount = BigDecimal.valueOf(100_000L),
						emiAmount = BigDecimal.valueOf(1000L),
						interestRate = 10.00F,
						loanCommencedDate = LocalDate.now(),
						numberOfEmi = 180)
		)
	}
}