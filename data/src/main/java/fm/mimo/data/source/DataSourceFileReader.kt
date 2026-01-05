package fm.mimo.data.source

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

class DataSourceFileReader(val context: Context, val gson: Gson) {
    fun readFromFile(
        fileName: String,
    ): String {
        val reader = BufferedReader(InputStreamReader(context.assets.open(fileName), Charset.defaultCharset()))
        return reader.readText()
    }

    inline fun <reified T : Any> fromJson(
        text: String
    ): T = gson.fromJson(text, TypeToken.get(T::class.java))
}
