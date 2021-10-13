package com.tree.chope.backend.data

import com.squareup.moshi.Json

data class Message(

	@Json(name="user_id")
	var userId: Int? = null,

	@Json(name="name")
	var name: String? = null,

	@Json(name="created_at")
	var createdAt: String? = null,

	@Json(name="id")
	var id: Int? = null,

	@Json(name="message")
	var message: String? = null
)
