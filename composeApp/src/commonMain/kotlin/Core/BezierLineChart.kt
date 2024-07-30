import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.ChartValue

@Composable
fun BezierLineChart(
    modifier: Modifier = Modifier,
    xData: List<ChartValue>,
    yData: List<Float>,
    strokeWidth: Float = 4f,
    strokeColor: Color = Color.Green,
    fillGradientColors: List<Color>,
    withGrid: Boolean = false,
    axisColor: Color = Color.Transparent,
    indicatorColor: Color = LitghtGrayColor,
    textStyle: TextStyle = TextStyle(color = LitghtGrayColor, fontSize = 12.sp),
) {
    val maxValue = yData.maxOfOrNull { it } ?: 0f
    val minValue = yData.minOfOrNull { it } ?: 0f
    val range = maxValue - minValue
    val textMeasurer = rememberTextMeasurer()
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )

    }

    Canvas(modifier = modifier) {
        val yStep = size.height / (yData.size - 1)
        val xStep = size.width / (yData.size - 1)
        // Draw y-axis indicators
        for (i in yData.indices) {
            val y = size.height - i * yStep
            drawText(
                text = yData[i].toInt().toString(),
                color = textStyle.color,
                topLeft = Offset(-19.dp.toPx(), y - 12.sp.toPx() / 2),
                textMeasurer = textMeasurer,
                textStyle = textStyle
            )
            drawLine(
                color = indicatorColor,
                start = Offset(if(withGrid) 0f else 5.dp.toPx(), y),
                end = Offset(if (withGrid) size.width else 10.dp.toPx(), y),
                strokeWidth =  1.dp.toPx()
            )
        }

        // Draw x-axis indicators
        for (i in xData.indices) {
            val x = i * xStep
            drawText(
                text = xData[i].x,
                color = textStyle.color,
                topLeft = Offset(x - 8.dp.toPx(), size.height + 4.dp.toPx()),
                textMeasurer = textMeasurer,
                textStyle = textStyle
            )
            drawLine(
                color = indicatorColor,
                start = Offset(x, if(withGrid) 0f else size.height - 5.dp.toPx()),
                end = Offset(x, if(withGrid) size.height else size.height + 10f),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Draw y-axis
        drawLine(
            color = axisColor,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = strokeWidth
        )

        // Draw x-axis
        drawLine(
            color = axisColor,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth
        )
        if (yData.isEmpty()) return@Canvas
        val path = Path().apply {
            moveTo(0.dp.toPx(), size.height - (xData[0].y - minValue) * size.height / range)
            for (i in 1 until yData.size) {
                val x1 = (i - 1) * size.width / (yData.size - 1)
                val y1 = size.height - (xData[i - 1].y - minValue) * size.height / range
                val x2 = i * size.width / (yData.size - 1)
                val y2 = size.height - (xData[i].y - minValue) * size.height / range
                val controlX1 = (x1 + x2) / 2
                val controlX2 = (x1 + x2) / 2
                cubicTo(controlX1, y1, controlX2, y2, x2, y2)
            }
        }

        clipRect(right = size.width * animationProgress.value) {
            drawPath(path = path, color = strokeColor, style = Stroke(2.dp.toPx()))
            path.lineTo(size.width, size.height)
            path.lineTo(0f, size.height)
            drawPath(path = path, brush = Brush.verticalGradient(fillGradientColors))
        }

    }
}


private fun DrawScope.drawText(
    text: String,
    color: Color = Color.Unspecified,
    topLeft: Offset = Offset.Zero,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle = TextStyle.Default,
    alpha: Float = Float.NaN,
    shadow: Shadow? = null,
    textDecoration: TextDecoration? = null,
    drawStyle: DrawStyle? = null,
    blendMode: androidx.compose.ui.graphics.BlendMode = DrawScope.DefaultBlendMode
) {
    val textLayoutResult = textMeasurer.measure(
        text = text,
        style = textStyle
    )

    drawText(
        textLayoutResult = textLayoutResult,
        color = color,
        topLeft = topLeft,
        alpha = alpha,
        shadow = shadow,
        textDecoration = textDecoration,
        drawStyle = drawStyle,
        blendMode = blendMode
    )
}

