package com.example.project2.screens.axxonOne.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

@Composable
fun DateTimePicker(
    onDismiss: () -> Unit,
    onDateTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance() //текущая дата и время

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val timePicker = TimePickerDialog(
                context,
                { _, hour, minute ->
                    val formattedDateTime = String.format(
                        Locale.US, //формат США
                        "%04d%02d%02dT%02d%02d%02d.000",
                        year, month + 1, dayOfMonth, hour, minute, 0
                    )
                    onDateTimeSelected(formattedDateTime) //возвращаем выбранное время
                },
                calendar.get(Calendar.HOUR_OF_DAY), //часы
                calendar.get(Calendar.MINUTE),
                true
            )
            timePicker.show()
        },

        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePicker.setOnCancelListener { onDismiss() } //закрытие при отмене
    datePicker.show()
}
