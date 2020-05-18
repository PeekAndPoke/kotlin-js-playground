package de.peekandpoke.app.domain

import de.peekandpoke.app.domain.cms.cmsModule
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

val domainCodec: Json = Json(
    configuration = JsonConfiguration.Stable.copy(
        classDiscriminator = "_type",
        ignoreUnknownKeys = true
    ),
    context = cmsModule
)

