plugins {
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
}


dependencies {
	implementation(project(":coupon-core"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	enabled = true
}