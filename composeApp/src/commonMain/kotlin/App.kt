import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.ChartValue
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        BezierLineChartPreview()
    }
}


@Composable
fun BezierLineChartPreview() {
    var isVisible = remember { mutableStateOf(false) }

    MaterialTheme {
        Column(modifier = Modifier.background(Color.Black).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            AnimatedVisibility(
                modifier = Modifier.wrapContentSize().background(Color.Black),
                visible = isVisible.value
            ) {

                BezierLineChart(
                    modifier = Modifier.size(450.dp).background(Color.Black).padding(20.dp),
                    yData = listOf(
                        10f,
                        20f,
                        30f,
                        40f,
                        50f,
                        60f,
                        70f
                    ),
                    xData =
                    listOf(
                        ChartValue("Jan", 10f),
                        ChartValue("Feb", 50f),
                        ChartValue("Mars", 30f),
                        ChartValue("May", 40f),
                        ChartValue("April", 50f),
                        ChartValue("Jun", 40f),
                        ChartValue("Jul", 70f)
                    ),
                    strokeWidth = 4f,
                    withGrid = true,
                    strokeColor = Color.Green,
                    fillGradientColors = listOf(Color.Green, Color.Transparent)
                )
            }

            Button(modifier = Modifier.padding(20.dp).height(100.dp).width(200.dp), onClick = {
                isVisible.value = !isVisible.value
            }) {
                Text("Show Chart")
            }

        }


    }
}
