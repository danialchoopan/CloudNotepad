package ir.danialchoopan.cloudnotepad.api.model

data class UserNote(
    val id: Int,
    val user_id: Int,
    val title: String,
    val body: String,
    val public: Boolean,
    val created_at: String,
    val updated_at: String,
)
