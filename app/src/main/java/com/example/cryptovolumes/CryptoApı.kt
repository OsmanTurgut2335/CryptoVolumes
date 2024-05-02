import com.example.cryptovolumes.CryptoVolumeData
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    @GET("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&sparkline=false&locale=en")
    fun getCryptoVolumeData(): Call<List<CryptoVolumeData>>
}