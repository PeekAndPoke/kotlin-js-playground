package de.peekandpoke.kraft.auth

import de.peekandpoke.jshelper.Object
import de.peekandpoke.jshelper.jsObjectToMap

@Suppress("FunctionName")
@JsModule("jwt-decode")
@JsNonModule
private external fun jwt_decode(jwt: String): Object?

fun decodeJwtBody(jwt: String): Map<String, Any?> {

//    val parts = jwt.split(".")
//
//    if (parts.size < 2) {
//        return emptyMap()
//    }
//
//    console.log(parts[1])
//
//    val bodyStr = btoa(parts[1])
//
//    console.log(bodyStr)
//    window.alert(bodyStr)

    return jsObjectToMap(jwt_decode(jwt))
}

