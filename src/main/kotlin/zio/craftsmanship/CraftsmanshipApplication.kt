package zio.craftsmanship

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CraftsmanshipApplication

fun main(args: Array<String>) {
    runApplication<CraftsmanshipApplication>(*args)
}
