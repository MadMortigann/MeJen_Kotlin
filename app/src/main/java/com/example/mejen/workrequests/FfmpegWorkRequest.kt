package com.example.mejen.workrequests

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.arthenica.ffmpegkit.FFmpegKit
import java.io.File

private const val LOG_TAG = "MainActivity"

class FfmpegWorkRequest(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val context = applicationContext
        try {
            val mejenDir = context.filesDir.absolutePath + "/temp_videos/"
            val mejenFolder = File(mejenDir)
            if (!mejenFolder.exists()) {
                val result = mejenFolder.mkdir()
                if (!result) {
                    Log.e(LOG_TAG, "Cant make mejen directory")
                    return Result.failure()
                }
            }
            val outName = context.filesDir.absolutePath + "/temp_videos/my_mov12345.mp4"
            val cmd = String.format(
                "-y -i %s -r 24.0 -preset superfast -c:v h264 -vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -f mp4 %s",
                context.filesDir.absolutePath + "/temp_videos/my_mov123.mp4",
                outName
            )
            Log.d(LOG_TAG, String.format("Saving file to %s", outName))
            FFmpegKit.executeAsync(cmd) { session ->
                val state = session.getState()
                val returnCode = session.getReturnCode()
                //Success
                Log.d(
                    LOG_TAG,
                    String.format(
                        "FFmpeg process exited with state %s and rc %s.%s",
                        state,
                        returnCode,
                        session.getFailStackTrace()
                    )
                )
            }
        } catch (throwable: Throwable) {
            Log.d(
                LOG_TAG,
                "Error Sending Notification" + throwable.message
            )
            return Result.failure()
        }
        return Result.success()
    }

}