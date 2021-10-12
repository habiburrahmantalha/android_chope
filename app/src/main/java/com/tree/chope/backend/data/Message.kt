package com.tree.chope.backend.data

import com.squareup.moshi.Json

data class Message(

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="message")
	val message: String? = null
)
