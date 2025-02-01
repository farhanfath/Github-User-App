package basic.training.compose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import basic.training.compose.domain.model.User
import basic.training.compose.presentation.ui.theme.inversePrimaryLight
import basic.training.compose.presentation.ui.theme.onPrimaryContainerLight
import basic.training.compose.presentation.ui.theme.primaryLight

@Preview
@Composable
fun UserItem(
    user: User = User("UserName","","","User"),
    onClick : () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(primaryLight.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                ImageLoader(url = user.avatarUrl)
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(text = user.login)
                Spacer(Modifier.height(4.dp))

                val backgroundColor = when(user.type) {
                    "User" -> onPrimaryContainerLight
                    "Organization" -> inversePrimaryLight
                    else -> onPrimaryContainerLight
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(backgroundColor)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                        text = user.type,
                        color = Color.White
                    )
                }
            }
        }
    }
}