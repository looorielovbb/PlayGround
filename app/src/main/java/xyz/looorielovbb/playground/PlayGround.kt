package xyz.looorielovbb.playground

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class PlayGround : Application() {

    @Inject
    @ApplicationContext
    // FIX: Delete when https://github.com/google/dagger/issues/3601 is resolved.
    lateinit var appContext: Context
    override fun onCreate() {
        super.onCreate()
        application = this
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object {
        lateinit var application: PlayGround
    }
}