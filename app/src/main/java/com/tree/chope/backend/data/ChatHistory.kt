package com.tree.chope.backend.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

data class ChatHistory(

	@Json(name="name")
	val name: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="last_message")
	val lastMessage: String? = null,

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="id")
	val id: Int? = null,
)

