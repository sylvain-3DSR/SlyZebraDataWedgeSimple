package com.sr.slyzebradatawedgesimple.zebra.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sr.slyzebradatawedgesimple.R


@Composable
fun MyComposable(
    source: String,
    data: String,
    labelType: String
) {

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .width(1680.dp)
            .height(2400.dp)
            .border(BorderStroke(1.dp, color = Color.Black))
            .background(color = Color.White),
        contentAlignment = Alignment.Center

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(BorderStroke(1.dp, color = Color.Black)),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {
            Image(
                painter = painterResource(id = R.drawable.fuck),
                contentDescription = null
            )

            Text("Scan Source: ${source}")
            Text("Scan Data: ${data}")
            Text("Scan Label Type: ${labelType}")

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}