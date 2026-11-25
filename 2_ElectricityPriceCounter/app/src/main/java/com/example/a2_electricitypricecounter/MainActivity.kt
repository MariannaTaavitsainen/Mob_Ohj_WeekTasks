package com.example.a2_electricitypricecounter

import android.icu.text.AlphabeticIndex
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2_electricitypricecounter.ui.theme._2_ElectricityPriceCounterTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2_ElectricityPriceCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    ElectricityCalculator(
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
}



@Composable
fun ElectricityCalculator(modifier: Modifier = Modifier) {
    var consumptionKWH by remember { mutableStateOf("")}
    var price by remember {mutableStateOf(0.25f)}
    var zeroVAT by remember {mutableStateOf(false)}

    val vat = 25.5f
    val consumption = consumptionKWH.toFloatOrNull() ?: 0f
    val cost = price * consumption
    val vatCost = cost * vat / (100 + vat)

    val totalPrice = if (zeroVAT) cost - vatCost else cost
    val roundedTotalPrice = "%.2f".format(totalPrice).toFloat()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Electricity price calculator",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = consumptionKWH,
            onValueChange = {  consumptionKWH = it },
            label = { Text ("Consumption (kwh)")}
        )

        Text(
            text = "Price: %.2f €/kWh".format(price)
        )
        Slider(
            value = price,
            onValueChange = { price = it },
            valueRange= 0f..1f
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
                Checkbox(
                    checked = zeroVAT,
                    onCheckedChange = { zeroVAT = it}
                )
            Text ( "Without VAT 25,5%")
        }

        Surface() {

            Text(
                text = (roundedTotalPrice.toString())
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _2_ElectricityPriceCounterTheme {
    }
}
