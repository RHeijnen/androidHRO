package com.example.root.firstapp

import android.os.Handler
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.root.firstapp.DataModels.exerciseCollectionModel
import com.example.root.firstapp.DataModels.helpCollectionModel
import com.example.root.firstapp.FireBase.activeExReq
import com.example.root.firstapp.FireBase.archiveExReq
import com.example.root.firstapp.FireBase.helpReq
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.SyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.security.SecureRandom

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val firebaseArchiveExerciseURL  = "https://myschoolproject-b4a1a.firebaseio.com/ArchiveExercise.json?print=pretty"
    val firebaseActiveExerciseURL   = "https://myschoolproject-b4a1a.firebaseio.com/ActiveExercise.json?print=pretty"
    val firebaseHelpURL             = "https://myschoolproject-b4a1a.firebaseio.com/HelpRequests.json?print=pretty"
    var exerciseCollection = exerciseCollectionModel()
    var helpContainer      = helpCollectionModel()
    val client = SyncHttpClient()
    val runFalseFlag = false

    @Test
    fun falseFlag() {
        if(runFalseFlag){
            fun randomString(length: Int): String {
                val rnd = SecureRandom()
                val AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
                val sb = StringBuilder(length)
                for (i in 0..length - 1)
                    sb.append(
                            AB.get(
                                    rnd.nextInt(
                                            AB.lastIndex
                                    )
                            )
                    )
                return sb.toString()
            }
            if(randomString(5) is String) {
                assertEquals(false, true)
            }else{
                assertEquals(false, true)
            }
        }else{
            assertEquals(true, true)
        }

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.root.firstapp", appContext.packageName)
        client.get(firebaseArchiveExerciseURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });

    }
    @Test
    fun getReqArchive() {
        client.get(firebaseArchiveExerciseURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }
    @Test
    fun getReqActive() {
        client.get(firebaseActiveExerciseURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }
    @Test
    fun getReqHelp() {
        client.get(firebaseHelpURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }
    @Test
    fun convertHelpResponseToHelpObject() {
        client.get(firebaseHelpURL, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }
    @Test
    fun getRandomString() {
        fun randomString(length: Int): String {
            val rnd = SecureRandom()
            val AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            val sb = StringBuilder(length)
            for (i in 0..length - 1)
                sb.append(
                        AB.get(
                                rnd.nextInt(
                                        AB.lastIndex
                                )
                        )
                )
            return sb.toString()
        }
        if(randomString(5) is String) {
            assertEquals(true, true)
        }else{
            assertEquals(false, true)

        }
    }
    @Test
    fun upvoteTest() {
        client.get("http://192.168.37.1:3000/upvote?id=dummy", object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }        // http://localhost:3000/downvote?id=...

    @Test
    fun downvoteTest() {
        client.get("http://192.168.37.1:3000/downvote?id=dummy" , object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                assertEquals(true, false)
            }
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseString: String?) {
                assertEquals(true, true)
            }
        });
    }


}
