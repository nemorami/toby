import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Calculator {
    fun <T>calcWithLine(filepath: String, initValue : T, operation: (T, String) -> T) : T{
        var result = initValue
        return BufferedReader(FileReader(filepath)).use {
            it.forEachLine {
                result = operation(result, it)
            }
            result
        }
    }

    fun calcSum(filepath: String): Int {
        return calcWithLine(filepath, 0) {a: Int, b: String  -> a + b.toInt()}
    }
}