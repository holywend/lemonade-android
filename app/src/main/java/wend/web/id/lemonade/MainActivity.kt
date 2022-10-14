package wend.web.id.lemonade

import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wend.web.id.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeLayout(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonadeLayout(modifier: Modifier = Modifier) {
    var requiredClick by remember { mutableStateOf(0) }
    var stepId by remember { mutableStateOf(1) }
    val steps = ArrayList<Step>()
    steps.add(Step(1, R.drawable.lemon_tree, R.string.lemon_tree, R.string.tap_lemon_tree))
    steps.add(Step(2, R.drawable.lemon_squeeze, R.string.lemon, R.string.tap_squeeze_lemon))
    steps.add(Step(3, R.drawable.lemon_drink, R.string.glass_of_lemon, R.string.tap_glass_to_drink))
    steps.add(Step(4, R.drawable.lemon_restart, R.string.empty_glass, R.string.tap_glass_to_start))
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (stepId) {
            1 -> {
                ImageAndTest(count = null,
                    image = steps[stepId - 1].image,
                    imageDesc = steps[stepId - 1].imageDesc,
                    text = steps[stepId - 1].text,
                    onClick = { stepId++
                        requiredClick = (2..6).random()
                    })

            }
            2 -> {
                ImageAndTest(count = requiredClick,
                    image = steps[stepId - 1].image,
                    imageDesc = steps[stepId - 1].imageDesc,
                    text = steps[stepId - 1].text,
                    onClick = {
                        requiredClick--
                        if (requiredClick == 0) {
                            stepId++
                        }
                    })
            }
            4 -> {
                ImageAndTest(count = null,
                    image = steps[stepId - 1].image,
                    imageDesc = steps[stepId - 1].imageDesc,
                    text = steps[stepId - 1].text,
                    onClick = { stepId = 1 })
            }
            else -> {
                ImageAndTest(count = null,
                    image = steps[stepId - 1].image,
                    imageDesc = steps[stepId - 1].imageDesc,
                    text = steps[stepId - 1].text,
                    onClick = { stepId++ })
            }
        }
    }
}

@Composable
fun ImageAndTest(
    count: Int?,
    image: Int,
    imageDesc: Int,
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (count == null) stringResource(id = text) else stringResource(id = text) + ": $count",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(painter = painterResource(id = image),
            contentDescription = stringResource(id = imageDesc),
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .border(
                    1.dp, Color(red = 105, green = 205, blue = 216), RoundedCornerShape(8.dp)
                ))
    }
}

class Step(
    var id: Int, var image: Int, var imageDesc: Int, var text: Int
)