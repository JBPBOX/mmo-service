package com.pvpbox.player.service.security.config

import com.pvpbox.player.service.repositories.PlayerRepository
import com.pvpbox.player.service.security.filter.JWTTokenFilter
import com.pvpbox.player.service.security.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig() {

    @Autowired
    lateinit var jwtTokenFilter: JWTTokenFilter

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsServiceImpl()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:5173")  // Application url
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .csrf().disable()
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { registry ->
                registry.requestMatchers("/api/auth/signin").permitAll()
                registry.requestMatchers("/api/auth/signup").permitAll()
                registry.requestMatchers("/api/auth/signout").permitAll()

                registry.requestMatchers("/api/test/permitall").permitAll()
                registry.requestMatchers("/api/test/mod").hasAnyRole("MOD", "ADMIN")
                registry.requestMatchers("/api/test/admin").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/profiles/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/profiles/**").authenticated()
                registry.requestMatchers(HttpMethod.PUT,"/api/profiles/**").authenticated()
                registry.requestMatchers(HttpMethod.DELETE,"/api/profiles/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/bankaccount/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/bankaccount/**").authenticated()
                registry.requestMatchers(HttpMethod.PUT,"/api/bankaccount/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.DELETE,"/api/bankaccount/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/transations/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/transations/**").authenticated()

                registry.requestMatchers(HttpMethod.GET,"/api/kdas/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/kdas/**").authenticated()
                registry.requestMatchers(HttpMethod.PUT,"/api/kdas/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.DELETE,"/api/kdas/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/attributes/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/attributes/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.PUT,"/api/attributes/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.DELETE,"/api/attributes/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/ranks/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/ranks/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.PUT,"/api/ranks/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.DELETE,"/api/ranks/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/equipements/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/equipements/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.PUT,"/api/equipements/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.DELETE,"/api/equipements/**").hasRole("ADMIN")

                registry.requestMatchers(HttpMethod.GET,"/api/profilestoattributes/**").authenticated()
                registry.requestMatchers(HttpMethod.POST,"/api/profilestoattributes/**").hasRole("ADMIN")
                registry.requestMatchers(HttpMethod.PUT,"/api/profilestoattributes/**").hasRole("ADMIN")
                registry.anyRequest().authenticated()
            }
            .build()
    }
}