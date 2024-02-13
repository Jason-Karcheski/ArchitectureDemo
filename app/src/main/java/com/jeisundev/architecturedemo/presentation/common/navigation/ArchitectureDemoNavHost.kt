package com.jeisundev.architecturedemo.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jeisundev.architecturedemo.presentation.postDetails.composable.PostDetailsRoute
import com.jeisundev.architecturedemo.presentation.posts.composable.PostsRoute

@Composable
fun ArchitectureDemoNavHost() {

    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Destination.Posts.name
    ) {
        composable(route = Destination.Posts.name) {
            PostsRoute(onPostClicked = { postId: Int ->
                controller.navigate(
                    route = "${Destination.PostDetails.name}/$postId"
                )
            })
        }

        composable(
            route = "${Destination.PostDetails.name}/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            PostDetailsRoute(
                postId = backStackEntry.arguments?.getInt("postId"),
                onBack = { controller.popBackStack() }
            )
        }
    }

}

private sealed class Destination(val name: String) {
    data object Posts : Destination(name = "posts")
    data object PostDetails : Destination(name = "postDetails")
}