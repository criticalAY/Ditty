package com.uchi.ditty

class Content {
}

data class ApiResponse(
    val message: String,
    val data: ApiData
)

data class ApiData(
    val posts: List<Post>
)

data class Post(
    val postId: String,
    val creator: Creator,
    val comment: Comment,
    val reaction: Reaction,
    val submission: Submission
)

data class Creator(
    val name: String,
    val id: String,
    val handle: String,
    val pic: String
)

data class Comment(
    val count: Int,
    val commentingAllowed: Boolean
)

data class Reaction(
    val count: Int,
    val voted: Boolean
)

data class Submission(
    val title: String,
    val description: String,
    val mediaUrl: String,
    val thumbnail: String,
    val hyperlink: String,
    val placeholderUrl: String
)
