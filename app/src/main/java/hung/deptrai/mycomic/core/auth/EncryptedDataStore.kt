package hung.deptrai.mycomic.core.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import hung.deptrai.mycomic.core.domain.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec


val Context.tokenDataStore: DataStore<Token> by dataStore(
    fileName = "token_data.pb",
    serializer = TokenInfoSerializer()
)

class TokenInfoSerializer : Serializer<Token> {
    override val defaultValue: Token = Token()

    override suspend fun readFrom(input: InputStream): Token {
        val encryptedBytes = input.readBytes()
        val decrypted = decrypt(encryptedBytes)
        return Json.decodeFromString(Token.serializer(), String(decrypted))
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        val json = Json.encodeToString(Token.serializer(), t)
        val encrypted = encrypt(json.toByteArray())
        withContext(Dispatchers.IO) {
            output.write(encrypted)
        }
    }

    private fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        return iv + cipher.doFinal(data)
    }

    private fun decrypt(data: ByteArray): ByteArray {
        val iv = data.sliceArray(0 until 12)
        val cipherText = data.sliceArray(12 until data.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        return cipher.doFinal(cipherText)
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = ByteArray(32) { 1 }
        return SecretKeySpec(keyBytes, "AES")
    }
}
