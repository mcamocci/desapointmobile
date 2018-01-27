package com.desapoint.desapoint.kotlindata;

import java.io.Serializable

/**
 * Created by hikaroseworx on 1/27/18.
 */


data class AnnouncementDataObject(val id: Int, val title: String ,val body: String , val date: String , val user_position: String ): Serializable;