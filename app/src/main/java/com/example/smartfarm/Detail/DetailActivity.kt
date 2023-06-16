package com.example.smartfarm.Detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartfarm.ImageView.ImageViewActivity
import com.example.smartfarm.R
import com.example.smartfarm.databinding.ActivityDetailBinding
import com.example.smartfarm.navigasi.NavigasiActivity
import com.example.smartfarm.navigasi.ui.Camera.CameraActivity
import com.example.smartfarm.navigasi.ui.Camera.rotateFile
import java.io.File


val dummypadidesc = listOf(
    "BacterialBlight",
    "Blast",
    "Brownspot",
    "Healthy",
    "Leaf Scald",
    "Obeject Tidak Terdekteksi",
    "Tungro"
)

val dummypadi = listOf(
            "Bacterial blight disebabkan oleh Xanthomonas oryzae pv. oryzae. Penyakit ini menyebabkan layu pada bibit tanaman dan kuning serta mengeringnya daun.\n" +
            "\n" +
            "Kenapa terinfeksi?\n" +
            "Penyakit ini cenderung berkembang di daerah yang memiliki gulma dan sisa-sisa tanaman yang terinfeksi. Penyakit ini dapat terjadi baik di lingkungan tropis maupun iklim sedang, terutama di daerah dataran rendah yang diairi secara irigasi atau tergantung pada hujan. Secara umum, penyakit ini lebih menyukai suhu antara 25 hingga 34°C, dengan kelembapan relatif di atas 70%.\n" +
            "\n" +
            "Penyakit ini biasanya terlihat ketika terjadi angin kencang dan hujan deras secara terus-menerus, yang memungkinkan bakteri penyebab penyakit menyebar dengan mudah melalui tetesan lendir pada luka tanaman yang terinfeksi.\n" +
            "\n" +
            "Bacterial blight dapat menjadi parah pada varietas padi yang rentan jika mendapatkan pemupukan nitrogen yang tinggi.\n" +
            "\n" +
            "Treatment\n" +
            "Menanam varietas yang tahan terhadap penyakit ini telah terbukti sebagai cara yang paling efisien, paling dapat diandalkan, dan paling murah untuk mengendalikan bacterial blight.\n" +
            "\n" +
            "Pilihan pengendalian penyakit lainnya meliputi:\n" +
            "1. Gunakan jumlah nutrisi tanaman yang seimbang, terutama nitrogen.\n" +
            "2. Pastikan drainase yang baik pada lahan pertanian (pada tanaman yang ditanam dengan sistem perendaman konvensional) dan pada tempat persemaian.\n" +
            "3. Jaga kebersihan lahan pertanian. Hilangkan gulma dan olah sisa-sisa tanaman seperti jerami, rebah padi, batang padi, dan bibit sukarela yang dapat berfungsi sebagai inang bagi bakteri penyebab penyakit.\n" +
            "4. Biarkan lahan pertanian tidak ditanami selama beberapa waktu agar kering dan menekan agen penyebab penyakit di dalam tanah dan sisa-sisa tanaman.",
            "Blast disebabkan oleh jamur Magnaporthe oryzae. Blast dapat mempengaruhi semua bagian tanaman padi yang berada di atas tanah: daun, kerah, nodus, leher, bagian dari malai, dan kadang-kadang selubung daun. \n" +
            "\n" +
            "Kenapa terinfeksi?\n" +
            "Blast dapat terjadi di mana saja spora blast hadir. Ini terjadi di daerah dengan kelembaban tanah rendah, periode hujan yang sering dan lama, dan suhu dingin pada siang hari. Pada padi sawah, perbedaan suhu siang-malam yang besar yang menyebabkan pembentukan embun pada daun dan suhu yang lebih dingin secara keseluruhan mendukung perkembangan penyakit.\n" +
            "\n" +
            "Treatment\n" +
            "Untuk mengelola risiko utama serangan penyakit blast pada tanaman, ada beberapa cara yang dapat dilakukan. Salah satunya adalah dengan menanam varietas tanaman yang tahan terhadap penyakit tersebut. Anda dapat menghubungi kantor pertanian setempat untuk mendapatkan informasi mengenai varietas terbaru yang tersedia.\n" +
            "\n" +
            "Selain itu, ada beberapa tindakan pengelolaan tanaman lain yang bisa dilakukan, seperti:\n" +
            "\n" +
            "1. Menyesuaikan waktu penanaman. Sebaiknya menabur benih pada awal musim hujan, jika memungkinkan.\n" +
            "2. Membagi aplikasi pupuk nitrogen menjadi dua atau lebih perlakuan. Penggunaan pupuk yang terlalu berlebihan dapat meningkatkan tingkat serangan penyakit blast.\n" +
            "3. Melakukan penyiraman lapangan secara teratur untuk menjaga kelembaban tanah.\n" +
            "4. Mengaplikasikan pupuk silikon, seperti silikat kalsium, pada tanah yang kekurangan unsur silikon. Hal ini dapat membantu mengurangi risiko serangan penyakit. Namun, perlu diingat bahwa silikon harus digunakan dengan efisien karena harganya yang tinggi. Sebagai alternatif, jerami padi dengan kandungan silikon tinggi dapat digunakan sebagai sumber silikon yang lebih terjangkau. Namun, pastikan jerami tersebut bebas dari jamur penyebab blast, karena jamur dapat bertahan hidup pada jerami dan dapat menyebarkan penyakit lebih lanjut jika digunakan sebagai sumber silikon.\n" +
            "5. Fungisida sistemik seperti triazol dan strobilurin dapat digunakan dengan bijaksana untuk mengontrol serangan penyakit blast. Aplikasi fungisida saat tanaman mulai berbunga dapat efektif dalam mengendalikan penyakit tersebut.",

            "Brown spot adalah penyakit jamur yang menginfeksi coleoptile, daun, pelepah daun, cabang bunga, glum, dan bonggol padi.\n" +
            "Kerusakan yang paling terlihat adalah adanya bercak besar pada daun yang dapat menyebabkan kematian seluruh daun. Ketika infeksi terjadi pada biji, biji yang tidak terisi atau biji yang bercak atau berubah warna akan terbentuk.\n" +
            "\n" +
            "Kenapa terinfeksi?\n" +
            "Penyakit ini dapat berkembang di daerah dengan kelembapan relatif tinggi (86−100%) dan suhu antara 16 hingga 36°C. Penyakit ini umum terjadi di tanah yang tidak tergenang air dan kekurangan nutrisi, atau pada tanah yang mengandung zat-zat beracun. Untuk terjadinya infeksi, daun harus basah selama 8 hingga 24 jam. Jamur ini dapat bertahan hidup dalam biji selama lebih dari empat tahun dan dapat menyebar dari tanaman ke tanaman melalui udara. Sumber utama brown spot di lahan pertanian meliputi:\n" +
            "1. biji yang terinfeksi, yang menghasilkan bibit yang terinfeksi\n" +
            "2. padi sukarela\n" +
            "3. sisa-sisa padi yang terinfeksi\n" +
            "4. gulma\n" +
            "Brown spot dapat terjadi pada semua tahap pertumbuhan tanaman, tetapi infeksi paling kritis terjadi selama periode pertumbuhan maksimum hingga tahap pematangan tanaman.\n" +
            "\n" +
            "Treatment\n" +
            "Meningkatkan kesuburan tanah adalah langkah pertama dalam mengendalikan brown spot. Untuk melakukannya:\n" +
            "1. Monitor nutrisi tanah secara teratur\n" +
            "2. Aplikasikan pupuk yang diperlukan \n" +
            "3. Untuk tanah yang rendah kandungan silikanya, aplikasikan slag silikat kalsium sebelum penanaman\n" +
            "Namun, pupuk dapat mahal dan mungkin membutuhkan beberapa musim tanam sebelum efektif. Pilihan pengelolaan yang lebih ekonomis meliputi:\n" +
            "1. Gunakan varietas tahan. Hubungi kantor pertanian setempat Anda untuk daftar varietas terbaru yang tersedia.\n" +
            "2. Gunakan fungisida (misalnya, iprodione, propikonazol, azoksistrobin, trifloksistrobin, dan karbendazim) sebagai perlakuan biji.\n" +
            "3. Perlakukan biji dengan air panas (53−54°C) selama 10−12 menit sebelum penanaman, untuk mengendalikan infeksi primer pada tahap bibit. Untuk meningkatkan efektivitas perlakuan, rendam biji dalam air dingin selama delapan jam sebelumnya.",
    "Tanaman padi anda terdekteksi sehat!",

            "Leaf scald adalah penyakit jamur yang disebabkan oleh Microdochium oryzae, yang menyebabkan daun terlihat seperti terbakar.\n" +
            "\n" +
            "Kenapa terinfeksi?\n" +
            "Perkembangan penyakit biasanya terjadi menjelang akhir musim pada daun dewasa dan lebih disukai oleh cuaca basah, pemupukan nitrogen tinggi, dan jarak tanam yang rapat. Penyakit ini berkembang lebih cepat pada daun yang terluka daripada pada daun yang tidak terluka. Sumber infeksi berasal dari biji dan sisa tanaman. Cuaca basah dan dosis pupuk nitrogen yang tinggi mendukung perkembangan penyakit ini.\n" +
            "\n" +
            "Leaf scald umumnya terjadi di Amerika Tengah dan Selatan, di mana penyakit ini telah menyebabkan kerugian hasil yang signifikan. Penyakit ini juga terjadi di Asia, Afrika, dan Amerika Serikat. Penyakit ini ditemukan di daerah dataran tinggi, hujan, irigasi, dan hutan bakau.\n" +
            "\n" +
            "Treatment\n" +
            "1. Gunakan varietas yang tahan.\n" +
            "2. Hubungi kantor pertanian setempat untuk daftar varietas yang tersedia saat ini.\n" +
            "3. Hindari penggunaan pupuk yang berlebihan. Aplikasikan nitrogen secara terbagi.\n" +
            "4. Gunakan benomil, karbendazim, kitozene, dan tiophanate-metil untuk mengobati biji.\n" +
            "5. Di lapangan, penyemprotan benomil, asetat fentin, edifenfos, dan validamisin secara signifikan mengurangi kejadian leaf scald. Pengaplikasian daun kaptafol, mankozeb, dan oksiklorida tembaga juga mengurangi kejadian dan tingkat keparahan penyakit jamur tersebut.",
    "Pastikan Anda Memasukkan Foto Tanaman Padi",

            "Penyakit tungro padi disebabkan oleh kombinasi dua virus yang ditularkan oleh wereng daun. Penyakit ini menyebabkan perubahan warna daun, pertumbuhan terhambat, jumlah anakan berkurang, dan biji yang steril atau terisi sebagian.\n" +
            "\n" +
            "Tungro menginfeksi padi yang ditanam, beberapa kerabat padi liar, dan gulma berumput lainnya yang umum ditemukan di sawah padi.\n" +
            "\n" +
            "Kenapa terninfeksi?\n" +
            "Virus penyebab penyakit tungro ditularkan dari satu tanaman ke tanaman lain oleh wereng daun yang memakan tanaman yang terinfeksi tungro. Vektor yang paling efisien adalah wereng daun hijau. Wereng daun dapat mengambil virus dari bagian mana pun tanaman yang terinfeksi dengan memakan tanaman tersebut, bahkan dalam waktu singkat. Mereka kemudian dapat langsung mentransmisikan virus ke tanaman lain dalam waktu 5 hingga 7 hari. Virus tidak tetap dalam tubuh wereng kecuali jika wereng tersebut kembali memakan tanaman yang terinfeksi dan mengambil virus lagi.\n" +
            "\n" +
            "Infeksi tungro dapat terjadi pada semua tahap pertumbuhan tanaman padi. Penyakit ini paling sering terlihat selama fase vegetatif. Tanaman paling rentan saat tahap anakan. Kejadian tungro tergantung pada ketersediaan sumber virus dan populasi vektor. Selain tanaman padi terinfeksi di lahan petani, sumber utama lain untuk tungro meliputi:\n" +
            "1. Rebah dari tanaman sebelumnya\n" +
            "2. Pertumbuhan baru dari rebah yang terinfeksi yang tidak dibajak dan diolah dengan baik\n" +
            "3. Padi sukarela\n" +
            "4. Tanaman terinfeksi di sawah padi terdekat\n" +
            "5. Bibit yang ditanam di tempat persemaian juga dapat terinfeksi tungro sebelum dipindahkan dan dapat menjadi sumber virus utama.\n" +
            "\n" +
            "Treatment\n" +
            "Setelah tanaman padi terinfeksi tungro, penyakit ini tidak dapat disembuhkan. Langkah-langkah pencegahan lebih efektif untuk pengendalian tungro daripada langkah pengendalian penyakit langsung. Penggunaan insektisida untuk mengendalikan wereng daun sering tidak efektif, karena wereng daun hijau terus berpindah ke lahan sekitarnya dan menyebarkan tungro dengan cepat dalam waktu makan yang singkat. Langkah-langkah yang paling praktis saat ini meliputi:\n" +
            "\n" +
            "1. Menanam varietas tahan tungro atau wereng daun. Ini adalah cara yang paling ekonomis untuk mengendalikan penyakit ini. Terdapat varietas tahan tungro yang tersedia untuk Filipina, Malaysia, Indonesia, India, dan Bangladesh. Hubungi kantor pertanian setempat Anda untuk daftar varietas terbaru yang tersedia.\n" +
            "2. Praktikkan penanaman secara serentak dengan petani di sekitar. Penanaman yang terlambat atau terlalu awal, dibandingkan dengan tanggal rata-rata di suatu daerah, membuat lahan rentan terhadap tungro. Lahan yang ditanam terlambat juga membawa risiko bagi penanaman awal pada musim berikutnya.\n" +
            "3. Atur waktu penanaman ketika wereng daun hijau tidak berada musim atau berlimpah, jika diketahui.\n" +
            "4. Bajak rebah yang terinfeksi segera setelah panen untuk mengurangi sumber inokulum dan memusnahkan telur dan tempat berkembang biak wereng daun hijau."
)

class DetailActivity : AppCompatActivity() {
//    lateinit var namapenyakit : penyakit
    private lateinit var binding: ActivityDetailBinding
    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_IMAGE = "extra_image"

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Mendapatkan data gambar dari Intent
        val byteArray = intent.getByteArrayExtra(EXTRA_IMAGE)
        val result = intent.getStringExtra(EXTRA_NAME)
        val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }


        val textView = findViewById<TextView>(R.id.textView2)
        textView.text = dummypadidesc[result!!.toInt()]

        val textView2 = findViewById<TextView>(R.id.textView3)
        textView2.text = dummypadi[result!!.toInt()]


        // Mengubah ukuran gambar
        val resizedBitmap = bitmap?.let { resizeBitmap(it, 409, 250) }

        // Tampilkan gambar di ImageView
        val imageView = findViewById<ImageView>(R.id.imageView5)
        resizedBitmap?.let { imageView.setImageBitmap(it) }



        binding.back.setOnClickListener {
            val intent = Intent(this, ImageViewActivity::class.java)
            startActivity(intent)

        }

    }
    private fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

}