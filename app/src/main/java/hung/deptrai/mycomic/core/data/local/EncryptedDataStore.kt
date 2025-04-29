package hung.deptrai.mycomic.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec


val Context.tokenDataStore: DataStore<String> by dataStore(
    fileName = "token_data",
    serializer = EncryptedSerializer()
)

class EncryptedSerializer : androidx.datastore.core.Serializer<String> {
    override val defaultValue: String = ""

    override suspend fun readFrom(input: java.io.InputStream): String {
        val encryptedBytes = input.readBytes()
        val decryptedBytes = decrypt(encryptedBytes)
        return String(decryptedBytes)
    }

    override suspend fun writeTo(t: String, output: java.io.OutputStream) {
        val encryptedBytes = encrypt(t.toByteArray())
        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }

    private fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = getSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(data)
        return iv + encrypted
    }

    private fun decrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = getSecretKey()
        val iv = data.sliceArray(0 until 12)
        val encrypted = data.sliceArray(12 until data.size)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return cipher.doFinal(encrypted)
    }

    private fun getSecretKey(): SecretKey {
        // Tạo hoặc lấy khóa bí mật từ Android Keystore
        val keyBytes = ByteArray(32) // 256-bit key
        // Khởi tạo keyBytes với giá trị cụ thể hoặc lấy từ Keystore
        return SecretKeySpec(keyBytes, "AES")
    }
}