package com.alness.myapplication.models

import java.time.LocalDateTime
import java.util.UUID

data class ModuleResponse(
    var id: UUID?,
    var title: String?,
    var subtitle: String?,
    var path: String?,
    var icon: String?,
    var level: String?,
    var createdAt: String?,
    var updatedAt: String?,
    var erased: Boolean?

)