import android.annotation.SuppressLint
import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.graph_marker.view.*
import java.text.DateFormat
import java.util.*

class CustomMarker(context: Context, layoutResource: Int):  MarkerView(context, layoutResource) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        val value = entry?.y?.toDouble() ?: 0.0
        val xValue = entry?.x?.toFloat() ?:0.0
        val date = convertToDate(xValue as Float)
        var resText = ""
        if(value.toString().length > 8){
            resText = "BMI: " + value.toString().substring(0,7)
            if(xValue!=0) resText += "\nDate: $date";

        }
        else{
            resText = "BMI: " + value.toString()
            if(xValue!=0) resText += "\nDate: $date";
        }
        bmiValue.text = resText
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }

    private fun convertToDate(value: Float): String? {
        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        return dateTimeFormat.format(timeMilliseconds)
    }
}