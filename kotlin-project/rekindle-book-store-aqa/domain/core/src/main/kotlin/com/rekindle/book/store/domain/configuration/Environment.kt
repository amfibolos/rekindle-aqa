package com.rekindle.book.store.domain.configuration

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

/**
 * It will first look for environment specified, if that fails, local props will be used
 */
@Sources(value = ["classpath:environments/\${environment}.properties",
"classpath:environments/local.properties"])
interface Environment : Config {
    @Key("base.url")
    fun baseUrl(): String

    @Key("database.url")
    fun databaseUrl(): String

    @Key("database.username")
    fun databaseUsername(): String

    @Key("database.password")
    fun databasePassword(): String

    @Config.Separator(",")
    @Key("database.schemas")
    fun databaseSchemas(): List<String>

    @Key("auth.token.url")
    fun authTokenUrl(): String

    @Key("client.id")
    fun clientId(): String

    @Key("client.secret")
    fun clientSecret(): String

    @Key("scopes.admin")
    fun scopesAdmin(): String

    @Key("grant.type")
    fun grantType(): String

}