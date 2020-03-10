package nl.coffeeit.appversioning.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import nl.coffeeit.appversioning.AppVersionManager
import nl.coffeeit.appversioning.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnCheckVersion.setOnClickListener {
            AppVersionManager.getInstance().getAppVersionStatus().executeAsync(
                onSuccess = {
                    Snackbar.make(binding.root, "App Version Status: ${it.name}", Snackbar.LENGTH_LONG).show()
                },
                onError = { e ->
                    Log.e("MainActivity", e.message, e)
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
                }
            )
        }
    }
}
