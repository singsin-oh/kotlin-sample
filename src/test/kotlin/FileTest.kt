import org.junit.jupiter.api.Test

class FileTest {
    @Test
    fun getCurrentFileRoot() {
        println("currentFileRoot: ${System.getenv("user_dir")}")
    }
}