package com.desapoint.desapoint.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.desapoint.desapoint.R
import com.desapoint.desapoint.kotlindata.AnnouncementDataObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AnnouncementDetailActivitty : AppCompatActivity() {


    companion object {
        var CONSTANT_INTENT_TITLE = "ANNOUNCEMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_detail)

        val announceMentDataObject = intent.getSerializableExtra(CONSTANT_INTENT_TITLE) as AnnouncementDataObject

        (findViewById(R.id.title) as TextView).text = announceMentDataObject.title.trim()
        (findViewById(R.id.body) as TextView).text = announceMentDataObject.body.trim()
        (findViewById(R.id.from) as TextView).text = announceMentDataObject.user_position.trim()

        findViewById(R.id.close_btn).setOnClickListener({
            finish()
        })

        val pattern = "yyyy-MM-dd hh:mm:ss"
        var date: Date? = null
        val requiredFormat = "MMM dd, yyyy"

        try {
            date = SimpleDateFormat(pattern).parse(announceMentDataObject.date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val dateToDisplay = SimpleDateFormat(requiredFormat).format(date)
        (findViewById(R.id.date) as TextView).text = dateToDisplay

    }
}
